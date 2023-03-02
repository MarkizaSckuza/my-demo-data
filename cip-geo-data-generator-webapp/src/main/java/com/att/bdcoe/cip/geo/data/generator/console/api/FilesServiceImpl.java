package com.att.bdcoe.cip.geo.data.generator.console.api;


import com.att.bdcoe.cip.geo.data.generator.model.FileName;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileFilter;
import java.util.Collection;


@RestController
public class FilesServiceImpl implements FilesService {

    static Logger log = Logger.getLogger(FilesServiceImpl.class);
    @Value("${generator.metadata.folder}")
    private String path;
    private Collection<FileName> jobFilesList = null;


    @PostConstruct
    public void init() {
        File metadataFolder = new File(path);

        if (path == null) {
            return;
        }
        metadataFolder = new File(path);
        if (!metadataFolder.exists()) {
            if (!metadataFolder.mkdirs()) {
                log.error("Can't create " + path + " foleder.");
            } else {
                log.error("Metadata folder " + metadataFolder.getAbsolutePath() + " was created.");
            }
        }
    }

    @RequestMapping(value = "/files/jobs", produces = "application/json", method = RequestMethod.GET)
    public Collection<FileName> getListOfJobs() {
        return this.getList(FileType.Job);
    }

    @RequestMapping(value = "/files/scenarios", produces = "application/json", method = RequestMethod.GET)
    public Collection<FileName> getListOfScenarios() {
        return this.getList(FileType.Scenario);
    }

    public Collection<FileName> getList(FileType type) {

        if (type == null) return null;

        File metadataFolder = new File(path);

        File[] filesList;
        File[] listOfFiles = metadataFolder.listFiles();
        String pattern = null;


        pattern = ".*\\." + FileType.getFileExtension(type) + "$";
        FileFilter filter = new RegexFileFilter(pattern);
        filesList = metadataFolder.listFiles(filter);

        return FileName.arrayToList(filesList);
    }

    @RequestMapping(value = "/files", method = RequestMethod.DELETE)
    public boolean deleteFile(String fileName) {
        try {
            File file = new File(fileName);
            if (file.delete()) {
                System.out.println(file.getName() + " is deleted!");
                return true;
            } else {
                System.out.println("Delete operation is failed.");
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public File getFileByName(String fileName) {
        File metadataFolder = new File(path);

        File file = new File(metadataFolder.getAbsolutePath().concat(File.separator).concat(fileName));
        if (!file.exists())
            log.error("Trying to get " + metadataFolder.getAbsolutePath().concat(File.separator).concat(fileName) + " File not exist.");
        return file;
    }
}
