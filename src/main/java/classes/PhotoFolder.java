package classes;

import enums.FileType;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhotoFolder {
    private String folderName;
    private ArrayList<PhotoSubFolder> photoSubFolders = new ArrayList<PhotoSubFolder>();

    public PhotoFolder(String folderName) {
        this.folderName = folderName;
        checkThatItIsFolder();
        buildListOfSubFolders();
    }

    public PhotoFolder(String folderName, ArrayList<PhotoSubFolder> photoSubFolders) {
        this.folderName = folderName;
        this.photoSubFolders = photoSubFolders;
    }

    public void checkThatItIsFolder() {
        File file = new File(folderName);
        if (!file.isDirectory()) {
            throw new RuntimeException("Primary Photo Library '" + folderName + "'is not a folder");
        }
    }

    public void buildListOfSubFolders() {
        File subFolder = new File(folderName);
        File[] files = subFolder.listFiles();

        for (File file : files) {
            photoSubFolders.add(new PhotoSubFolder(folderName, file.getName()));
        }
    }

    public HashMap<FileType,Integer> getPhotoFilesByFileTypeTotals() {
        HashMap<FileType, Integer> summaryOfFileTypes = new HashMap<FileType, Integer>();
        for (PhotoSubFolder photoSubFolder : photoSubFolders) {
            for (Map.Entry<FileType, Integer> subTotal : photoSubFolder.getPhotoFilesByFileTypeSubTotals().entrySet()) {
                if (summaryOfFileTypes.containsKey(subTotal.getKey())) {
                    summaryOfFileTypes.put(subTotal.getKey(), summaryOfFileTypes.get(subTotal.getKey()) + subTotal.getValue());
                } else {
                    summaryOfFileTypes.put(subTotal.getKey(), 1);
                }
            }
        }
        return summaryOfFileTypes;
    }

    public String getFolderName() {
        return folderName;
    }

    public ArrayList<PhotoSubFolder> getPhotoSubFolders() {
        return photoSubFolders;
    }

    @Override
    public String toString() {
        return "PhotoFolder{" +
                "folderName='" + folderName + '\'' +
                ", photoSubFolders=" + photoSubFolders +
                '}';
    }
}
