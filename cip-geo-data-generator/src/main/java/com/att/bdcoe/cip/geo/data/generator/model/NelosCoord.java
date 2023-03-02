package com.att.bdcoe.cip.geo.data.generator.model;

import com.att.bdcoe.cip.geo.data.core.Track;
import com.att.bdcoe.cip.geo.data.core.gen.CoordType;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NelosCoord extends MapTrackCoord {

    private String label;
    private static final String DEFAULT_DELIMITER = "|";


    private String stationType;
    private String StationId;
    private double altitude;
    private int locMethod;
    private double locAccuracy;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'@'HH:mm:ss");
    private final SimpleDateFormat dsFormat = new SimpleDateFormat("yyyyMMddHH");


    public NelosCoord(Date timestamp, double lat, double lng, CoordType coordType) {
        super(timestamp, lat, lng, coordType);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getStationType() {
        return stationType;
    }

    public void setStationType(String stationType) {
        this.stationType = stationType;
    }

    public String getStationId() {
        return StationId;
    }

    public void setStationId(String stationId) {
        StationId = stationId;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public int getLocMethod() {
        return locMethod;
    }

    public void setLocMethod(int loc_method) {
        this.locMethod = locMethod;
    }

    public double getLocAccuracy() {
        return locAccuracy;
    }

    public void setLocAccuracy(double locAccuracy) {
        this.locAccuracy = locAccuracy;
    }

    public static Track<String, NelosCoord> getTrack(String stationType, String StationId, double altitude, int loc_method, double loc_accuracy, String s, List<MapTrackCoord> mapTrackCoords) {
        MapTrack mapTrack = new MapTrack(s, mapTrackCoords);

        return null;
    }

    @Override
    public String getDelimiter() {
        if (super.getDelimiter() != null)
            return super.getDelimiter();
        else
            return DEFAULT_DELIMITER;
    }

    @Override
    public String writeToString() {
        String formatBuilder = "%s" + this.getDelimiter() +
                "%s" + this.getDelimiter() +
                "%s" + this.getDelimiter() +
                "%s" + this.getDelimiter() +
                "%s" + this.getDelimiter() +
                "%s" + this.getDelimiter() +
                "%S" + this.getDelimiter() +
                "%S" + this.getDelimiter() +
                "%s\n";


        String[] formattedValues = new String[9];
        formattedValues[0] = this.getTimestamp() == null ? "" : dateFormat.format(this.getTimestamp());
        formattedValues[1] = encloseWithoutQuotes(this.getStationType());
        formattedValues[2] = encloseWithoutQuotes(this.getStationId());
        formattedValues[3] = encloseWithoutQuotes(this.getLat());
        formattedValues[4] = encloseWithoutQuotes(this.getLng());
        formattedValues[5] = encloseWithoutQuotes(this.getAltitude());
        formattedValues[6] = formatDouble(this.getLocMethod());
        formattedValues[7] = formatDouble(this.getLocAccuracy());
        formattedValues[8] = this.getTimestamp() == null ? "" : dsFormat.format(this.getTimestamp());


        //formattedValues[5] = this.getLabel()==null? "" : this.getLabel();

        return String.format(formatBuilder, (Object[]) formattedValues);
    }

    private String encloseWithoutQuotes(Object value) {
        if (value == null) return null;
        return String.format("%s", value);
    }

    private String formatDouble(Object value) {
        if (value == null) return null;
        DecimalFormat df = new DecimalFormat("#");
        return df.format(value);
    }
}

