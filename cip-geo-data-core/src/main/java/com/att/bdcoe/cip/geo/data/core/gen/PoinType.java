package com.att.bdcoe.cip.geo.data.core.gen;


public enum PoinType {
    None(0),
    Begin(1),
    StayFor1Min(2),
    WiFi(3);
    private int value;
    private PoinType(int value){
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
