package classes;

import enums.FileCategory;
import enums.FileType;
import enums.Month;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class PhotoSubFolder {
    private String folderName;
    private String subFolderName;
    private boolean OriginalSubFolderName = false;
    private boolean NewSubFolderName = false;
    private ArrayList<PhotoFile> photoFiles = new ArrayList<PhotoFile>();
    private Map<FileCategory, Integer> countOfFileCategories = new HashMap<FileCategory, Integer>();
    private Map<FileType, Integer> summaryOfFileTypes = new HashMap<FileType, Integer>();
    private String newFolderName;
    private String newSubFolderName = null;

    public PhotoSubFolder(String folderName, String subFolderName) {
        this.folderName = folderName;
        this.newFolderName = folderName.concat(" - New Revised Copy");
        this.subFolderName = subFolderName;
        checkThatItIsFolder();
        determineSubFolderNameFormatIndicators();
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

    private void determineSubFolderNameFormatIndicators() {
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
                    newSubFolderName != null) {
                photoFile.setNewFilename(renamePhotoFile(photoFile.getFilename(),newFileSequence));
            }
            photoFiles.add(photoFile);
        }
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

    private int incrementCountOfFileCategory(FileCategory fileCategory) {
        if (countOfFileCategories.containsKey(fileCategory)) {
            countOfFileCategories.put(fileCategory,
                    (countOfFileCategories.get(fileCategory)) + 1);
        } else {
            countOfFileCategories.put(fileCategory, 1);
        }
        return countOfFileCategories.get(fileCategory);
    }

    public String renamePhotoFile(String filename, int newSequenceNumber) {
        int commentStartPos = filename.indexOf('[');
        int commentEndPos = filename.indexOf(']');

        String commentField;
        if (commentStartPos >= 0
                & commentEndPos > 0
                & commentStartPos < commentEndPos) {
            commentField = " " + filename.substring(commentStartPos, commentEndPos + 1);
        } else {
            commentField = "";
        }

        return newSubFolderName.substring(0, 17)
                .concat(String.format("#%03d ", newSequenceNumber))
                .concat(newSubFolderName.substring(17))
                .concat(commentField)
                .concat(filename.substring(filename.indexOf('.')));
    }

    public void makeNewSubFolder() {
        File folder = new File(newFolderName);
        if (!folder.exists()) {
            if (!folder.mkdir()) {
                throw new RuntimeException("Error - unable to create new folder '" + newFolderName + "'.");
            }
        }
        File subFolder = new File(newFolderName.concat("\\").concat(newSubFolderName));
        if (!subFolder.mkdir()) {
            throw new RuntimeException("Error - unable to create new sub-folder '" + newSubFolderName + "'.");
        }
    }

    public void copyToNewSubFolder() {
        for (PhotoFile photoFile : photoFiles) {
            if (photoFile.getFileType().getFileCategory().isRetainFile()) {
                try {
                    File file = new File(folderName.concat("\\").concat(subFolderName).concat("\\").concat(photoFile.getFilename()));
                    File newFile = new File(newFolderName.concat("\\").concat(newSubFolderName).concat("\\").concat(photoFile.getNewFilename()));
                    Files.copy(file.toPath(), newFile.toPath(), REPLACE_EXISTING);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
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
