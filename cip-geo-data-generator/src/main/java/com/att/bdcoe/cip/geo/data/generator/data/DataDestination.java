package com.att.bdcoe.cip.geo.data.generator.data;

import java.io.IOException;
import java.io.Writer;


public interface DataDestination {
    Writer getWriter(String id) throws IOException;

    Writer getWiFiWriter(String id) throws IOException;
}

