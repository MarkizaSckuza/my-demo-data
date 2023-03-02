package com.att.bdcoe.cip.geo.data.generator.gen;

import com.att.bdcoe.cip.geo.data.core.Coord;
import com.att.bdcoe.cip.geo.data.core.gen.Behaviour;
import com.att.bdcoe.cip.geo.data.core.gen.BehaviourManager;
import com.att.bdcoe.cip.geo.data.core.gen.PoinType;
import com.att.bdcoe.cip.geo.data.core.gen.TrackBuilder;
import com.att.bdcoe.cip.geo.data.generator.model.MapTrackCoord;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BehaviourManagerImpl implements BehaviourManager<MapTrackCoord> {

    @Override
    public Behaviour get(PoinType type, Date start, Coord startCoord, double trackIntervalSeconds, TrackBuilder<String, MapTrackCoord> trackBuilder) {
        if (type != null && type.equals(PoinType.StayFor1Min))
            return new StayFor1Minute(start, startCoord, trackIntervalSeconds, trackBuilder);
        if (type != null && type.equals(PoinType.WiFi))
            return new StayInWiFi(start, startCoord, trackIntervalSeconds, trackBuilder);
        return null;
    }
}
