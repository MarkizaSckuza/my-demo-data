package com.att.bdcoe.cip.geo.data.generator.model;

import com.att.bdcoe.cip.geo.data.generator.data.JsonDateSerializer;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Options {

    private MovingObjects pedestrians;
    private MovingObjects bicyclists;
    private MovingObjects cars;

    @JsonSerialize(using = JsonDateSerializer.class)
    private Date startTime;
    private float trackingInterval;
    private String fileName;
    private boolean beginOnlyFromSpecialPoints;
    private double fluctuation;

    private List<MapRoute> routes;
    private List<WiFiZone> wifi;

    public static Options getDefault() {
        Options options = new Options();
        options.setPedestrians(new MovingObjects(10, 5));
        options.setBicyclists(new MovingObjects(10, 30));
        options.setCars(new MovingObjects(10, 60));

        options.setStartTime(new Date());
        options.setTrackingInterval(2f);

        options.setRoutes(new ArrayList<MapRoute>());
        options.setWifi(new ArrayList<WiFiZone>());

        return options;
    }

    public MovingObjects getPedestrians() {
        return pedestrians;
    }

    public void setPedestrians(MovingObjects pedestrians) {
        this.pedestrians = pedestrians;
    }

    public MovingObjects getBicyclists() {
        return bicyclists;
    }

    public void setBicyclists(MovingObjects bicyclists) {
        this.bicyclists = bicyclists;
    }

    public MovingObjects getCars() {
        return cars;
    }

    public void setCars(MovingObjects cars) {
        this.cars = cars;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public float getTrackingInterval() {
        return trackingInterval;
    }

    public void setTrackingInterval(float trackingInterval) {
        this.trackingInterval = trackingInterval;
    }

    public List<MapRoute> getRoutes() {
        return routes;
    }

    public void setRoutes(List<MapRoute> routes) {
        this.routes = routes;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isBeginOnlyFromSpecialPoints() {
        return beginOnlyFromSpecialPoints;
    }

    public void setBeginOnlyFromSpecialPoints(boolean beginOnlyFromSpecialPoints) {
        this.beginOnlyFromSpecialPoints = beginOnlyFromSpecialPoints;
    }

    public double getFluctuation() {
        return fluctuation;
    }

    public void setFluctuation(double fluctuation) {
        this.fluctuation = fluctuation;
    }

    public List<WiFiZone> getWifi() {
        return wifi;
    }

    public void setWifi(List<WiFiZone> wifi) {
        this.wifi = wifi;
    }
}
