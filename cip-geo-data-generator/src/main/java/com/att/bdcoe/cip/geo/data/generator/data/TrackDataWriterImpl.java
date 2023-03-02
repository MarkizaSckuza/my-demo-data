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
    private static final Log LOG = LogFactory.getLog(TrackDataWriterImpl.class);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    private String outputPath;
    private String wifiOutputPath;

    @Autowired
    public TrackDataWriterImpl(Configuration configuration) {
        this.outputPath = configuration.getOutputPath();
        this.wifiOutputPath = configuration.getWifiOutputPath();

        createFiles();
    }

    @Override
    public void write(MapTrack track, Writer writer) throws IOException {
        LOG.debug(String.format("Writing track %s", track.getId()));

        for (MapTrackCoord coord : track.getCoords())
            writer.write(coord.writeToString());
    }

    @Override
    public void writeWiFiSession(String session, Writer writer) throws IOException {
        LOG.debug(String.format("Writing wifi %s", session));
        writer.write(session);
    }

    private void createFiles() {
        File file = new File(this.outputPath);
        file.mkdirs();

        file = new File(this.wifiOutputPath);
        file.mkdirs();
    }
}
