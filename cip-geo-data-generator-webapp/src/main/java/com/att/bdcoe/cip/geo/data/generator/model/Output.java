package com.att.bdcoe.cip.geo.data.generator.model;


import com.att.bdcoe.cip.geo.data.generator.gen.Selectable;

public class Output extends Selectable {
    private String path;
    private PartitionType partitionType;
    private String separator;
    private String factoryName;
    private String description;
    private boolean location = false;

    public Output() {
    }

    public Output(String factoryName, String description, boolean location) {
        this.factoryName = factoryName;
        this.description = description;
        this.location = location;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public PartitionType getPartitionType() {
        return partitionType;
    }

    public void setPartitionType(PartitionType partitionType) {
        this.partitionType = partitionType;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isLocation() {
        return location;
    }

    public void setLocation(boolean location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || (this.factoryName == null || ((Output) obj).factoryName == null)) return false;
        return obj == this || this.factoryName.equals(((Output) obj).factoryName);
    }
}
