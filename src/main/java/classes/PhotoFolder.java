package classes;

import enums.FileCategory;
import enums.FileType;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhotoFolder {
    public static final String NEW_LINE = "\n";
    public static final String REVISED_FOLDER_NAME_TEMPLATE = "D:\\Family XXXXX Library - Revised Folders";
    private String folderName;
    private ArrayList<PhotoSubFolder> photoSubFolders = new ArrayList<PhotoSubFolder>();

    public PhotoFolder(String folderName) {
        this.folderName = folderName;
        checkThatItIsFolder();
        buildListOfSubFolders();
        displayFolderAndSubFolderSummary();
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

    public void displayFolderAndSubFolderSummary() {

        System.out.println("Photo file renaming utility");
        System.out.println("===========================");

        System.out.println(NEW_LINE + "Folder Name: " + folderName);
        for (PhotoSubFolder photoSubFolder : photoSubFolders) {
            System.out.println(NEW_LINE + "    Sub-Folder Name: " + photoSubFolder.getSubFolderName());
            for (Map.Entry<FileType, Integer> fileType : photoSubFolder.getPhotoFilesByFileTypeSubTotals().entrySet()) {
                System.out.println(String.format("      % 4d file(s) of Type '", fileType.getValue()) + fileType.getKey().name() + "' found.");
            }
            for (Map.Entry<FileCategory, Integer> fileCategory : photoSubFolder.getCountOfFilesInFileCategory().entrySet()) {
                System.out.println(String.format("              % 4d file(s) of Category '", fileCategory.getValue()) + fileCategory.getKey().name() + "' found.");
            }
        }
        System.out.println(NEW_LINE + "Grand Totals");
        for (Map.Entry<FileType, Integer> fileType : getPhotoFilesByFileTypeTotals().entrySet()) {
            System.out.println(String.format("% 5d file(s) of Type '", fileType.getValue()) + fileType.getKey().name() + "' found.");
        }
        for (Map.Entry<FileCategory, Integer> fileCategory : getPhotoFilesByFileCategoryTotals().entrySet()) {
            System.out.println(String.format("          % 5d file(s) of Category '", fileCategory.getValue()) + fileCategory.getKey().name() + "' found.");
        }
    }

    public void update(){
        System.out.println(NEW_LINE + "Performing updates ........");
        for (Map.Entry<FileCategory, Integer> fileCategory : getPhotoFilesByFileCategoryTotals().entrySet()) {
            String revisedFolderName = REVISED_FOLDER_NAME_TEMPLATE.replaceFirst("XXXXX", fileCategory.getKey().getLibraryName());
            deleteRevisedFolderStructure(revisedFolderName);
        }
        for (PhotoSubFolder photoSubFolder : photoSubFolders){
            photoSubFolder.createRevisedFolderStructure(REVISED_FOLDER_NAME_TEMPLATE);
            photoSubFolder.copyRevisedFileToRevisedSubFolder(REVISED_FOLDER_NAME_TEMPLATE);
        }
        System.out.println("Completed.");
    }

    private void deleteRevisedFolderStructure(String revisedFolderName) {
        File folder = new File(revisedFolderName);
        if (folder.exists()) {
            for (File subFolder : folder.listFiles()) {
                if (subFolder.isDirectory()) {
                    for (File file : subFolder.listFiles()) {
                        file.delete();
                    }
                }
                subFolder.delete();
                System.out.println("Deleting pre-existing sub-folder: " + subFolder.getPath());
            }
            if (!folder.delete()) {
                throw new RuntimeException("Error - unable to delete new folder '" + revisedFolderName + "'.");
            }
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
