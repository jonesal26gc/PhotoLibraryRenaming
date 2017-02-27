package classes;

import enums.FileCategory;
import enums.FileType;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhotoFolder {
    public static final String NEW_LINE = "\n";
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
        File folder = new File(folderName);
        if (!folder.isDirectory()) {
            throw new RuntimeException("Primary Photo Library '" + folderName + "'is not a folder");
        }
    }

    public void buildListOfSubFolders() {
        File folder = new File(folderName);
        File[] subFolders = folder.listFiles();
        for (File subFolder : subFolders) {
            photoSubFolders.add(new PhotoSubFolder(folderName, subFolder.getName()));
        }
    }

    public HashMap<FileType, Integer> getPhotoFilesByFileTypeTotals() {
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

    public HashMap<FileCategory, Integer> getPhotoFilesByFileCategoryTotals() {
        HashMap<FileCategory, Integer> summaryOfFileCategories = new HashMap<FileCategory, Integer>();
        for (PhotoSubFolder photoSubFolder : photoSubFolders) {
            for (Map.Entry<FileCategory, Integer> subTotal : photoSubFolder.getCountOfFilesInFileCategory().entrySet()) {
                if (summaryOfFileCategories.containsKey(subTotal.getKey())) {
                    summaryOfFileCategories.put(subTotal.getKey(), summaryOfFileCategories.get(subTotal.getKey()) + subTotal.getValue());
                } else {
                    summaryOfFileCategories.put(subTotal.getKey(), 1);
                }
            }
        }
        return summaryOfFileCategories;
    }

    public ArrayList<PhotoSubFolder> getPhotoSubFolders() {
        return photoSubFolders;
    }

    public void displayFolderSummary() {

        System.out.println("Photo file renaming utility");
        System.out.println("===========================");

        System.out.println(NEW_LINE + "Folder Name: " + folderName);
        for (PhotoSubFolder photoSubFolder : photoSubFolders) {
            System.out.println("    Sub-Folder Name: " + photoSubFolder.getSubFolderName());
            for (Map.Entry<FileType, Integer> fileType : photoSubFolder.getPhotoFilesByFileTypeSubTotals().entrySet()) {
                System.out.println("        File Type: " + fileType.getKey().name() + "     Files Found = " + fileType.getValue());
            }
            for (Map.Entry<FileCategory, Integer> fileCategory : photoSubFolder.getCountOfFilesInFileCategory().entrySet()) {
                System.out.println("        File Category: " + fileCategory.getKey().name() + "     Files Found = " + fileCategory.getValue());
            }
        }
        System.out.println(NEW_LINE + "Grand Totals");
        for (Map.Entry<FileType, Integer> fileType : getPhotoFilesByFileTypeTotals().entrySet()) {
            System.out.println("    File Type: " + fileType.getKey().name() + "     Files Found = " + fileType.getValue());
        }
        for (Map.Entry<FileCategory, Integer> fileCategory : getPhotoFilesByFileCategoryTotals().entrySet()) {
            System.out.println("    File Category: " + fileCategory.getKey().name() + "     Files Found = " + fileCategory.getValue());
        }
    }

    @Override
    public String toString() {
        return "PhotoFolder{" +
                "folderName='" + folderName + '\'' +
                ", photoSubFolders=" + photoSubFolders +
                '}';
    }
}
