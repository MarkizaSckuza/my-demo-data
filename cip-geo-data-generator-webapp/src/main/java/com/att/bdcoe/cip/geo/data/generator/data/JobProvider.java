package com.att.bdcoe.cip.geo.data.generator.data;


import com.att.bdcoe.cip.geo.data.generator.model.Job;

import java.io.IOException;
import java.util.List;

public interface JobProvider {
    Job read(String filename) throws IOException;

    List<Job> getAll() throws IOException;

    void write(Job job) throws IOException;
}

