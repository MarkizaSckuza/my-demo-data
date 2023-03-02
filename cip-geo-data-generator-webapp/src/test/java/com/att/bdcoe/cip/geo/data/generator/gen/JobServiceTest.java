package com.att.bdcoe.cip.geo.data.generator.gen;

import com.att.bdcoe.cip.geo.data.generator.model.FileName;
import com.att.bdcoe.cip.geo.data.generator.model.Job;
import com.att.bdcoe.cip.geo.data.generator.model.Output;
import com.att.bdcoe.cip.geo.data.generator.model.Step;
import junit.framework.TestCase;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class JobServiceTest extends TestCase {

    private AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();


    @Before
    public void setUp() {
//        rootContext.register(GeneratorConfiguration.class);
//        rootContext.refresh();
//        JobProvider bean = rootContext.getBean(JobProvider.class);
        Job job = new Job();

        Step step1 = new Step();
        List<Output> outputs = new ArrayList<Output>();
        Output singleOutput = new Output("class", "descrition", false);

        outputs.add(new Output("class", "descrition", false));
        outputs.add(new Output("class", "descrition", false));

        List<FileName> scenarios = new ArrayList<FileName>();
        scenarios.add(new FileName("scenario1step1"));
        scenarios.add(new FileName("scenario1step1"));

        step1.setOutputs(outputs);
        step1.setScenarios(scenarios);


        Step step2 = new Step();
        outputs = new ArrayList<Output>();


        outputs.add(new Output("class", "descrition", false));
        outputs.add(new Output("class", "descrition", false));

        scenarios = new ArrayList<FileName>();
        scenarios.add(new FileName("scenario1step2"));
        scenarios.add(new FileName("scenario1step2"));

        step2.setOutputs(outputs);
        step2.setScenarios(scenarios);


        List<Step> steps = new ArrayList<Step>();
        steps.add(step1);
        steps.add(step2);
        job.setSteps(steps);


        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String jsonString = null;
        try {
            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(job);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print(jsonString);
        //YarnClient yc = new YarnClient();


//        ServletRegistration.Dynamic dispatcher=servletContext.addServlet(DISPATCHER_SERVLET_NAME,new DispatcherServlet(rootContext));
//        dispatcher.setLoadOnStartup(1);
//        dispatcher.addMapping(DISPATCHER_SERVLET_MAPPING_HOME);
//        dispatcher.addMapping(DISPATCHER_SERVLET_MAPPING_PRODUCTS);
//        dispatcher.addMapping(DISPATCHER_SERVLET_MAPPING_SERVICES);
//        servletContext.addListener(new ContextLoaderListener(rootContext));
    }

    @Test
    public void testHadoop() {

    }


}