package com.att.bdcoe.cip.geo.data.generator.console.api;


import com.att.bdcoe.cip.geo.data.generator.data.OptionsProvider;
import com.att.bdcoe.cip.geo.data.generator.model.Options;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/optionsload")
@Service
public class OptionsLoadImpl implements OptionsLoad<Options> {

    private static final Log LOG = LogFactory.getLog(OptionsServiceImpl.class);
    private OptionsProvider optionsProvider;

    @Autowired
    public OptionsLoadImpl(OptionsProvider optionsProvider) {
        this.optionsProvider = optionsProvider;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({"application/json"})
    public Options load(String fileName) {
        try {
            Options options = optionsProvider.read(fileName);
            options.setFileName(fileName);
            return options;
        } catch (IOException ex) {
            LOG.error("Error reading generator options ", ex);
            return Options.getDefault();
        }
    }
}
