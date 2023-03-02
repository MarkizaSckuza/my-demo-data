package com.att.bdcoe.cip.geo.data.core.gen;


import com.att.bdcoe.cip.geo.data.core.Coord;
import com.att.bdcoe.cip.geo.data.core.TrackCoord;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class Behaviour<T extends TrackCoord>{
    protected Date startTime;
    protected Date endTime;
    protected Date endCoord;
    protected Coord startCoord;
    private String venueCode;
    protected double trackIntervalSeconds = 0.0;
    protected TrackBuilder<String, T> trackBuilder;
    protected Calendar calendar = Calendar.getInstance();


    public Behaviour(Date start,Coord startCoord,double trackIntervalSeconds,TrackBuilder<String, T> trackBuilder){
        calendar.setTime(start);
        calendar.add(Calendar.MINUTE, 1);

        this.startTime = start;
        this.endTime = calendar.getTime();
        this.startCoord = startCoord;
        this.trackIntervalSeconds = trackIntervalSeconds;
        this.trackBuilder = trackBuilder;
    }

    abstract public Date behave(List<TrackCoord> trackCoords);

    public String getVenueCode() {
        return venueCode;
    }

    public void setVenueCode(String venueCode) {
        this.venueCode = venueCode;
    }
}
