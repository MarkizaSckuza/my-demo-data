package com.att.bdcoe.cip.geo.data.generator.gen;

import com.att.bdcoe.cip.geo.data.core.Coord;
import com.att.bdcoe.cip.geo.data.core.Track;
import com.att.bdcoe.cip.geo.data.core.gen.CoordType;
import com.att.bdcoe.cip.geo.data.core.gen.TrackBuilder;
import com.att.bdcoe.cip.geo.data.generator.model.MapTrack;
import com.att.bdcoe.cip.geo.data.generator.model.MapTrackCoord;

import java.util.Date;
import java.util.List;

public class MapTrackBuilder implements TrackBuilder<String, MapTrackCoord> {

    private double fluctuation = 0.0;
    private String delimiter = null;

    @Override
    public Track<String, MapTrackCoord> buildTrack(String s, List<MapTrackCoord> mapTrackCoords) {
        mapTrackCoords.get(0).setLabel("Start");
        mapTrackCoords.get(mapTrackCoords.size() - 1).setLabel("Finish");
        return new MapTrack(s, mapTrackCoords);
    }

    @Override
    public MapTrackCoord buildTrackCoord(Date timestamp, Coord coord, CoordType coordType) {
        MapTrackCoord mapTrackCoord = new MapTrackCoord(timestamp, coord.getLat(), coord.getLng(), coordType);
        mapTrackCoord.setFluctuation(this.fluctuation);
        return new MapTrackCoord(timestamp, coord.getLat(), coord.getLng(), coordType);
    }

    public double getFluctuation() {
        return fluctuation;
    }

    @Override
    public void setFluctuation(double fluctuation) {
        this.fluctuation = fluctuation;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }
}
