package com.att.bdcoe.cip.geo.data.generator.console.api;

import com.att.bdcoe.cip.geo.data.generator.model.FileName;
import com.att.bdcoe.cip.geo.data.generator.model.Output;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class MetaDataServiceImpl implements MetaDataService {

    private static final char DOT = '.';
    private static final char SLASH = '/';
    private static final String packageName = "com.att.bdcoe.cip.geo.data.generator.gen.factory";
    private static final String CLASS_SUFFIX = ".class";
    private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";
    private static Log log = LogFactory.getLog(MetaDataServiceImpl.class);
    List<String> listOfOutputs = null;
    @Autowired
    private FilesService filesService;

    private Collection<FileName> jobFilesList = null;
    private Collection<FileName> scenarioFilesList = null;
    private List<Output> allOutputs = null;

    public static List<Output> find(String scannedPackage) {
        String scannedPath = scannedPackage.replace(DOT, SLASH);
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
        if (scannedUrl == null) {
            throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage));
        }

        List<Output> classes = new ArrayList<>();
        try {
            File scannedDir = new File(URIUtil.decode(scannedUrl.getFile()));
            for (File file : scannedDir.listFiles()) {
                Class<?> cl;
                cl = Class.forName(scannedPackage.concat(".").concat(file.getName().replace(".class", "")));
                classes.add(new Output(file.getName().replace(".class", ""),
                        cl.getField("description") != null ? cl.getField("description").toGenericString() : null,
                        cl.getField("location") != null ? cl.getField("location").getBoolean(null) : false));

            }
        } catch (URIException | ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            log.error("find :", e);
        }
        return classes;
    }

    @PostConstruct
    public void init() {
        this.setJobFilesList(filesService.getList(FileType.Job));
        this.setScenarioFilesList(filesService.getList(FileType.Scenario));
        this.setAllOutputs(find(packageName));
    }

    public Collection<FileName> getJobFilesList() {
        return jobFilesList;
    }

    public void setJobFilesList(Collection<FileName> jobFilesList) {
        this.jobFilesList = jobFilesList;
    }

    public Collection<FileName> getScenarioFilesList() {
        return scenarioFilesList;
    }

    public void setScenarioFilesList(Collection<FileName> scenarioFilesList) {
        this.scenarioFilesList = scenarioFilesList;
    }

    public List<Output> getAllOutputs() {
        return allOutputs;
    }

    public void setAllOutputs(List<Output> allOutputs) {
        this.allOutputs = allOutputs;
    }

    @Override
    public List<Output> getLocationOutputs() {
        if (allOutputs == null) return null;
        List<Output> returnList = new ArrayList<Output>();
        for (Output output : allOutputs) {
            if (output.isLocation()) returnList.add(output);
        }
        return returnList;
    }

    @Override
    public List<Output> getNonLocationOutputs() {
        if (allOutputs == null) return null;
        List<Output> returnList = new ArrayList<Output>();
        for (Output output : allOutputs) {
            if (!output.isLocation()) returnList.add(output);
        }
        return returnList;
    }
}
