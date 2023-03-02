package com.att.bdcoe.cip.geo.data.generator;

import org.apache.commons.cli.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ArgumentsConfiguration implements Configuration {
    public static String[] args;
    private static Log log = LogFactory.getLog(ArgumentsConfiguration.class);
    private boolean isConsoleMode = false;
    private boolean isOutputPartitioned = false;
    private int partitionsNumber = 1;
    private List<String> optionsFilename = Arrays.asList("options.json");
    private int consolePort = 8888;
    private String outputPath = "output";
    private String wifiOutputPath = "outputwifi";
    private String factoryClassName = "MapTrackBuilder";
    private String factoryPakageName = "com.att.bdcoe.cip.com.att.bdcoe.cip.geo.data.generator.gen";
    private boolean hasErrors;
    private String delimiter = null;

    public ArgumentsConfiguration() {
        Options options = getCommandLineOptions();
        try {

            CommandLineParser commandLineParser = new BasicParser();
            CommandLine commandLineArguments = commandLineParser.parse(options, args);

            isConsoleMode = commandLineArguments.hasOption('c');

            isOutputPartitioned = commandLineArguments.hasOption('s');

            String SPartitionsNumber = commandLineArguments.getOptionValue('s');
            if (SPartitionsNumber != null)
                partitionsNumber = Integer.parseInt(SPartitionsNumber);

            String port = commandLineArguments.getOptionValue('p');
            if (port != null)
                consolePort = Integer.parseInt(port);

            String outputPath = commandLineArguments.getOptionValue('o');
            if (outputPath != null)
                this.outputPath = outputPath;

            String wifiOutputPath = commandLineArguments.getOptionValue('w');
            if (wifiOutputPath != null)
                this.wifiOutputPath = wifiOutputPath;

            String factoryClassName = commandLineArguments.getOptionValue('f');
            if (factoryClassName != null)
                this.factoryClassName = factoryClassName;

            String[] optionsFiles = commandLineArguments.getArgs();
            if (optionsFiles != null && optionsFiles.length > 0)
                optionsFilename = Arrays.asList(optionsFiles);

            String delimiter = commandLineArguments.getOptionValue('d');
            if (delimiter != null)
                this.delimiter = delimiter;


        } catch (ParseException ex) {
            //log.warn("Unable to parse command line arguments", ex);
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("generator <options> [generator-options-files, ...]", options);
            hasErrors = true;
        }
    }

    private static Options getCommandLineOptions() {
        Options options = new Options();

        Option consoleOption = new Option("c", "console", false, "Start web console");
        options.addOption(consoleOption);

        Option consolePortOption = OptionBuilder
                .isRequired(false)
                .hasArg()
                .withArgName("port-number")
                .withDescription("Web console port number")
                .withLongOpt("port")
                .create("p");

        options.addOption(consolePortOption);

        Option outputFolder = OptionBuilder
                .isRequired(false)
                .hasArg()
                .withArgName("output-folder")
                .withDescription("Generator output folder name")
                .withLongOpt("output")
                .create("o");

        options.addOption(outputFolder);

        Option factoryClass = OptionBuilder
                .isRequired(false)
                .hasArg()
                .withArgName("factory-class-name")
                .withDescription("POJO factory class name")
                .withLongOpt("factory")
                .create("f");

        options.addOption(factoryClass);

        Option wifiOutput = OptionBuilder
                .isRequired(false)
                .hasArg()
                .withArgName("wifi-output")
                .withDescription("WiFi output folder")
                .withLongOpt("wifioutput")
                .create("w");

        options.addOption(wifiOutput);

        Option partitionOutput = OptionBuilder
                .isRequired(false)
                .hasArg()
                .withArgName("split")
                .withDescription("split output into partitions")
                .withLongOpt("split")
                .create("s");

        options.addOption(partitionOutput);
        Option delimiter = OptionBuilder
                .isRequired(false)
                .hasArg()
                .withArgName("delimiter")
                .withDescription("overwrite default delimiter")
                .withLongOpt("delimiter")
                .create("d");

        options.addOption(delimiter);

        return options;
    }

    @Override
    public boolean isConsoleMode() {
        return isConsoleMode;
    }

    @Override
    public String getDefaultOptionsFilename() {
        return "options.json";
    }

    @Override
    public List<String> getOptionFilenames() {
        return optionsFilename;
    }

    @Override
    public int getConsolePort() {
        return consolePort;
    }

    @Override
    public String getOutputPath() {
        return outputPath;
    }

    @Override
    public String getWifiOutputPath() {
        return wifiOutputPath;
    }

    @Override
    public boolean hasErrors() {
        return hasErrors;
    }

    @Override
    public String getFactoryClassName() {
        return factoryClassName;
    }

    public String getFactoryPakageName() {
        return factoryPakageName;
    }

    public void setFactoryPakageName(String factoryPakageName) {
        this.factoryPakageName = factoryPakageName;
    }


    @Override
    public boolean isOutputPartitioned() {
        return isOutputPartitioned;
    }

    public void setOutputPartitioned(boolean isOutputPartitioned) {
        this.isOutputPartitioned = isOutputPartitioned;
    }

    @Override
    public int getPartitionsNumber() {
        return partitionsNumber;
    }

    public void setPartitionsNumber(int partitionsNumber) {
        this.partitionsNumber = partitionsNumber;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }
}
