package com.att.bdcoe.cip.geo.data.generator.console.api;


import com.att.bdcoe.cip.geo.data.generator.data.JobProvider;
import com.att.bdcoe.cip.geo.data.generator.model.FileName;
import com.att.bdcoe.cip.geo.data.generator.model.Job;
import com.att.bdcoe.cip.geo.data.generator.model.Output;
import com.att.bdcoe.cip.geo.data.generator.model.Step;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/jobs", headers = "Accept=application/json")
public class JobServiceImpl implements JobService {

    @Autowired
    FilesService filesService;


    @Autowired
    MetaDataService metaDataService;


    @Autowired
    JobProvider jobProvider;

    private ObjectMapper mapper;

    public JobServiceImpl() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public List<Job> get() throws IOException {
        List<Job> jobs = jobProvider.getAll();
        for (Job job : jobs) {
            for (Step step : job.getSteps()) {
                step.setOutputs(Output.mergeWithFullList(step.getOutputs(), metaDataService.getNonLocationOutputs()));
                step.setScenarios(FileName.mergeWithFullList(step.getScenarios(), new ArrayList<FileName>(metaDataService.getScenarioFilesList())));
            }
        }
        return jobs;
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public void saveAs(Job entity) throws IOException {
        for (Step step : entity.getSteps()) {
            step.setOutputs(Output.getSlectedOnly(step.getOutputs()));
            step.setScenarios(FileName.getSlectedOnly(step.getScenarios()));
        }

        jobProvider.write(entity);
    }
}
