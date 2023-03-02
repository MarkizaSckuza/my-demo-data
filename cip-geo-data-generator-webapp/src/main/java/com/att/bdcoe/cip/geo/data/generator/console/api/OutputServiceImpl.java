package com.att.bdcoe.cip.geo.data.generator.console.api;


import com.att.bdcoe.cip.geo.data.generator.model.Output;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/outputs")
public class OutputServiceImpl implements OutputService {

    @Autowired
    private MetaDataService metaDataService;

    @Override
    @RequestMapping(value = "/location", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Output> getLocation() {
        return metaDataService.getLocationOutputs();
    }

    @Override
    @RequestMapping(value = "/nonlocation", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Output> getNonLocation() {
        return metaDataService.getNonLocationOutputs();
    }
}
