package com.att.bdcoe.cip.geo.data.generator.data;

import com.att.bdcoe.cip.geo.data.generator.Configuration;
import com.att.bdcoe.cip.geo.data.generator.model.MapTrack;
import com.att.bdcoe.cip.geo.data.generator.model.MapTrackCoord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;

@Component
public class TrackDataWriterImpl extends Configured implements TrackDataWriter<MapTrack> {

    public static final String FS_PARAM_NAME = "fs.defaultFS";
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    private Log log = LogFactory.getLog(TrackDataWriterImpl.class);
    private String outputPath;
    private String wifiOutputPath;

    @Autowired
    public TrackDataWriterImpl(Configuration configuration) {
        this.outputPath = configuration.getOutputPath();
        File file = new File(this.outputPath);
        file.mkdirs();
        this.wifiOutputPath = configuration.getWifiOutputPath();
        file = new File(this.wifiOutputPath);
        file.mkdirs();
    }

    @Override
    public void write(MapTrack track, Writer writer) throws IOException {
        log.debug(String.format("Writing track %s", track.getId()));

        for (MapTrackCoord coord : track.getCoords())
            writer.write(coord.writeToString());
    }

    @Override
    public void writeWiFiSesion(String sesion, Writer writer) throws IOException {
        log.debug(String.format("Writing wifi %s", sesion));
        writer.write(sesion);
    }
}
