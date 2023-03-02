package com.att.bdcoe.cip.geo.data.generator.data;

import com.att.bdcoe.cip.geo.data.core.Track;

import java.io.IOException;
import java.io.Writer;

public interface TrackDataWriter<T extends Track> {
    void write(T track, Writer writer) throws IOException;

    void writeWiFiSesion(String sesion, Writer writer) throws IOException;
}
