package classes;

import enums.FileType;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhotoSubFolder {
    private String subFolderName;
    private ArrayList<PhotoFile> photoFiles = new ArrayList<PhotoFile>();
    private Map<FileType,Integer> summaryOfFileTypes = new HashMap<FileType, Integer>();

    public PhotoSubFolder(String subFolderName) {
        this.subFolderName = subFolderName;
        checkThatItIsFolder();
        retrieveListOfPhotoFiles();
        summarisePhotoFilesByFileType();
    }

    private void checkThatItIsFolder() {
        File file = new File(subFolderName);
        if (!file.isDirectory()) {
            throw new RuntimeException("Photo SubFolder '" + subFolderName + "' is not a folder");
        }
    }

    private void retrieveListOfPhotoFiles() {
        File subFolder = new File(subFolderName);
        File[] files = subFolder.listFiles();
        for (File file : files) {
            photoFiles.add(new PhotoFile(file.getName()));
        }
    }

    private void summarisePhotoFilesByFileType() {
        for (PhotoFile photoFile : photoFiles) {
            if (summaryOfFileTypes.containsKey(photoFile.getFileType())) {
                summaryOfFileTypes.put(photoFile.getFileType(),summaryOfFileTypes.get(photoFile.getFileType())+1);
            } else {
                summaryOfFileTypes.put(photoFile.getFileType(),1);
            }
        }
    }

    public String getSubFolderName() {
        return subFolderName;
    }

    public ArrayList<PhotoFile> getPhotoFiles() {
        return photoFiles;
    }

    public Map<FileType, Integer> getSummaryOfFileTypes() {
        return summaryOfFileTypes;
    }
}
