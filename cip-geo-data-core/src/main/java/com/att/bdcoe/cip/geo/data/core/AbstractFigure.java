package com.att.bdcoe.cip.geo.data.core;
import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractFigure {

    /**
     * Checks inclusion of the point in a Figure
     * @param point Coord object
     * @return True if current Figure includes the point
     */
    public abstract boolean contains(Coord point);

    /**
     * Filters the collection of externalRing and returns the externalRing included by current figure
     * @param points Collections of externalRing
     * @return Collection of externalRing that are included by current Figure
     */
    public Collection<Coord> trim(Collection<Coord> points) {

        Collection<Coord> filteredPoints = new ArrayList<>();

        for (Coord point : points)
        {
            if(!contains(point)) continue;
            filteredPoints.add(point);
        }
        return filteredPoints;
    }

//    /**
//     * Bounding box of the Figure
//     * @return Returns rectangle that represents the bounding box
//     */
//    public abstract Rectangle getBoundingBox();
}
