package com.att.bdcoe.cip.geo.data.generator.model;

import java.util.List;

public class Step {
    private int id;
    private Output locationBasedOutput;
    private List<FileName> scenarios;
    private List<Output> outputs;

    public Step() {
    }

    public List<FileName> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<FileName> scenarios) {
        this.scenarios = scenarios;
    }

    public List<Output> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<Output> outputs) {
        this.outputs = outputs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Output getLocationBasedOutput() {
        return locationBasedOutput;
    }

    public void setLocationBasedOutput(Output locationBasedOutput) {
        this.locationBasedOutput = locationBasedOutput;
    }
}
