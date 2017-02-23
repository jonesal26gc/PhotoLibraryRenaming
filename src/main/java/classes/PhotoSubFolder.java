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
    private static final char DASH = '-';
    private static final char SPACE = ' ';
    private static final String SLASH_DELIMITER = "\\";
    private static final char OPEN_SQUARE_BRACKET = '[';
    private static final char CLOSE_SQUARE_BRACKET = ']';
    private static final String EMPTY_STRING = "";
    private static final char FULL_STOP = '.';
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
            formatNewSubFolderName(subFolderName);
        }
        buildListOfPhotoFiles();
        buildPhotoFilesByFileTypeSubTotals();
    }

    private void checkThatItIsFolder() {
        File file = new File(folderName.concat(SLASH_DELIMITER).concat(subFolderName));
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

    private void formatNewSubFolderName(String subFolderName) {
        revisedSubFolderName = (cutCenturyAndYear(subFolderName)
                + DASH
                + cutMonth(subFolderName)
                + DASH
                + cutDay(subFolderName)
                + SPACE
                + Month.findAbbreviatedName(cutMonth(subFolderName))
                + cutYear(subFolderName)
                + SPACE).concat(cutSubjectText(subFolderName));
    }

    private void buildListOfPhotoFiles() {
        File subFolder = new File(folderName.concat(SLASH_DELIMITER).concat(subFolderName));
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

    private String cutCenturyAndYear(String subFolderName) {
        return subFolderName.substring(0, 4);
    }

    private String cutMonth(String subFolderName) {
        return subFolderName.substring(5, 7);
    }

    private String cutDay(String subFolderName) {
        return subFolderName.substring(8, 10);
    }

    private String cutYear(String subFolderName) {
        return subFolderName.substring(2, 4);
    }

    private String cutSubjectText(String subFolderName) {
        return subFolderName.substring(21);
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

        return cutTimeStampFromNewSubFolderName()
                .concat(formatFileSequenceNumber(newSequenceNumber))
                .concat(cutSubjectTextFromNewSubFolderName())
                .concat(cutCommentFieldFromFilename(filename))
                .concat(cutFileExtensionFromFilename(filename));
    }

    private String cutTimeStampFromNewSubFolderName() {
        return revisedSubFolderName.substring(0, 17);
    }

    private String formatFileSequenceNumber(int newSequenceNumber) {
        return String.format("#%03d ", newSequenceNumber);
    }

    private String cutSubjectTextFromNewSubFolderName() {
        return revisedSubFolderName.substring(17);
    }

    private String cutCommentFieldFromFilename(String filename) {
        int commentStartPos = filename.indexOf(OPEN_SQUARE_BRACKET);
        int commentEndPos = filename.indexOf(CLOSE_SQUARE_BRACKET);

        if (commentStartPos >= 0
                & commentStartPos < commentEndPos) {
            return SPACE + filename.substring(commentStartPos, commentEndPos + 1);
        }
        return EMPTY_STRING;
    }

    private String cutFileExtensionFromFilename(String filename) {
        return filename.substring(filename.indexOf(FULL_STOP));
    }

    public void makeNewSubFolder() {
        File folder = new File(revisedFolderName);
        if (!folder.exists()) {
            if (!folder.mkdir()) {
                throw new RuntimeException("Error - unable to create new folder '" + revisedFolderName + "'.");
            }
        }
        File subFolder = new File(revisedFolderName.concat(SLASH_DELIMITER).concat(revisedSubFolderName));
        if (!subFolder.mkdir()) {
            throw new RuntimeException("Error - unable to create new sub-folder '" + revisedSubFolderName + "'.");
        }
    }

    public void copyToNewSubFolder() {
        for (PhotoFile photoFile : photoFiles) {
            if (photoFile.getFileType().getFileCategory().isRetainFile()) {
                try {
                    File file = new File(folderName.concat(SLASH_DELIMITER).concat(subFolderName).concat(SLASH_DELIMITER).concat(photoFile.getFilename()));
                    File newFile = new File(revisedFolderName.concat(SLASH_DELIMITER).concat(revisedSubFolderName).concat(SLASH_DELIMITER).concat(photoFile.getRevisedFilename()));
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
