package com.att.bdcoe.cip.geo.data.generator.model;

import com.att.bdcoe.cip.geo.data.core.TrackCoord;
import com.att.bdcoe.cip.geo.data.core.gen.CoordType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MapTrackCoord extends TrackCoord implements Writable {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    String label;
    private String delimiter = null;

    public MapTrackCoord(Date timestamp, double lat, double lng, CoordType coordType) {
        super(timestamp, lat, lng);
        label = coordType == CoordType.None ? null : coordType.name();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String writeToString() {
        StringBuilder formatBuilder = new StringBuilder();

        formatBuilder.append("%s,");
        formatBuilder.append("%s,");
        formatBuilder.append("%s,");
        formatBuilder.append("%s\n");

        String[] formattedValues = new String[4];
        formattedValues[0] = this.getTimestamp() == null ? "" : encloseWithQuotes(dateFormat.format(this.getTimestamp()));
        formattedValues[1] = encloseWithQuotes(this.getLat());
        formattedValues[2] = encloseWithQuotes(this.getLng());
        formattedValues[3] = this.getLabel() == null ? "" : encloseWithQuotes(this.getLabel());

        return String.format(formatBuilder.toString(), formattedValues);
    }

    private String encloseWithQuotes(Object value) {
        if (value == null) return null;
        return String.format("\"%s\"", value);
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }
}
