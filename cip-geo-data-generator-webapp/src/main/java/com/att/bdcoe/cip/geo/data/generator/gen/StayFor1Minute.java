package com.att.bdcoe.cip.geo.data.generator.gen;


import com.att.bdcoe.cip.geo.data.core.Coord;
import com.att.bdcoe.cip.geo.data.core.TrackCoord;
import com.att.bdcoe.cip.geo.data.core.gen.Behaviour;
import com.att.bdcoe.cip.geo.data.core.gen.CoordType;
import com.att.bdcoe.cip.geo.data.core.gen.TrackBuilder;
import com.att.bdcoe.cip.geo.data.generator.model.MapTrackCoord;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StayFor1Minute extends Behaviour<MapTrackCoord> {

    public StayFor1Minute(Date start, Coord startCoord, double trackIntervalSeconds, TrackBuilder<String, MapTrackCoord> trackBuilder) {
        super(start, startCoord, trackIntervalSeconds, trackBuilder);
    }

    @Override
    public Date behave(List<TrackCoord> trackCoords) {
        if (this.trackIntervalSeconds == 0) return this.startTime;
        if (trackCoords == null) trackCoords = new ArrayList<TrackCoord>();
        int numberOfPoints = (int) (60 / this.trackIntervalSeconds);
        calendar.setTime(this.startTime);

        for (int i = 1; i <= numberOfPoints; i++) {
            calendar.add(Calendar.SECOND, (int) trackIntervalSeconds);
            trackCoords.add(trackBuilder.buildTrackCoord(calendar.getTime(), this.startCoord, CoordType.None));
        }
        return this.endTime;
    }
}
