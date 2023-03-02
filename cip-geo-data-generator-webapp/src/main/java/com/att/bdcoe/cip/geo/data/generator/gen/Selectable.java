package com.att.bdcoe.cip.geo.data.generator.gen;


import java.util.ArrayList;
import java.util.List;

public class Selectable {

    private boolean selected = false;

    public static <T extends Selectable> List<T> getSlectedOnly(List<T> list) {
        if (list == null) return null;
        List<T> selectedList = new ArrayList<T>();
        if (!list.isEmpty()) {
            for (T item : list) {
                if (item.isSelected()) selectedList.add(item);
            }
        }
        return selectedList;
    }

    public static <T extends Selectable> List<T> mergeWithFullList(List<T> list, List<T> fullList) {
        if (list == null || fullList == null) return null;
        ArrayList<T> returnList = new ArrayList<T>(fullList);

        if (!list.isEmpty() && !returnList.isEmpty()) {
            for (T item : list) {
                if (returnList.contains(item)) {
                    item.setSelected(true);
                    returnList.set(returnList.indexOf(item), item);
                }
            }
        } else if (list.isEmpty()) {
            return returnList;
        } else if (returnList.isEmpty()) {
            return list;
        }

        return returnList;

    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
