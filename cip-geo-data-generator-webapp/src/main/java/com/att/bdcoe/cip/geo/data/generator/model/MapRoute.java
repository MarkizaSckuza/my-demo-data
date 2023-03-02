package com.att.bdcoe.cip.geo.data.generator.model;

import com.att.bdcoe.cip.geo.data.core.Route;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MapRoute extends Route<MapCoord> {
    public MapRoute() {
        super(0, null);
    }

    public MapRoute(int id) {
        super(id, null);
    }

    public MapRoute(int id, List<MapCoord> points) {
        super(id, points);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPoints(List<MapCoord> points) {
        this.points = points;
    }
}
