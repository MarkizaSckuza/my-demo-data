package com.att.bdcoe.cip.geo.data.generator.console.api;

import com.att.bdcoe.cip.geo.data.generator.Configuration;
import com.att.bdcoe.cip.geo.data.generator.data.OptionsProvider;
import com.att.bdcoe.cip.geo.data.generator.model.Options;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/options", headers = "Accept=application/json")
public class OptionsServiceImpl implements OptionsService<Options> {

    private static Log log = LogFactory.getLog(OptionsServiceImpl.class);
    private final Configuration configuration;
    private OptionsProvider optionsProvider;
    private FilesService filesService;


    @Autowired
    public OptionsServiceImpl(Configuration configuration, OptionsProvider optionsProvider, FilesService filesService) {
        this.configuration = configuration;
        this.optionsProvider = optionsProvider;
        this.filesService = filesService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Options get() {
        try {
            Options options = optionsProvider.read(configuration.getDefaultOptionsFilename());
            return options;
        } catch (IOException ex) {
            log.error("Error reading generator options", ex);
            return Options.getDefault();
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public void saveAs(Options options) {
        try {
            if (options.getFileName() != null) {
                if (!options.getFileName().trim().endsWith(FileType.getFileExtension(FileType.Scenario)))
                    options.setFileName(options.getFileName().trim().concat(FileType.getFileExtension(FileType.Scenario)));
                optionsProvider.write(options.getFileName(), options);
            }
        } catch (IOException ex) {
            log.error("Error reading generator options", ex);
        }
    }
}
