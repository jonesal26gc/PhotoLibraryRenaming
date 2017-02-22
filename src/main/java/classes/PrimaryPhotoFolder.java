package classes;

import enums.FileType;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PrimaryPhotoFolder {
    private String folderName;
    private ArrayList<PhotoSubFolder> photoSubFolders = new ArrayList<PhotoSubFolder>();
    private Map<FileType, Integer> summaryOfFileTypes = new HashMap<FileType, Integer>();

    public PrimaryPhotoFolder(String folderName) {
        this.folderName = folderName;
        checkThatItIsFolder();
        retrieveListOfSubFolders();
        summarisePhotoFilesByFileType();
    }

    private void checkThatItIsFolder() {
        File file = new File(folderName);
        if (!file.isDirectory()) {
            throw new RuntimeException("Primary Photo Library '" + folderName + "'is not a folder");
        }
    }

    public void retrieveListOfSubFolders() {
        File subFolder = new File(folderName);
        File[] files = subFolder.listFiles();

        for (File file : files) {
            photoSubFolders.add(new PhotoSubFolder(folderName + "\\" + file.getName()));
        }
    }

    private void summarisePhotoFilesByFileType() {
        for (PhotoSubFolder photoSubFolder : photoSubFolders) {
            for (Map.Entry<FileType, Integer> subTotal : photoSubFolder.getSummaryOfFileTypes().entrySet()) {
                if (summaryOfFileTypes.containsKey(subTotal.getKey())) {
                    summaryOfFileTypes.put(subTotal.getKey(), summaryOfFileTypes.get(subTotal.getKey()) + subTotal.getValue());
                } else {
                    summaryOfFileTypes.put(subTotal.getKey(), 1);
                }
            }
        }
    }

    public String getFolderName() {
        return folderName;
    }

    public ArrayList<PhotoSubFolder> getPhotoSubFolders() {
        return photoSubFolders;
    }

    public Map<FileType, Integer> getSummaryOfFileTypes() {
        return summaryOfFileTypes;
    }

    @Override
    public String toString() {
        return "PrimaryPhotoFolder{" +
                "folderName='" + folderName + '\'' +
                ", summaryOfFileTypes=" + summaryOfFileTypes +
                '}';
    }
}
