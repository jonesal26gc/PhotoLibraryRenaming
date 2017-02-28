package classes;

import enums.FileCategory;
import enums.FileType;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class PhotoFolder {
    public static final String NEW_LINE = "\n";
    public static final String REVISED_FOLDER_NAME_TEMPLATE = "D:\\Family XXXXX Library - Revised Folders";
    private File folder;
    private ArrayList<PhotoSubFolder> photoSubFolders = new ArrayList<PhotoSubFolder>();
    private int countOfMisplacedSubFolders = 0;
    private int countOfMisplacedFiles = 0;

    public PhotoFolder(File folder) {
        this.folder = folder;

        checkThatItIsFolder(folder);
        buildListOfSubFolders(folder);
        displayFolderAndSubFolderSummary();
        checkForDuplicatedPhotoFilesAcrossAllSubFolders();
    }

    public void checkThatItIsFolder(File folder) {
        if (!folder.isDirectory()) {
            throw new RuntimeException("Primary Photo Library '" + folder.getPath() + "'is not a folder");
        }
    }

    public void buildListOfSubFolders(File folder) {
        File[] subFolders = folder.listFiles();
        for (File subFolder : subFolders) {
            if (subFolder.isDirectory()) {
                photoSubFolders.add(new PhotoSubFolder(subFolder));
            } else {
                System.out.println("* Warning - Non Sub-folder '" + folder.getPath() + "' encountered.");
                countOfMisplacedFiles++;
            }
        }
    }

    public void displayFolderAndSubFolderSummary() {

        System.out.println("Photo folder renaming utility");
        System.out.println("===========================");

        System.out.println(NEW_LINE + "Folder Name: " + folder.getName());
        for (PhotoSubFolder photoSubFolder : photoSubFolders) {
            System.out.println(NEW_LINE + "    Sub-Folder Name: " + photoSubFolder.getSubFolder().getName());
            for (Map.Entry<FileType, Integer> fileType : photoSubFolder.getPhotoFilesByFileTypeSubTotals().entrySet()) {
                System.out.println(String.format("      % 4d file(s) of Type '", fileType.getValue()) + fileType.getKey().name() + "' found.");
            }
            for (Map.Entry<FileCategory, Integer> fileCategory : photoSubFolder.getCountOfFilesInFileCategory().entrySet()) {
                System.out.println(String.format("              % 4d file(s) of Category '", fileCategory.getValue()) + fileCategory.getKey().name() + "' found.");
            }
            countOfMisplacedSubFolders += photoSubFolder.getCountOfMisplacedSubFolders();
        }
        System.out.println(NEW_LINE + "Grand Totals");
        for (Map.Entry<FileType, Integer> fileType : getPhotoFilesByFileTypeTotals().entrySet()) {
            System.out.println(String.format("% 5d file(s) of Type '", fileType.getValue()) + fileType.getKey().name() + "' found.");
        }
        for (Map.Entry<FileCategory, Integer> fileCategory : getPhotoFilesByFileCategoryTotals().entrySet()) {
            System.out.println(String.format("          % 5d file(s) of Category '", fileCategory.getValue()) + fileCategory.getKey().name() + "' found.");
        }
        if (countOfMisplacedFiles > 0 | countOfMisplacedSubFolders > 0) {
            if (countOfMisplacedSubFolders > 0) {
                System.out.println(NEW_LINE + "* Warning * There were " + countOfMisplacedSubFolders + " misplaced sub-folders encountered.");
            }
            if (countOfMisplacedFiles > 0) {
                System.out.println(NEW_LINE + "* Warning * There were " + countOfMisplacedFiles + " misplaced files encountered.");
            }
            sleepForAMoment();
            throw new RuntimeException("* Error * Run aborted due to previous exceptions.");
        }
    }

    private void checkForDuplicatedPhotoFilesAcrossAllSubFolders() {
        int countOfDuplicatePhotoFiles = 0;
        TreeMap<String, File> checkSumToFileMappings = new TreeMap<String, File>();
        System.out.print(NEW_LINE);
        for (PhotoSubFolder photoSubFolder : photoSubFolders) {
            for (PhotoFile photoFile : photoSubFolder.getPhotoFiles()) {
                if (checkSumToFileMappings.containsKey(photoFile.getCheckSumInHex())) {
                    countOfDuplicatePhotoFiles++;
                    photoSubFolder.ignoreDuplicatedPhotoFile(photoFile);
                    System.out.println("* Warning * Duplicated file: '" + photoFile.getNameOfFile() + "' - dropping it.");
                } else {
                    checkSumToFileMappings.put(photoFile.getCheckSumInHex(), photoFile.getFile());
                }
            }
        }
        System.out.println("There were " + countOfDuplicatePhotoFiles + " duplicate(s) found.");
    }

    public HashMap<FileType, Integer> getPhotoFilesByFileTypeTotals() {
        HashMap<FileType, Integer> summaryOfFileTypes = new HashMap<FileType, Integer>();
        for (PhotoSubFolder photoSubFolder : photoSubFolders) {
            for (Map.Entry<FileType, Integer> subTotal : photoSubFolder.getPhotoFilesByFileTypeSubTotals().entrySet()) {
                if (summaryOfFileTypes.containsKey(subTotal.getKey())) {
                    summaryOfFileTypes.put(subTotal.getKey(), summaryOfFileTypes.get(subTotal.getKey()) + subTotal.getValue());
                } else {
                    summaryOfFileTypes.put(subTotal.getKey(), subTotal.getValue());
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
                    summaryOfFileCategories.put(subTotal.getKey(), subTotal.getValue());
                }
            }
        }
        return summaryOfFileCategories;
    }

    private void sleepForAMoment() {
        try {
            Thread.sleep(1000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public PhotoFolder(File folder, ArrayList<PhotoSubFolder> photoSubFolders) {
        this.folder = folder;
        this.photoSubFolders = photoSubFolders;
    }

    public ArrayList<PhotoSubFolder> getPhotoSubFolders() {
        return photoSubFolders;
    }

    public void doUpdatesToFolder() {
        System.out.println(NEW_LINE + "Performing updates ........");
        for (Map.Entry<FileCategory, Integer> fileCategory : getPhotoFilesByFileCategoryTotals().entrySet()) {
            String revisedFolderName = REVISED_FOLDER_NAME_TEMPLATE.replaceFirst("XXXXX", fileCategory.getKey().getLibraryName());
            deleteRevisedFolderStructure(revisedFolderName);
        }
        for (PhotoSubFolder photoSubFolder : photoSubFolders) {
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
                "folder=" + folder +
                ", photoSubFolders=" + photoSubFolders +
                '}';
    }
}
