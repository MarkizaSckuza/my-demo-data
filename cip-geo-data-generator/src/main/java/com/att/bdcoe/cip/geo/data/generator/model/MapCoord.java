package com.att.bdcoe.cip.geo.data.generator.model;

import com.att.bdcoe.cip.geo.data.core.Coord;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class MapCoord extends Coord {

    private int id;

    public MapCoord() {
        super(0, 0);
    }

    public MapCoord(Coord coord) {
        super(coord.getLat(), coord.getLng());
    }

    public MapCoord(int id, double lat, double lng) {
        super(lat, lng);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
