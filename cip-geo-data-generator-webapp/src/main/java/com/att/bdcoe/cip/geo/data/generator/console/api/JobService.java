package com.att.bdcoe.cip.geo.data.generator.console.api;


import com.att.bdcoe.cip.geo.data.generator.model.Job;

import java.io.IOException;
import java.util.List;

public interface JobService {
    List<Job> get() throws IOException;

    void saveAs(Job entity) throws IOException;
}
