package com.att.bdcoe.cip.geo.data.core;

import java.util.Date;

public class TrackCoord extends Coord {

	private Date timestamp;

    private boolean stayWiFi = false;
    private Date wiFiStart = null;
    private Date wifiStop = null;

	public TrackCoord(Date timestamp, double lat, double lng) {
		super(lat, lng);
		this.timestamp = timestamp;
	}

	public Date getTimestamp() {
		return timestamp;
	}


    public boolean isStayWiFi() {
        return stayWiFi;
    }

    public void setStayWiFi(boolean stayWiFi) {
        this.stayWiFi = stayWiFi;
    }

    public Date getWiFiStart() {
        return wiFiStart;
    }

    public void setWiFiStart(Date wiFiStart) {
        this.wiFiStart = wiFiStart;
    }

    public Date getWifiStop() {
        return wifiStop;
    }

    public void setWifiStop(Date wifiStop) {
        this.wifiStop = wifiStop;
    }
}
