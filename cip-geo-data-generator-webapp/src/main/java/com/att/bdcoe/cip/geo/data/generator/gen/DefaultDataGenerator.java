package com.att.bdcoe.cip.geo.data.generator.gen;

import com.att.bdcoe.cip.geo.data.core.Route;
import com.att.bdcoe.cip.geo.data.core.TrackCoord;
import com.att.bdcoe.cip.geo.data.core.WiFiSession;
import com.att.bdcoe.cip.geo.data.core.gen.BehaviourManager;
import com.att.bdcoe.cip.geo.data.core.gen.Generator;
import com.att.bdcoe.cip.geo.data.core.gen.GraphBasedGenerator;
import com.att.bdcoe.cip.geo.data.core.gen.WiFiSessionManager;
import com.att.bdcoe.cip.geo.data.core.util.DataUtil;
import com.att.bdcoe.cip.geo.data.core.util.RandomUpperAndNumberString;
import com.att.bdcoe.cip.geo.data.generator.Configuration;
import com.att.bdcoe.cip.geo.data.generator.data.DataDestination;
import com.att.bdcoe.cip.geo.data.generator.data.OptionsProvider;
import com.att.bdcoe.cip.geo.data.generator.data.TrackDataWriter;
import com.att.bdcoe.cip.geo.data.generator.model.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class DefaultDataGenerator implements DataGenerator {

    private final Configuration configuration;
    RandomUpperAndNumberString mac = new RandomUpperAndNumberString(63);
    OptionsProvider optionsProvider;
    Generator<String, MapCoord, MapTrackCoord> generator;
    TrackDataWriter<MapTrack> trackDataWriter;
    Options options = null;
    DataDestination destination;
    WiFiSessionManager<WiFiZone, MapRoute, MapCoord> wiFiSesionManager;
    private Log log = LogFactory.getLog(getClass());
    private BehaviourManager behaviourManager;
    private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHH");

    @Autowired
    public DefaultDataGenerator(Configuration configuration, OptionsProvider optionsProvider, TrackDataWriter<MapTrack> trackDataWriter, BehaviourManager behaviourManager, DataDestination destination, WiFiSessionManager<WiFiZone, MapRoute, MapCoord> wiFiSesionManager) {
        this.configuration = configuration;
        this.optionsProvider = optionsProvider;
        this.trackDataWriter = trackDataWriter;
        this.behaviourManager = behaviourManager;
        this.destination = destination;
        this.wiFiSesionManager = wiFiSesionManager;
    }

    @Override
    public void start() throws IOException {

        List<String> optionFileNames = configuration.getOptionFilenames();

        for (String filename : optionFileNames) {
            processOptionsFile(filename);
        }
    }

    private void processOptionsFile(String filename) throws IOException {
        File file = new File(filename);
        String name = file.getName();

        log.info(String.format("Processing file %s", name));

        this.options = optionsProvider.read(file.getAbsolutePath());
        List<MapRoute> routes = options.getRoutes();

        wiFiSesionManager.submitWiFiZones(options.getWifi());
        wiFiSesionManager.addWiFiZonePoints(routes);

        if (routes.size() <= 0) {
            log.error("No routes defined. Skipping.");
            return;
        }

        log.info(String.format("Generating tracks for %d moving objects...",
                options.getPedestrians().getCount() + options.getBicyclists().getCount() + options.getCars().getCount()));

        name = name.substring(0, name.indexOf('.') < 0 ? name.length() : name.indexOf('.'));

        if (configuration.isOutputPartitioned()) {
            log.info(String.format("Output is partitioned. Building %d partitions...", configuration.getPartitionsNumber()));
            for (int i = 0; i < configuration.getPartitionsNumber(); i++) {
                Date startTime = getPartitionStartTime(options.getStartTime(), i);
                String partitionFolderName = "ds=" + DATE_FORMAT.format(startTime);
                generateDataForMovingObjects(name + "/" + partitionFolderName + "/P", startTime, options.getTrackingInterval(), options.getPedestrians(), routes);
                generateDataForMovingObjects(name + "/" + partitionFolderName + "/B", startTime, options.getTrackingInterval(), options.getBicyclists(), routes);
                generateDataForMovingObjects(name + "/" + partitionFolderName + "/C", startTime, options.getTrackingInterval(), options.getCars(), routes);
                log.info(String.format("Partition %d is done ...", i + 1));
            }

        } else {
            generateDataForMovingObjects(name + "/P", options.getStartTime(), options.getTrackingInterval(), options.getPedestrians(), routes);
            generateDataForMovingObjects(name + "/B", options.getStartTime(), options.getTrackingInterval(), options.getBicyclists(), routes);
            generateDataForMovingObjects(name + "/C", options.getStartTime(), options.getTrackingInterval(), options.getCars(), routes);
        }
    }

    private Date getPartitionStartTime(Date startTime, int partition) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);

        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + partition);
        return cal.getTime();
    }

    private void generateDataForMovingObjects(
            String prefix,
            Date startTime,
            double trackingInterval,
            MovingObjects movingObjects,
            List<MapRoute> routes) throws IOException {

        int count = movingObjects.getCount();

        String[] realms = {"ATT iPhone client on Wayport",
                "EDO Placeholder 1 - Wayport",
                "Android - AT&T",
                "Android - Wayport"};
        Random random = new Random();
        String[] userAgents = {"CaptiveNetworkSupport-277.10.5 wisp",
                "aptiveNetworkSupport-277 wispr"
        };

        List<Route<MapCoord>> routeList = new ArrayList<>();
        for (MapRoute route : routes) routeList.add(route);

        Class factory = MapTrackBuilder.class;
        try {
            if (configuration.getFactoryClassName() != null && (configuration.getFactoryClassName().trim().length() > 0 && (!configuration.getFactoryClassName().equals("MapTrackBuilder"))))
                factory = Class.forName(configuration.getFactoryPakageName() + "." + configuration.getFactoryClassName());
        } catch (Exception ex) {
            System.out.println(configuration.getFactoryClassName() + " class not found");
            System.out.println(ex.getStackTrace());
        }
        try {

            MapTrackBuilder trackBuilder = (MapTrackBuilder) factory.newInstance();
            trackBuilder.setDelimiter(configuration.getDelimiter());
            trackBuilder.setFluctuation(options.getFluctuation());
            generator = new GraphBasedGenerator<>(routeList, trackBuilder, behaviourManager);
        } catch (Exception ex) {
            System.out.println("Factory : " + configuration.getFactoryClassName() + " GraphBasedGenerator creation exception.");
            System.out.println(ex.getStackTrace());
        }

        Writer wifiWriter = destination.getWiFiWriter(prefix + "WiFi");

        for (int i = 1; i <= count; i++) {
            String macAdr = mac.nextString();
            String paymentIdentifier = mac.nextString();
            String phoneNumber = mac.nextString();
            String realm = realms[random.nextInt(realms.length)];
            String agent = userAgents[random.nextInt(userAgents.length)];
            MapTrack track = (MapTrack) generator.generate(
                    prefix + i,
                    startTime,
                    trackingInterval,
                    DataUtil.kilometersPerHourToMetersPerSecond(
                            DataUtil.milesToKilometers(
                                    movingObjects.getSpeed())));


            Writer writer = destination.getWriter(track.getId());

            if (writer != null) {

                trackDataWriter.write(track, writer);
                writer.close();
            }

            for (TrackCoord coord : track.getCoords()) {
                if (coord.isStayWiFi()) {
                    WiFiSession sesion = new WiFiSession();
                    sesion.setVenueCode(coord.getVenueCode());
                    sesion.setClientMAC(macAdr);
                    sesion.setStartDate(coord.getWiFiStart());
                    sesion.setStopDate(coord.getWifiStop());
                    sesion.setBytesTransmitted(random.nextInt(10000000));
                    sesion.setBytesReceived(random.nextInt(10000000));
                    sesion.setPaymentMethod("Mobility Subscriber");
                    sesion.setRealm(realm);
                    sesion.setUserAgent(agent);
                    sesion.setPaymentIdentifier(paymentIdentifier);
                    sesion.setPhoneNumber(phoneNumber);
                    sesion.setSessionID(wiFiSesionManager.getNewSessionId());

                    trackDataWriter.writeWiFiSesion(sesion.toString(), wifiWriter);
                }
            }
        }
        wifiWriter.close();
    }
}
