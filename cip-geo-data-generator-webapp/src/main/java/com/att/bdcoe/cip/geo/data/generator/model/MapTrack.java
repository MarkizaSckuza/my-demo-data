package com.att.bdcoe.cip.geo.data.generator.model;

import com.att.bdcoe.cip.geo.data.core.Track;

import java.util.List;

public class MapTrack extends Track<String, MapTrackCoord> {
    public MapTrack(String s, List<MapTrackCoord> points) {
        super(s, points);
    }
}
