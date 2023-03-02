package com.att.bdcoe.cip.geo.data.generator.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovingObjects {

    private int count;
    private double speed;

    public MovingObjects() {
    }

    public MovingObjects(int count, double speed) {
        this.count = count;
        this.speed = speed;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
