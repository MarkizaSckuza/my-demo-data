package com.att.bdcoe.cip.geo.data.generator.data;

import com.att.bdcoe.cip.geo.data.generator.console.api.FilesService;
import com.att.bdcoe.cip.geo.data.generator.model.Options;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class JSONFileOptionsProvider implements OptionsProvider {

    @Autowired
    FilesService filesService;
    private Log log = LogFactory.getLog(getClass());
    private ObjectMapper mapper;

    public JSONFileOptionsProvider() {

        mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public Options read(String filename) throws IOException {
        File optionsFile = filesService.getFileByName(filename);
        if (!optionsFile.exists()) {
            log.warn(String.format("Options file %s not found.", filename));
            return Options.getDefault();
        }

        byte[] bytes = Files.readAllBytes(Paths.get(filename));
        String fileContents = new String(bytes, Charset.defaultCharset());
        Options options = mapper.readValue(fileContents, Options.class);
        return options;
    }

    @Override
    public void write(String filename, Options options) throws IOException {
        PrintWriter fileWriter = null;
        try {
            fileWriter = new PrintWriter(new FileWriter(filesService.getFileByName(filename)), true);
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(options);
            fileWriter.print(jsonString);
            fileWriter.close();
        } finally {
            if (fileWriter != null) fileWriter.close();
        }
    }
}
