package com.att.bdcoe.cip.geo.data.generator.console.api;


import com.att.bdcoe.cip.geo.data.generator.model.Files;

import java.util.Collection;


public interface FilesService {
    Collection<Files> getList();
}