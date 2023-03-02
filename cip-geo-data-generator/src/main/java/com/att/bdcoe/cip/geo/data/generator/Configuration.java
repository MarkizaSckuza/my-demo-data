package com.att.bdcoe.cip.geo.data.generator;

import java.util.List;

public interface Configuration {
    boolean isConsoleMode();

    String getDefaultOptionsFilename();

    List<String> getOptionFileNames();

    int getConsolePort();

    String getOutputPath();

    String getFactoryClassName();

    String getFactoryPackageName();

    String getWifiOutputPath();

    boolean hasErrors();

    boolean isOutputPartitioned();

    int getPartitionsNumber();

    String getDelimiter();
}
