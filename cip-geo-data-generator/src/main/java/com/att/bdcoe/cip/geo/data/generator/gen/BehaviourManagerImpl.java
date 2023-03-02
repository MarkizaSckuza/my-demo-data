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
        Behaviour behaviour = null;
        if (type != null) {
            switch (type) {
                case StayFor1Min: behaviour = new StayFor1Minute(start, startCoord, trackIntervalSeconds, trackBuilder); break;
                case WiFi: behaviour = new StayInWiFi(start, startCoord, trackIntervalSeconds, trackBuilder); break;
            }
        }
        return behaviour;
    }
}
