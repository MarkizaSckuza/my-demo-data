package com.att.bdcoe.cip.geo.data.generator.gen.factory;


import com.att.bdcoe.cip.geo.data.core.Coord;
import com.att.bdcoe.cip.geo.data.core.Track;
import com.att.bdcoe.cip.geo.data.core.gen.CoordType;
import com.att.bdcoe.cip.geo.data.core.util.RandomString;
import com.att.bdcoe.cip.geo.data.generator.gen.MapTrackBuilder;
import com.att.bdcoe.cip.geo.data.generator.model.MapTrack;
import com.att.bdcoe.cip.geo.data.generator.model.MapTrackCoord;
import com.att.bdcoe.cip.geo.data.generator.model.Scada5Coord;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class Scada5TrackBuilder extends MapTrackBuilder {

    public static String description = "Scada 5 output";
    public static boolean location = true;
    private double fluctuation = 0.0;

    @Override
    public Track<String, MapTrackCoord> buildTrack(String s, List<MapTrackCoord> mapTrackCoords) {

        Random rn = new Random();
        int maxAccuracy = 6;
        int minAccuracy = 3;
        int accuracy = maxAccuracy - minAccuracy + 1;

        mapTrackCoords.get(0).setLabel("Start");
        mapTrackCoords.get(mapTrackCoords.size() - 1).setLabel("Finish");
        RandomString stationIdGenerator = new RandomString(13);
        String stationId = "*" + stationIdGenerator.nextString();
        String locIdentifier = "attexplenox.atlaga";
        MapTrack returnMapTrack = new MapTrack(s, mapTrackCoords);
        for (MapTrackCoord cord : returnMapTrack.getCoords()) {
            ((Scada5Coord) cord).setStationType("1");
            ((Scada5Coord) cord).setStationId(stationId);
            ((Scada5Coord) cord).setAltitude(0.0);
            ((Scada5Coord) cord).setLocMethod(9);
            ((Scada5Coord) cord).setLocAccuracy(minAccuracy + (rn.nextInt() % accuracy));
            ((Scada5Coord) cord).setDs(((Scada5Coord) cord).getTimestamp());
            ((Scada5Coord) cord).setLocIdentifier(locIdentifier);
            cord.setDelimiter(this.getDelimiter());
            //cord.fluctuate(rn);
        }

        return returnMapTrack;
    }

    @Override
    public MapTrackCoord buildTrackCoord(Date timestamp, Coord coord, CoordType coordType) {
        Scada5Coord nelosCoord = new Scada5Coord(timestamp, coord.getLat(), coord.getLng(), coordType);
        nelosCoord.setFluctuation(this.fluctuation);
        return nelosCoord;
    }

    @Override
    public void setFluctuation(double fluctuation) {
        this.fluctuation = fluctuation;
    }
}