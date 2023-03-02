package com.att.bdcoe.cip.geo.data.generator.console;

import com.att.bdcoe.cip.geo.data.generator.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.util.component.AbstractLifeCycle;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ServerListener extends AbstractLifeCycle.AbstractLifeCycleListener {

    private static final Log LOG = LogFactory.getLog(ServerListener.class);

    private String consoleUrl;

    public ServerListener(Configuration configuration) {
        consoleUrl = String.format("http://localhost:%d/index.html", configuration.getConsolePort());
    }

    private static void openBrowser(String url) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException ex) {
                LOG.error("Error opening default browser ", ex);
            }
        } else {
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + url);
            } catch (IOException ex) {
                LOG.error("Error opening default browser ", ex);
            }
        }
    }

    public void lifeCycleStarted(org.eclipse.jetty.util.component.LifeCycle event) {
        openBrowser(consoleUrl);
    }
}
