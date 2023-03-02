package com.att.bdcoe.cip.geo.data.generator.console.api;


import com.att.bdcoe.cip.geo.data.generator.model.FileName;

import java.io.File;
import java.util.Collection;


public interface FilesService {
    Collection<FileName> getList(FileType type);

    File getFileByName(String fileName);
}