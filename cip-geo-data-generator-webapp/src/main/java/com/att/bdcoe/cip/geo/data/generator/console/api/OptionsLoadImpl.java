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
@RequestMapping(value = "/optionsload", headers = "Accept=application/json")
public class OptionsLoadImpl implements OptionsLoad<Options> {

    private static Log log = LogFactory.getLog(OptionsServiceImpl.class);
    private final Configuration configuration;
    private OptionsProvider optionsProvider;

    @Autowired
    public OptionsLoadImpl(Configuration configuration, OptionsProvider optionsProvider) {
        this.configuration = configuration;
        this.optionsProvider = optionsProvider;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Options load(String fileName) {
        try {
            Options options = optionsProvider.read(fileName);
            options.setFileName(fileName);
            return options;
        } catch (IOException ex) {
            log.error("Error reading generator options", ex);
            return Options.getDefault();
        }
    }
}
