package com.att.bdcoe.cip.geo.data.generator.console;

import com.att.bdcoe.cip.geo.data.generator.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static sun.net.www.protocol.http.AuthCacheValue.Type.Server;

@Component
public class WebGeneratorConsole implements GeneratorConsole {

    private static final Log LOG = LogFactory.getLog(WebGeneratorConsole.class);

    private Configuration configuration;

    @Autowired
    public WebGeneratorConsole(Configuration configuration) {
        this.configuration = configuration;
    }

    public void start() {
        try {
            Server server = new Server(configuration.getConsolePort());

            WebAppContext context = new WebAppContext();
            context.setContextPath("/");

            String resLocation = WebGeneratorConsole.class.getResource("/webapp").toString();
            context.setResourceBase(resLocation);

            String webXmlLocation = WebGeneratorConsole.class.getResource("/webapp/WEB-INF/web.xml").toString();
            context.setDescriptor(webXmlLocation);

            server.setHandler(context);

            server.addLifeCycleListener(new ServerListener(configuration));

            server.start();
            server.dumpStdErr();
            server.join();

        } catch (Exception ex) {
            LOG.fatal("Web console error ", ex);
        }
    }
}
