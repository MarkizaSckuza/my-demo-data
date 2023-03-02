package com.att.bdcoe.cip.geo.data.generator.console.api;

import com.att.bdcoe.cip.geo.data.generator.Configuration;
import com.att.bdcoe.cip.geo.data.generator.data.OptionsProvider;
import com.att.bdcoe.cip.geo.data.generator.model.Options;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.IOException;

@Path("/options")
@Produces({"application/json"})
@org.springframework.stereotype.Service
public class OptionsServiceImpl implements OptionsService<Options> {

    private static final Log LOG = LogFactory.getLog(OptionsServiceImpl.class);
    private final Configuration configuration;
    private OptionsProvider optionsProvider;

    @Autowired
    public OptionsServiceImpl(Configuration configuration, OptionsProvider optionsProvider) {
        this.configuration = configuration;
        this.optionsProvider = optionsProvider;
    }

    @GET
    public Options get() {
        try {
            return optionsProvider.read(configuration.getDefaultOptionsFilename());
        } catch (IOException ex) {
            LOG.error("Error reading generator options ", ex);
            return Options.getDefault();
        }
    }

    //	@POST
    public void save(Options options) {
        try {
            optionsProvider.write(configuration.getDefaultOptionsFilename(), options);
        } catch (IOException ex) {
            LOG.error("Error reading generator options ", ex);
        }
    }

    @POST
    public void saveAs(Options options) {
        try {
            if (options.getFileName() != null) {
                if (!options.getFileName().trim().endsWith(".json"))
                    options.setFileName(options.getFileName().trim().concat(".json"));
                optionsProvider.write(options.getFileName(), options);
            }
        } catch (IOException ex) {
            LOG.error("Error reading generator options ", ex);
        }
    }
}
