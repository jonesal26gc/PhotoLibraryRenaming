package classes;

import enums.FileCategory;
import enums.FileType;
import enums.Month;

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
    private Map<FileCategory, Integer> countOfFileCategories = new HashMap<FileCategory, Integer>();
    private Map<FileType, Integer> summaryOfFileTypes = new HashMap<FileType, Integer>();
    private String newSubFolderName = null;

    public PhotoSubFolder(String folderName, String subFolderName) {
        this.folderName = folderName;
        this.subFolderName = subFolderName;
        checkThatItIsFolder();
        setSubFolderNameFormatIndicators();
        if (isOriginalSubFolderName()) {
            formatNewSubFolderName();
        }
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

    public boolean isOriginalSubFolderName() {
        return OriginalSubFolderName;
    }

    private void formatNewSubFolderName() {
        String newPrefix = subFolderName.substring(0, 4)
                + subFolderName.substring(4, 5)
                + subFolderName.substring(5, 7)
                + "-"
                + subFolderName.substring(8, 10)
                + " "
                + Month.findAbbreviatedName(subFolderName.substring(5, 7))
                + subFolderName.substring(2, 4)
                + " ";
        newSubFolderName = newPrefix.concat(subFolderName.substring(21));
    }

    private void retrieveListOfPhotoFiles() {
        File subFolder = new File(folderName.concat("\\").concat(subFolderName));
        File[] files = subFolder.listFiles();
        for (File file : files) {
            PhotoFile photoFile = new PhotoFile(file.getName());

            int newFileSequence = incrementCountOfFileCategory(photoFile.getFileType().getFileCategory());
            if (photoFile.getFileType().getFileCategory().isRenameFile() &
                    newSubFolderName != null ) {
                photoFile.setNewFilename(newSubFolderName, newFileSequence);
            }
            photoFiles.add(photoFile);
        }
    }

    private int incrementCountOfFileCategory(FileCategory fileCategory) {
        if (countOfFileCategories.containsKey(fileCategory)) {
            countOfFileCategories.put(fileCategory,
                    (countOfFileCategories.get(fileCategory)) + 1);
        } else {
            countOfFileCategories.put(fileCategory, 1);
        }
        return countOfFileCategories.get(fileCategory);
    }

    private void summarisePhotoFilesByFileType() {
        for (PhotoFile photoFile : photoFiles) {
            if (summaryOfFileTypes.containsKey(photoFile.getFileType())) {
                summaryOfFileTypes.put(photoFile.getFileType(), summaryOfFileTypes.get(photoFile.getFileType()) + 1);
            } else {
                summaryOfFileTypes.put(photoFile.getFileType(), 1);
            }
        }
    }

    public String getSubFolderName() {
        return subFolderName;
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

    public String getNewSubFolderName() {
        return newSubFolderName;
    }

    @Override
    public String toString() {
        return "PhotoSubFolder{" +
                "subFolderName='" + subFolderName + '\'' +
                ", summaryOfFileTypes=" + summaryOfFileTypes +
                '}';
    }
}
