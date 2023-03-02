package com.att.bdcoe.cip.geo.data.generator.gen;

import com.att.bdcoe.cip.geo.data.core.Coord;

import java.util.Comparator;


public class MapCordComparator<T extends Coord> implements Comparator<T> {

    private T start;

    public MapCordComparator(T start) {
        this.start = start;
    }

    @Override
    public int compare(T s1, T s2) {
        return Double.compare(start.distance(s1), start.distance(s2));
    }
}
