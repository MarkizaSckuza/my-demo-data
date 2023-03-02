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

public class StayInWiFi extends Behaviour<MapTrackCoord> {
    private long stayInMInutes = 2;

    public StayInWiFi(Date start, Coord startCoord, double trackIntervalSeconds, TrackBuilder<String, MapTrackCoord> trackBuilder) {
        super(start, startCoord, trackIntervalSeconds, trackBuilder);
    }

    @Override
    public Date behave(List<TrackCoord> trackCoords) {
        if (this.trackIntervalSeconds == 0) return this.startTime;
        if (trackCoords == null) trackCoords = new ArrayList<TrackCoord>();
        int numberOfPoints = (int) (stayInMInutes * 60 / this.trackIntervalSeconds);
        calendar.setTime(this.startTime);
        TrackCoord coord = null;
        for (int i = 1; i <= numberOfPoints; i++) {
            calendar.add(Calendar.SECOND, (int) trackIntervalSeconds);
            if (i == 1) {
                coord = trackBuilder.buildTrackCoord(calendar.getTime(), this.startCoord, CoordType.None);
                coord.setStayWiFi(true);
                coord.setVenueCode(this.startCoord.getVenueCode());
                coord.setWiFiStart(calendar.getTime());
                trackCoords.add(coord);
                continue;
            }
            trackCoords.add(trackBuilder.buildTrackCoord(calendar.getTime(), this.startCoord, CoordType.None));
        }
        if (coord != null) coord.setWifiStop(this.endTime);
        return this.endTime;
    }

    public long getStayInMInutes() {
        return stayInMInutes;
    }

    public void setStayInMInutes(long stayInMInutes) {
        this.stayInMInutes = stayInMInutes;
    }
}
