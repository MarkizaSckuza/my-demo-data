package com.att.bdcoe.cip.geo.data.generator.model;


public enum PartitionType {
    Zero(0),
    One(1);
    private int value;

    PartitionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
