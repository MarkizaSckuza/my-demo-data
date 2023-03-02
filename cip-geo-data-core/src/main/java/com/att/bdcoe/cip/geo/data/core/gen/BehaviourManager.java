package com.att.bdcoe.cip.geo.data.core.gen;


import com.att.bdcoe.cip.geo.data.core.Coord;
import com.att.bdcoe.cip.geo.data.core.TrackCoord;

import java.util.Date;

public interface BehaviourManager<T extends TrackCoord> {
    Behaviour get(PoinType type, Date start, Coord startCoord, double trackIntervalSeconds, TrackBuilder<String, T> trackBuilder);
}
