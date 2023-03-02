package com.att.bdcoe.cip.geo.data.core;

import java.util.ArrayList;
import java.util.List;

public class Track<TId, TCoord extends TrackCoord> {

    private static ArrayList emptyPoints = new ArrayList();
    protected TId id;
    protected List<TCoord> coords;

    public Track(TId id, List<TCoord> coords) {
        this.id = id;
        this.coords = coords;
    }

    public static Track empty() {
        return new Track(0, emptyPoints);
    }

    public TId getId() {
        return id;
    }

    public List<TCoord> getCoords() {
        return coords;
    }
}
