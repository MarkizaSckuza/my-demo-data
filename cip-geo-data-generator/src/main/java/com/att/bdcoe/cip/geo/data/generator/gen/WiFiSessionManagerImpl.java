package com.att.bdcoe.cip.geo.data.generator.gen;

import com.att.bdcoe.cip.geo.data.core.Coord;
import com.att.bdcoe.cip.geo.data.core.gen.PoinType;
import com.att.bdcoe.cip.geo.data.core.gen.WiFiSessionManager;
import com.att.bdcoe.cip.geo.data.core.util.RandomUpperAndNumberString;
import com.att.bdcoe.cip.geo.data.generator.model.MapCoord;
import com.att.bdcoe.cip.geo.data.generator.model.MapRoute;
import com.att.bdcoe.cip.geo.data.generator.model.WhenCrossing;
import com.att.bdcoe.cip.geo.data.generator.model.WiFiZone;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.*;

@Component
public class WiFiSessionManagerImpl implements WiFiSessionManager<WiFiZone, MapRoute, MapCoord> {

    private Random randomGenerator = new Random();
    private long initSessionId = randomGenerator.nextLong();
    private DecimalFormat myFormatter = new DecimalFormat("0000000000000");
    private List<WiFiZone> wiFiZones;
    private Map<MapCoord, WiFiZone> foundInZone = null;

    @Override
    public String getNewSessionId() {
        initSessionId++;
        return myFormatter.format(initSessionId);
    }

    @Override
    public void submitWiFiZones(Collection<WiFiZone> wiFiZones) {
        this.wiFiZones = new ArrayList<>(wiFiZones);

        //Generate venue codes
        RandomUpperAndNumberString venueCodeGenerator = new RandomUpperAndNumberString(63);
        for (WiFiZone zone : this.wiFiZones) {
            zone.setVenueCode(venueCodeGenerator.nextString());
        }
    }

    @Override
    public void addWiFiZonePoints(List<MapRoute> routes) {
        if (CollectionUtils.isNotEmpty(routes)) {

            List<MapCoord> wiFiCoords = new ArrayList<>();
            List<MapCoord> notWiFiCoords = new ArrayList<>();
            List<WiFiZone> stayAll = new ArrayList<>();

            for (MapRoute route : routes) {
                for (MapCoord mapCoord : route.getPoints()) {
                    if (PoinType.WiFi == mapCoord.getPoiTypeEnum()) {
                        wiFiCoords.add(mapCoord);
                    } else notWiFiCoords.add(mapCoord);
                }
            }

            if (CollectionUtils.isEmpty(wiFiZones)) {
                //remove ALL WiFiZone Marks. NO WiFiZones defined.
                removeAllWiFiZones(wiFiCoords);
                return;
            }

            // sort zones
            for (WiFiZone zone : wiFiZones) {
                if (WhenCrossing.STAYALL == zone.getWhenCrossingAsEnum()) {
                    stayAll.add(zone);
                }
            }

            //remove unnecessary WiFiZone Marks
            removeUnnecessaryWiFiZoneMarks(wiFiCoords, stayAll);

            //finding points which are inside WiFi but not marked as WiFi
            findPointsInsideWiFiAndNotMarkedAsWiFi(notWiFiCoords, stayAll);

            // if ends of route segment are not in WiFi but route is crossing WiFi we are adding additional waypoint on route inside WiFi
            addAdditionalWayPointOnRouteInsideWiFi(routes, stayAll);
        }
    }

    @Override
    public Map<MapCoord, WiFiZone> cordToCircleMap() {
        return foundInZone;
    }

    private void addAdditionalWayPointOnRouteInsideWiFi(List<MapRoute> routes, List<WiFiZone> stayAll) {
        for (MapRoute route : routes) {
            MapCoord previous = null;
            ListIterator<MapCoord> mapCoordIterator = route.getPoints().listIterator();
            MapCoord begin = null;
            List<MapCoord> waypointsToAdd = new ArrayList<>();
            while (mapCoordIterator.hasNext()) {
                MapCoord mapCoord = mapCoordIterator.next();
                if (previous != null) {
                    for (WiFiZone zone : stayAll) {
                        if (zone.crossingLine(previous, mapCoord) && notInList(zone.getVenueCode(), route.getPoints())) {
                            Coord pointInside = zone.pointInsideCircle(previous, mapCoord);
                            if (pointInside != null) {
                                MapCoord newWayPoint = new MapCoord(pointInside);
                                newWayPoint.setPoiType(PoinType.WiFi);
                                newWayPoint.setVenueCode(zone.getVenueCode());
                                waypointsToAdd.add(newWayPoint);
                            }
                        }
                    }
                } else {
                    begin = mapCoord;
                }
                previous = mapCoord;
            }

            //Sorting route.getPoints() begin
            if (CollectionUtils.isNotEmpty(waypointsToAdd)) {
                Comparator<MapCoord> comparator = new MapCordComparator<>(begin);
                route.getPoints().addAll(waypointsToAdd);
                Collections.sort(route.getPoints(), comparator);
            }
        }
    }

    private void findPointsInsideWiFiAndNotMarkedAsWiFi(List<MapCoord> notWiFiCoords, List<WiFiZone> stayAll) {
        for (MapCoord mapCoord : notWiFiCoords) {
            for (WiFiZone zone : stayAll) {
                if (zone.contains(mapCoord)) {
                    mapCoord.setPoiType(PoinType.WiFi);
                    mapCoord.setVenueCode(zone.getVenueCode());
                    foundInZone.put(mapCoord, zone);
                    break;
                }
            }
        }
    }

    private void removeUnnecessaryWiFiZoneMarks(List<MapCoord> wiFiCoords, List<WiFiZone> stayAll) {
        for (MapCoord mapCoord : wiFiCoords) {
            boolean found = false;
            //TODO: Review scenario when 2 WiFiZones with 2 different types will overlap each other.
            for (WiFiZone zone : stayAll) {
                if (zone.contains(mapCoord)) {
                    found = true;
                    mapCoord.setVenueCode(zone.getVenueCode());
                    foundInZone.put(mapCoord, zone);
                    break;
                }
            }
            if (!found) mapCoord.setPoiType(PoinType.None);
        }
    }

    private void removeAllWiFiZones(List<MapCoord> wiFiCoords) {
        for (MapCoord mapCoord : wiFiCoords) {
            mapCoord.setPoiType(PoinType.None);
        }
    }

    private boolean notInList(String venueCode, List<MapCoord> wayPoints) {
        return CollectionUtils.isEmpty(wayPoints)
                || StringUtils.isEmpty(venueCode)
                || IterableUtils.matchesAll(wayPoints, point -> ObjectUtils.notEqual(venueCode, point.getVenueCode()));
    }
}
