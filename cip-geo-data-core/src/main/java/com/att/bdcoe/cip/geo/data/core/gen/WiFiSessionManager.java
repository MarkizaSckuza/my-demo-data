package com.att.bdcoe.cip.geo.data.core.gen;


import com.att.bdcoe.cip.geo.data.core.Circle;
import com.att.bdcoe.cip.geo.data.core.Coord;
import com.att.bdcoe.cip.geo.data.core.Route;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface WiFiSessionManager<W extends Circle,R extends Route,C extends Coord> {
    String getNewSessionId();
    void submitWiFiZones(Collection<W> wiFiZones);
    void addWiFiZonePoints(List<R> routes);
    Map<C,W> cordToCircleMap();
}
