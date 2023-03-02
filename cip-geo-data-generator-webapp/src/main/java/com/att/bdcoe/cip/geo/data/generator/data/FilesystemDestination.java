package com.att.bdcoe.cip.geo.data.generator.data;

import com.att.bdcoe.cip.geo.data.generator.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

//import java.io.FSDataOutputStream;

@Component
public class FilesystemDestination implements DataDestination {
    private Log log = LogFactory.getLog(FilesystemDestination.class);

    private String outputPath;
    private String wiFiOutputPath;

    @Autowired
    public FilesystemDestination(Configuration configuration) {
        this.outputPath = configuration.getOutputPath();
        this.wiFiOutputPath = configuration.getWifiOutputPath();
        //File file = new File(this.outputPath);
        //file.mkdirs();
    }

    @Override
    public Writer getWriter(String id) throws IOException {

        boolean isFullUri = this.outputPath.contains(":");

        URI uri;

        try {
            uri = isFullUri ? new URI(this.outputPath) : new File(this.outputPath).toURI();
        } catch (URISyntaxException ex) {
            log.error("Wrong output path", ex);
            throw new IOException(ex);
        }

        if (uri.getScheme().equals("file")) {
            return getLocalFilesystemWriter(uri, id);
        }
        if (uri.getScheme().equals("hdfs")) {
            return getHDFSWriter(uri, id);
        }

        return null;
    }

    @Override
    public Writer getWiFiWriter(String id) throws IOException {

        boolean isFullUri = this.outputPath.contains(":");

        URI uri;

        try {
            uri = isFullUri ? new URI(this.wiFiOutputPath) : new File(this.wiFiOutputPath).toURI();
        } catch (URISyntaxException ex) {
            log.error("Wrong output path", ex);
            throw new IOException(ex);
        }

        if (uri.getScheme().equals("file")) {
            return getLocalFilesystemWriter(uri, id);
        }
        if (uri.getScheme().equals("hdfs")) {
            return getHDFSWriter(uri, id);
        }

        return null;
    }


    private Writer getHDFSWriter(URI uri, String id) throws IOException {

        System.setProperty("HADOOP_USER_NAME", "hdfs");

        org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
        conf.set("hadoop.job.ugi", "hdfs");
        conf.set("dfs.replication", "1");

        FileSystem hdfs = FileSystem.get(uri, conf);

        org.apache.hadoop.fs.Path file = new org.apache.hadoop.fs.Path(String.format("%s/%s.csv", this.outputPath, id));
        hdfs.mkdirs(file.getParent());

        FSDataOutputStream os = hdfs.create(file, true);


        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

        return br;

    }

    private Writer getLocalFilesystemWriter(URI uri, String id) throws IOException {

        File outputFile = new File(uri.getPath(), String.format("%s.csv", id));
        outputFile.getAbsoluteFile().getParentFile().mkdirs();
        FileWriter fileWriter = new FileWriter(outputFile, false);

        return fileWriter;
    }
}
