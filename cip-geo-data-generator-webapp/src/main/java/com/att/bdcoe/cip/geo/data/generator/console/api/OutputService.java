package com.att.bdcoe.cip.geo.data.generator.console.api;


import com.att.bdcoe.cip.geo.data.generator.model.Output;

import java.util.List;

public interface OutputService {
    List<Output> getLocation();

    List<Output> getNonLocation();

}
