package classes;

import enums.FileCategory;
import enums.FileType;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PhotoSubFolder {
    private File subFolder;
    private boolean originalSubFolderNameFormat;
    private boolean newSubFolderNameFormat;
    private ArrayList<PhotoFile> photoFiles = new ArrayList<PhotoFile>();
    private Map<FileCategory, Integer> countOfFilesInFileCategory = new HashMap<FileCategory, Integer>();
    private int countOfMisplacedSubFolders = 0;

    public PhotoSubFolder(File subFolder) {
        this.subFolder = subFolder;
        determineSubFolderNameFormatIndicators(subFolder.getName());
        buildListOfPhotoFiles(subFolder);
    }

    private void determineSubFolderNameFormatIndicators(String subFolderName) {
        FormatCheckerForOriginalSubFolderName formatCheckerForOriginalSubFolderName = new FormatCheckerForOriginalSubFolderName();
        originalSubFolderNameFormat = formatCheckerForOriginalSubFolderName.validate(subFolderName);

        FormatCheckerForNewSubFolderName formatCheckerForNewSubFolderName = new FormatCheckerForNewSubFolderName();
        newSubFolderNameFormat = formatCheckerForNewSubFolderName.validate(subFolderName);
    }

    private void buildListOfPhotoFiles(File subFolder) {
        for (File photo : subFolder.listFiles()) {
            if (!photo.isDirectory()) {
                PhotoFile photoFile = new PhotoFile(photo);
                incrementCountOfFilesInFileCategory(photoFile.getFileType().getFileCategory());
                photoFiles.add(photoFile);
            } else {
                System.out.println("* Warning - Non standard file '" + photo.getPath() + "' encountered.");
                countOfMisplacedSubFolders++;
            }
        }
        // Sort using the comparator for filename when more that one entry.
        if (photoFiles.size() > 1) {
            Collections.sort(photoFiles, PhotoFile.photoFileComparatorByFilename);
        }
    }

    private int incrementCountOfFilesInFileCategory(FileCategory fileCategory) {
        if (countOfFilesInFileCategory.containsKey(fileCategory)) {
            countOfFilesInFileCategory.put(fileCategory,
                    (countOfFilesInFileCategory.get(fileCategory)) + 1);
        } else {
            countOfFilesInFileCategory.put(fileCategory, 1);
        }
        return countOfFilesInFileCategory.get(fileCategory);
    }

    public PhotoSubFolder(File subFolder, boolean originalSubFolderNameFormat, boolean newSubFolderNameFormat, ArrayList<PhotoFile> photoFiles, Map<FileCategory, Integer> countOfFilesInFileCategory, int countOfMisplacedSubFolders) {
        this.subFolder = subFolder;
        this.originalSubFolderNameFormat = originalSubFolderNameFormat;
        this.newSubFolderNameFormat = newSubFolderNameFormat;
        this.photoFiles = photoFiles;
        this.countOfFilesInFileCategory = countOfFilesInFileCategory;
        this.countOfMisplacedSubFolders = countOfMisplacedSubFolders;
    }

    public boolean isOriginalSubFolderNameFormat() {
        return originalSubFolderNameFormat;
    }

    public HashMap<FileType, Integer> getPhotoFilesByFileTypeSubTotals() {
        HashMap<FileType, Integer> summaryOfFileTypes = new HashMap<FileType, Integer>();
        for (PhotoFile photoFile : photoFiles) {
            if (summaryOfFileTypes.containsKey(photoFile.getFileType())) {
                summaryOfFileTypes.put(photoFile.getFileType(), summaryOfFileTypes.get(photoFile.getFileType()) + 1);
            } else {
                summaryOfFileTypes.put(photoFile.getFileType(), 1);
            }
            if (photoFile.isDuplicateHasBeenFoundElsewhere()) {
                summaryOfFileTypes.put(photoFile.getFileType(), summaryOfFileTypes.get(photoFile.getFileType()) - 1);
            }
        }
        return summaryOfFileTypes;
    }

    public boolean isNewSubFolderNameFormat() {
        return newSubFolderNameFormat;
    }

    public ArrayList<PhotoFile> getPhotoFiles() {
        return photoFiles;
    }

    public Map<FileCategory, Integer> getCountOfFilesInFileCategory() {
        return countOfFilesInFileCategory;
    }

    public File getSubFolder() {
        return subFolder;
    }

    public int getCountOfMisplacedSubFolders() {
        return countOfMisplacedSubFolders;
    }

    public void ignoreDuplicatedPhotoFile(PhotoFile photoFile) {
        int count = (countOfFilesInFileCategory.get(photoFile.getFileType().getFileCategory()) - 1);
        countOfFilesInFileCategory.put(photoFile.getFileType().getFileCategory(), count);
        photoFile.setDuplicateHasBeenFoundElsewhere(true);
    }

    @Override
    public String toString() {
        return "PhotoSubFolder{" +
                "subFolder=" + subFolder +
                ", originalSubFolderNameFormat=" + originalSubFolderNameFormat +
                ", newSubFolderNameFormat=" + newSubFolderNameFormat +
                ", photoFiles=" + photoFiles +
                ", countOfFilesInFileCategory=" + countOfFilesInFileCategory +
                ", countOfMisplacedSubFolders=" + countOfMisplacedSubFolders +
                '}';
    }
}
