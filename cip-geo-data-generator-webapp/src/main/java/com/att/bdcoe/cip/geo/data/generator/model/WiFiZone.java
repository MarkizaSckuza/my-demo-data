package com.att.bdcoe.cip.geo.data.generator.model;

import com.att.bdcoe.cip.geo.data.core.Circle;
import com.att.bdcoe.cip.geo.data.core.Coord;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WiFiZone extends Circle {

    private final static double defaultRadius = 0.001916654;
    private int id;

    private double lat;
    private double lng;
    @JsonIgnore
    private String venueCode;
    private WhenCrossing whenCrossing;
    private double timeSpent;

    public WiFiZone() {
        this.setRadius(defaultRadius);
    }

    public WiFiZone(double x, double y, double radius) {
        super(x, y, radius);
    }

    public WiFiZone(Coord center, double radius) {
        super(center, radius);
    }

    public WiFiZone(Circle circle) {
        super(circle.getCenter(), circle.getRadius());
    }

    public WiFiZone(double x, double y, double radius, WhenCrossing whenCrossing, double timeSpent) {
        super(x, y, radius);
        this.whenCrossing = whenCrossing;
        this.timeSpent = timeSpent;
    }

    public WiFiZone(int id, double lat, double lng, WhenCrossing whenCrossing, double timeSpent) {
        super(lat, lng, WiFiZone.defaultRadius);
        this.whenCrossing = whenCrossing;
        this.timeSpent = timeSpent;
        this.setId(id);
    }
    public WiFiZone(Coord center, double radius, WhenCrossing whenCrossing, double timeSpent) {
        super(center, radius);
        this.whenCrossing = whenCrossing;
        this.timeSpent = timeSpent;
    }

    public WhenCrossing getWhenCrossingAsEnum() {
        return whenCrossing;
    }

    public int getWhenCrossing() {
        return whenCrossing.getValue();
    }

    public void setWhenCrossing(WhenCrossing whenCrossing) {
        this.whenCrossing = whenCrossing;
    }

    public double getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(double timeSpent) {
        this.timeSpent = timeSpent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public boolean contains(Coord point) {
        if (this.getCenter() == null) this.center = new Coord(this.getLat(), this.getLng());
        return super.contains(point);
    }

    public String getVenueCode() {
        return venueCode;
    }

    public void setVenueCode(String venueCode) {
        this.venueCode = venueCode;
    }
}
