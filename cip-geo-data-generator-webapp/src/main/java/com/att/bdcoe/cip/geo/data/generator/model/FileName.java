package com.att.bdcoe.cip.geo.data.generator.model;


import com.att.bdcoe.cip.geo.data.generator.gen.Selectable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileName extends Selectable {
    private String fileName;
    private String fileNameToShow;
    private boolean selected = false;
    private int id = 0;

    public FileName(String fileName) {
        this.setFileName(fileName);
    }

    public FileName() {
    }

    public static List<FileName> arrayToList(File[] file) {
        if (file == null || file.length == 0) return null;
        List<FileName> listToReturn = new ArrayList<FileName>();
        for (File fileFromList : file) {
            listToReturn.add(new FileName(fileFromList.getName()));
        }
        return listToReturn;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
        if (fileName != null) {
            String[] parts = fileName.split("[.]");
            String help[] = new String[parts.length - 1];
            System.arraycopy(parts, 0, help, 0, help.length);
            parts = help;
            StringBuilder builder = new StringBuilder();
            boolean first = true;
            for (String s : parts) {
                if (!first) builder.append(".");
                builder.append(s);
                first = false;
            }
            this.fileNameToShow = builder.toString();
        }
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof FileName))
            return false;
        if (obj == this)
            return true;
        if (this.fileName == null && ((FileName) obj).fileName == null) return false;
        return this.fileName.equals(((FileName) obj).fileName);
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
