package com.att.bdcoe.cip.geo.data.generator.gen;

import com.att.bdcoe.cip.geo.data.core.Coord;
import com.att.bdcoe.cip.geo.data.core.gen.PoinType;
import com.att.bdcoe.cip.geo.data.core.gen.WiFiSessionManager;
import com.att.bdcoe.cip.geo.data.core.util.RandomUpperAndNumberString;
import com.att.bdcoe.cip.geo.data.generator.model.MapCoord;
import com.att.bdcoe.cip.geo.data.generator.model.MapRoute;
import com.att.bdcoe.cip.geo.data.generator.model.WhenCrossing;
import com.att.bdcoe.cip.geo.data.generator.model.WiFiZone;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.*;

@Component
public class WiFiSessionManagerImpl implements WiFiSessionManager<WiFiZone, MapRoute, MapCoord> {

    DecimalFormat myFormatter = new DecimalFormat("0000000000000");
    List<WiFiZone> wiFiZones;
    Map<MapCoord, WiFiZone> foundInZone = null;
    private Random randomGenerator = new Random();
    private long initSesionId = randomGenerator.nextLong();

    @Override
    public String getNewSessionId() {
        initSesionId++;
        return myFormatter.format(initSesionId);
    }

    @Override
    public void submitWiFiZones(Collection<WiFiZone> wiFiZones) {
        this.wiFiZones = new ArrayList<WiFiZone>(wiFiZones);
        //Geenrate venue codes
        RandomUpperAndNumberString venueCodeGenerator = new RandomUpperAndNumberString(63);
        for (WiFiZone zone : this.wiFiZones) {
            zone.setVenueCode(venueCodeGenerator.nextString());
        }

    }

    @Override
    public void addWiFiZonePoints(List<MapRoute> routes) {

        if ((this.wiFiZones == null || this.wiFiZones.isEmpty())
                || (routes == null || routes.isEmpty())) {
            //remove ALL WiFizone Marks. NO WiFiZones defined.
            for (MapRoute route : routes) {
                for (MapCoord mapCoord : route.getPoints()) {
                    if (mapCoord.getPoiTypeEnum().equals(PoinType.WiFi)) mapCoord.setPoiType(PoinType.None);
                }
            }

            return;
        }
        Map<MapCoord, WiFiZone> foundInZone = new HashMap<MapCoord, WiFiZone>();

        List<WiFiZone> stayAll = new ArrayList<WiFiZone>();
        List<WiFiZone> staySelected = new ArrayList<WiFiZone>();
        // sort zones
        for (WiFiZone zone : this.wiFiZones) {
            if (zone.getWhenCrossingAsEnum().equals(WhenCrossing.STAYALL)) stayAll.add(zone);
            else staySelected.add(zone);
        }

        //remove unnesesary WiFizone Marks
        for (MapRoute route : routes) {
            for (MapCoord mapCoord : route.getPoints()) {
                if (mapCoord.getPoiTypeEnum().equals(PoinType.WiFi)) {
                    boolean found = false;
                    //TODO: Review scenario when 2 WiFiZones with 2 different types will overlap each outher.
                    for (WiFiZone zone : stayAll) {
                        if (zone.contains(mapCoord)) {
                            found = true;
                            foundInZone.put(mapCoord, zone);
                            mapCoord.setVenueCode(zone.getVenueCode());
                            break;
                        }
                    }
                    if (!found) mapCoord.setPoiType(PoinType.None);
                }

            }
        }

        //finding points which are inside WiFi but not marked as WiFi
        for (MapRoute route : routes) {
            for (MapCoord mapCoord : route.getPoints()) {
                if (!mapCoord.getPoiTypeEnum().equals(PoinType.WiFi)) {
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
        }


        // if ends of route segment are not in WiFi but route is crossing WiFi we are adding additional waypoint on route inside WiFi

        for (MapRoute route : routes) {
            MapCoord previous = null;
            ListIterator<MapCoord> mapCoordIterator = route.getPoints().listIterator();
            MapCoord begin = null;
            List<MapCoord> waypointsToAdd = new ArrayList<MapCoord>();
            while (mapCoordIterator.hasNext()) {
                MapCoord mapCoord = (MapCoord) mapCoordIterator.next();
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
            if (waypointsToAdd.size() > 0) {
                Comparator<MapCoord> comparator = new MapCordComparator<MapCoord>(begin);
                route.getPoints().addAll(waypointsToAdd);
                Collections.sort(route.getPoints(), comparator);
            }
        }
    }

    private boolean notInList(String venueCode, List<MapCoord> wayPoints) {
        if (wayPoints != null ? wayPoints.size() <= 0 : true) return true;
        if (venueCode != null ? venueCode.length() <= 0 : true) return true;
        for (MapCoord point : wayPoints) {
            if (point.getVenueCode() != null ? point.getVenueCode().equals(venueCode) : false) return false;
        }
        return true;
    }

    @Override
    public Map<MapCoord, WiFiZone> cordToCircleMap() {
        return foundInZone;
    }
}
