package com.att.bdcoe.cip.geo.data.generator.model;

import com.att.bdcoe.cip.geo.data.generator.console.api.FileType;

import java.util.ArrayList;
import java.util.List;

public class Job {

    public static final String defaultFileName = "DefaultOutput." + FileType.getFileExtension(FileType.Job);

    private List<Step> steps;
    private String fileName;
    private String fileNameToShow;
    private int id;

    public Job() {
    }

    public static Job getDefault() {
        Job returnObject = new Job();
        returnObject.setSteps(new ArrayList<Step>());
        return returnObject;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {

        this.fileName = fileName;
        this.fileNameToShow = fileName != null ? fileName.replace("." + FileType.getFileExtension(FileType.Job), "") : null;
    }

    public String getFileNameToShow() {
        return fileNameToShow;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
