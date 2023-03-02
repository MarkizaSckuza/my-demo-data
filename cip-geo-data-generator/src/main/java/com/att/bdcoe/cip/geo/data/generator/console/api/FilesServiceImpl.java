package com.att.bdcoe.cip.geo.data.generator.console.api;


import com.att.bdcoe.cip.geo.data.generator.model.Files;
import org.springframework.stereotype.Service;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Path("/files")
@Produces({"application/json"})
@Service
public class FilesServiceImpl implements FilesService {

    @GET
    public Collection<Files> getList() {
        List<Files> filesList = new ArrayList<>();
        File currentDir = new File(".");
        File[] listOfFiles = currentDir.listFiles();

        if (listOfFiles != null) {
            for (File fileFromList : listOfFiles) {
                if (fileFromList.isFile()
                        && (fileFromList.getName().length() > 5 && fileFromList.getName().endsWith(".json"))) {
                    filesList.add(new Files(fileFromList.getName()));
                }
            }
        }
        return filesList;
    }

    @DELETE
    public boolean deleteFile(String fileName) {
        try {
            File file = new File(fileName);
            if (file.delete()) {
                System.out.println(file.getName() + " is deleted!");
                return true;
            } else {
                System.out.println("Delete operation is failed.");
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
