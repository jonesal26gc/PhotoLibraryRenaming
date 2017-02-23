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
    private boolean OriginalSubFolderNameFormat = false;
    private boolean NewSubFolderNameFormat = false;
    private ArrayList<PhotoFile> photoFiles = new ArrayList<PhotoFile>();
    private Map<FileCategory, Integer> countOfFileCategories = new HashMap<FileCategory, Integer>();
    private Map<FileType, Integer> summaryOfFileTypes = new HashMap<FileType, Integer>();
    private String revisedFolderName;
    private String revisedSubFolderName;

    public PhotoSubFolder(String folderName, String subFolderName) {
        this.folderName = folderName;
        this.revisedFolderName = folderName.concat(" - New Revised Copy");
        this.subFolderName = subFolderName;
        this.revisedSubFolderName = subFolderName;
        checkThatItIsFolder();
        determineSubFolderNameFormatIndicators();
        if (isOriginalSubFolderNameFormat()) {
            formatNewSubFolderName();
        }
        buildListOfPhotoFiles();
        buildPhotoFilesByFileTypeSubTotals();
    }

    private void checkThatItIsFolder() {
        File file = new File(folderName.concat("\\").concat(subFolderName));
        if (!file.isDirectory()) {
            throw new RuntimeException("Photo SubFolder '" + subFolderName + "' is not a folder");
        }
    }

    private void determineSubFolderNameFormatIndicators() {
        CheckerForOriginalSubFolderName checkerForOriginalSubFolderName = new CheckerForOriginalSubFolderName();
        OriginalSubFolderNameFormat = checkerForOriginalSubFolderName.validate(subFolderName);

        CheckerForNewSubFolderName checkerForNewSubFolderName = new CheckerForNewSubFolderName();
        NewSubFolderNameFormat = checkerForNewSubFolderName.validate(subFolderName);
    }

    public boolean isOriginalSubFolderNameFormat() {
        return OriginalSubFolderNameFormat;
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
        revisedSubFolderName = newPrefix.concat(subFolderName.substring(21));
    }

    private void buildListOfPhotoFiles() {
        File subFolder = new File(folderName.concat("\\").concat(subFolderName));
        File[] files = subFolder.listFiles();
        for (File file : files) {
            photoFiles.add(createPhotoFile(file.getName()));
        }
    }

    private void buildPhotoFilesByFileTypeSubTotals() {
        for (PhotoFile photoFile : photoFiles) {
            if (summaryOfFileTypes.containsKey(photoFile.getFileType())) {
                summaryOfFileTypes.put(photoFile.getFileType(), summaryOfFileTypes.get(photoFile.getFileType()) + 1);
            } else {
                summaryOfFileTypes.put(photoFile.getFileType(), 1);
            }
        }
    }

    private PhotoFile createPhotoFile(String filename) {
        PhotoFile photoFile = new PhotoFile(filename);
        int newFileSequence = incrementCountOfFileCategory(photoFile.getFileType().getFileCategory());
        if (photoFile.getFileType().getFileCategory().isRenameFile() &
                isOriginalSubFolderNameFormat()) {
            photoFile.setRevisedFilename(renamePhotoFile(photoFile.getFilename(), newFileSequence));
        }
        return photoFile;
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

        return revisedSubFolderName.substring(0, 17)
                .concat(String.format("#%03d ", newSequenceNumber))
                .concat(revisedSubFolderName.substring(17))
                .concat(commentField)
                .concat(filename.substring(filename.indexOf('.')));
    }

    public void makeNewSubFolder() {
        File folder = new File(revisedFolderName);
        if (!folder.exists()) {
            if (!folder.mkdir()) {
                throw new RuntimeException("Error - unable to create new folder '" + revisedFolderName + "'.");
            }
        }
        File subFolder = new File(revisedFolderName.concat("\\").concat(revisedSubFolderName));
        if (!subFolder.mkdir()) {
            throw new RuntimeException("Error - unable to create new sub-folder '" + revisedSubFolderName + "'.");
        }
    }

    public void copyToNewSubFolder() {
        for (PhotoFile photoFile : photoFiles) {
            if (photoFile.getFileType().getFileCategory().isRetainFile()) {
                try {
                    File file = new File(folderName.concat("\\").concat(subFolderName).concat("\\").concat(photoFile.getFilename()));
                    File newFile = new File(revisedFolderName.concat("\\").concat(revisedSubFolderName).concat("\\").concat(photoFile.getRevisedFilename()));
                    Files.copy(file.toPath(), newFile.toPath(), REPLACE_EXISTING);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public String getFolderName() {
        return folderName;
    }

    public String getSubFolderName() {
        return subFolderName;
    }

    public boolean isNewSubFolderNameFormat() {
        return NewSubFolderNameFormat;
    }

    public ArrayList<PhotoFile> getPhotoFiles() {
        return photoFiles;
    }

    public Map<FileCategory, Integer> getCountOfFileCategories() {
        return countOfFileCategories;
    }

    public Map<FileType, Integer> getSummaryOfFileTypes() {
        return summaryOfFileTypes;
    }

    public String getRevisedFolderName() {
        return revisedFolderName;
    }

    public String getRevisedSubFolderName() {
        return revisedSubFolderName;
    }

    @Override
    public String toString() {
        return "PhotoSubFolder{" +
                "subFolderName='" + subFolderName + '\'' +
                ", summaryOfFileTypes=" + summaryOfFileTypes +
                '}';
    }
}
