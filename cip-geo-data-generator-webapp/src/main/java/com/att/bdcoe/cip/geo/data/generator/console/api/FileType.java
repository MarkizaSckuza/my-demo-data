package com.att.bdcoe.cip.geo.data.generator.console.api;

public enum FileType {
    Scenario(0),
    Job(1);
    private static String[] fileExtension = {"scenario", "job"};
    private int value;

    FileType(int value) {
        this.value = value;
    }

    public static String getFileExtension(FileType i) {
        try {
            return fileExtension[i.getValue()];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return "*";
        }
    }

    public int getValue() {
        return value;
    }
}
