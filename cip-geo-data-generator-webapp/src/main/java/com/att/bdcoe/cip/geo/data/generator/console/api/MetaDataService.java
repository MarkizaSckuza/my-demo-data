package com.att.bdcoe.cip.geo.data.generator.console.api;

import com.att.bdcoe.cip.geo.data.generator.model.FileName;
import com.att.bdcoe.cip.geo.data.generator.model.Output;

import java.util.Collection;
import java.util.List;

public interface MetaDataService {
    Collection<FileName> getJobFilesList();

    Collection<FileName> getScenarioFilesList();

    List<Output> getAllOutputs();

    List<Output> getLocationOutputs();

    List<Output> getNonLocationOutputs();
}
