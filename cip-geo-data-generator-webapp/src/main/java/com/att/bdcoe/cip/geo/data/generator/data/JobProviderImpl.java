package com.att.bdcoe.cip.geo.data.generator.data;


import com.att.bdcoe.cip.geo.data.generator.console.api.FileType;
import com.att.bdcoe.cip.geo.data.generator.console.api.FilesService;
import com.att.bdcoe.cip.geo.data.generator.model.FileName;
import com.att.bdcoe.cip.geo.data.generator.model.Job;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class JobProviderImpl implements JobProvider {
    @Autowired
    FilesService filesService;
    private Log log = LogFactory.getLog(getClass());
    private ObjectMapper mapper;

    public JobProviderImpl() {

        mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public Job read(String filename) throws IOException {
        File optionsFile = filesService.getFileByName(filename);
        if (!optionsFile.exists()) {
            log.warn(String.format("Options file %s not found.", filename));
            return Job.getDefault();
        }

        byte[] bytes = Files.readAllBytes(Paths.get(filename));
        String fileContents = new String(bytes, Charset.defaultCharset());
        Job job = mapper.readValue(fileContents, Job.class);
        return job;
    }

    @Override
    public List<Job> getAll() throws IOException {
        List<Job> returnList = new ArrayList<Job>();
        Collection<FileName> jobFilesList = filesService.getList(FileType.Job);


        for (FileName fileName : jobFilesList) {

            try {
                File file = filesService.getFileByName(fileName.getFileName());

                byte fileContent[] = new byte[(int) file.length()];

                FileInputStream fin = new FileInputStream(file);

                // Reads up to certain bytes of data from this input stream into an array of bytes.
                fin.read(fileContent);

                //create string from byte array
                returnList.add(mapper.readValue(new String(fileContent), Job.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return returnList;
    }

    @Override
    public void write(Job job) throws IOException {

        if (job.getFileName() == null || job.getFileName().trim().length() <= 0)
            job.setFileName(Job.defaultFileName);
        else if (!job.getFileName().endsWith(FileType.getFileExtension(FileType.Job)))
            job.setFileName(job.getFileName().concat(".").concat(FileType.getFileExtension(FileType.Job)));

        PrintWriter fileWriter = null;
        try {
            fileWriter = new PrintWriter(new FileWriter(filesService.getFileByName(job.getFileName())), true);
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(job);
            fileWriter.print(jsonString);
            fileWriter.close();
        }
//        catch(Exception ex){
//            log.error("Saving Job: ",ex);
//        }
        finally {
            if (fileWriter != null) fileWriter.close();
        }
    }

}
