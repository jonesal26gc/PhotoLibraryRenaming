package classes;

import enums.FileCategory;
import enums.FileType;
import enums.Month;

import java.io.File;
import java.util.ArrayList;
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

    public PhotoSubFolder(File subFolder, boolean originalSubFolderNameFormat, boolean newSubFolderNameFormat, ArrayList<PhotoFile> photoFiles, Map<FileCategory, Integer> countOfFilesInFileCategory, int countOfMisplacedSubFolders) {
        this.subFolder = subFolder;
        this.originalSubFolderNameFormat = originalSubFolderNameFormat;
        this.newSubFolderNameFormat = newSubFolderNameFormat;
        this.photoFiles = photoFiles;
        this.countOfFilesInFileCategory = countOfFilesInFileCategory;
        this.countOfMisplacedSubFolders = countOfMisplacedSubFolders;
    }

    private void determineSubFolderNameFormatIndicators(String subFolderName) {
        FormatCheckerForOriginalSubFolderName formatCheckerForOriginalSubFolderName = new FormatCheckerForOriginalSubFolderName();
        originalSubFolderNameFormat = formatCheckerForOriginalSubFolderName.validate(subFolderName);

        FormatCheckerForNewSubFolderName formatCheckerForNewSubFolderName = new FormatCheckerForNewSubFolderName();
        newSubFolderNameFormat = formatCheckerForNewSubFolderName.validate(subFolderName);
    }

    public boolean isOriginalSubFolderNameFormat() {
        return originalSubFolderNameFormat;
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

    public HashMap<FileType, Integer> getPhotoFilesByFileTypeSubTotals() {
        HashMap<FileType, Integer> summaryOfFileTypes = new HashMap<FileType, Integer>();
        for (PhotoFile photoFile : photoFiles) {
            if (summaryOfFileTypes.containsKey(photoFile.getFileType())) {
                summaryOfFileTypes.put(photoFile.getFileType(), summaryOfFileTypes.get(photoFile.getFileType()) + 1);
            } else {
                summaryOfFileTypes.put(photoFile.getFileType(), 1);
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
