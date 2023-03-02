package com.att.bdcoe.cip.geo.data.generator;

import com.att.bdcoe.cip.geo.data.generator.console.GeneratorConsole;
import com.att.bdcoe.cip.geo.data.generator.gen.DataGenerator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GeneratorApplication {

    private static final Log LOG = LogFactory.getLog(GeneratorApplication.class);

    public static void main(String[] args) {
        try {

            ArgumentsConfiguration.args = args;

            ApplicationContext context = new ClassPathXmlApplicationContext(
                    new String[]{
                            "classpath:applicationContext.xml",
                    }
            );

            Configuration configuration = context.getBean(Configuration.class);

            if (configuration.hasErrors()) return;

            if (configuration.isConsoleMode()) {
                LOG.info("Started in web console mode");
                GeneratorConsole generatorConsole = context.getBean(GeneratorConsole.class);
                generatorConsole.start();
                // TODO: shutdown the app by console command
            } else {
                LOG.info("Started in general command line mode");
                DataGenerator generator = context.getBean(DataGenerator.class);
                generator.start();
                LOG.info("Done.");
            }
        } catch (Exception ex) {
            LOG.fatal("Fatal Error", ex);
        }
    }
}
