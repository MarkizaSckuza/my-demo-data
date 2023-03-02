package com.att.bdcoe.cip.geo.data.generator.data;

import com.att.bdcoe.cip.geo.data.generator.model.Options;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class JSONFileOptionsProvider implements OptionsProvider {

    private static final Log LOG = LogFactory.getLog(JSONFileOptionsProvider.class);

    private ObjectMapper mapper;

    public JSONFileOptionsProvider() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public Options read(String filename) throws IOException {
        File optionsFile = new File(filename);
        if (!optionsFile.exists()) {
            LOG.warn(String.format("Options file %s not found. ", filename));
            return Options.getDefault();
        }

        byte[] bytes = Files.readAllBytes(Paths.get(filename));
        String fileContents = new String(bytes, Charset.defaultCharset());
        return mapper.readValue(fileContents, Options.class);
    }

    @Override
    public void write(String filename, Options options) throws IOException {
        PrintWriter fileWriter = null;
        try {
            fileWriter = new PrintWriter(filename);
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(options);
            fileWriter.print(jsonString);
            fileWriter.close();
        } finally {
            if (fileWriter != null) fileWriter.close();
        }
    }
}
