package com.att.bdcoe.cip.geo.data.generator.model;


public enum WhenCrossing {
    STAYALL(0), STAYSELECTED(1);
    private int value;

    WhenCrossing(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
