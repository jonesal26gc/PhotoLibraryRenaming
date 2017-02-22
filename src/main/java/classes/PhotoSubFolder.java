package classes;

import enums.FileType;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhotoSubFolder {
    private String folderName;
    private String subFolderName;
    private boolean OriginalSubFolderName = false;
    private boolean NewSubFolderName = false;
    private ArrayList<PhotoFile> photoFiles = new ArrayList<PhotoFile>();
    private Map<FileType,Integer> summaryOfFileTypes = new HashMap<FileType, Integer>();

    public PhotoSubFolder(String folderName, String subFolderName) {
        this.folderName = folderName;
        this.subFolderName = subFolderName;
        checkThatItIsFolder();
        setSubFolderNameFormatIndicators();
        retrieveListOfPhotoFiles();
        summarisePhotoFilesByFileType();
    }

    private void checkThatItIsFolder() {
        File file = new File(folderName.concat("\\").concat(subFolderName));
        if (!file.isDirectory()) {
            throw new RuntimeException("Photo SubFolder '" + subFolderName + "' is not a folder");
        }
    }

    private void setSubFolderNameFormatIndicators() {
        CheckerForOriginalSubFolderName checkerForOriginalSubFolderName = new CheckerForOriginalSubFolderName();
        OriginalSubFolderName = checkerForOriginalSubFolderName.validate(subFolderName);

        CheckerForNewSubFolderName checkerForNewSubFolderName = new CheckerForNewSubFolderName();
        NewSubFolderName = checkerForNewSubFolderName.validate(subFolderName);
    }

    private void retrieveListOfPhotoFiles() {
        File subFolder = new File(folderName.concat("\\").concat(subFolderName));
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

    public boolean isOriginalSubFolderName() {
        return OriginalSubFolderName;
    }

    public boolean isNewSubFolderName() {
        return NewSubFolderName;
    }

    public ArrayList<PhotoFile> getPhotoFiles() {
        return photoFiles;
    }

    public Map<FileType, Integer> getSummaryOfFileTypes() {
        return summaryOfFileTypes;
    }

    @Override
    public String toString() {
        return "PhotoSubFolder{" +
                "subFolderName='" + subFolderName + '\'' +
                ", summaryOfFileTypes=" + summaryOfFileTypes +
                '}';
    }
}
