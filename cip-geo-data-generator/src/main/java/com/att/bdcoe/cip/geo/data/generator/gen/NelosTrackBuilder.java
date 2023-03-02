package com.att.bdcoe.cip.geo.data.generator.gen;

import com.att.bdcoe.cip.geo.data.core.Coord;
import com.att.bdcoe.cip.geo.data.core.Track;
import com.att.bdcoe.cip.geo.data.core.gen.CoordType;
import com.att.bdcoe.cip.geo.data.core.util.RandomString;
import com.att.bdcoe.cip.geo.data.generator.model.MapTrack;
import com.att.bdcoe.cip.geo.data.generator.model.MapTrackCoord;
import com.att.bdcoe.cip.geo.data.generator.model.NelosCoord;

import java.util.Date;
import java.util.List;
import java.util.Random;


public class NelosTrackBuilder extends MapTrackBuilder {

    private double fluctuation = 0.0;

    @Override
    public Track<String, MapTrackCoord> buildTrack(String s, List<MapTrackCoord> mapTrackCoords) {
        Random random = new Random();
        int maxAccuracy = 6;
        int minAccuracy = 3;
        int accuracy = maxAccuracy - minAccuracy + 1;

        mapTrackCoords.get(0).setLabel("Start");
        mapTrackCoords.get(mapTrackCoords.size() - 1).setLabel("Finish");

        RandomString randomString = new RandomString(13);
        String stationId = "*" + randomString.nextString();

        MapTrack returnMapTrack = new MapTrack(s, mapTrackCoords);

        for (MapTrackCoord cord : returnMapTrack.getCoords()) {
            ((NelosCoord) cord).setStationType("1");
            ((NelosCoord) cord).setStationId(stationId);
            ((NelosCoord) cord).setAltitude(0.0);
            ((NelosCoord) cord).setLocMethod(9);
            ((NelosCoord) cord).setLocAccuracy(minAccuracy + (random.nextInt() % accuracy));
            cord.setDelimiter(getDelimiter());
            //cord.fluctuate(random);
        }

        return returnMapTrack;
    }

    @Override
    public MapTrackCoord buildTrackCoord(Date timestamp, Coord coord, CoordType coordType) {
        NelosCoord nelosCoord = new NelosCoord(timestamp, coord.getLat(), coord.getLng(), coordType);
        nelosCoord.setFluctuation(this.fluctuation);
        return nelosCoord;
    }

    @Override
    public void setFluctuation(double fluctuation) {
        this.fluctuation = fluctuation;
    }
}


