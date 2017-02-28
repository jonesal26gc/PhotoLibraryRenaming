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
    private File subFolder;
    private boolean originalSubFolderNameFormat;
    private boolean newSubFolderNameFormat;
    private ArrayList<PhotoFile> photoFiles = new ArrayList<PhotoFile>();
    private Map<FileCategory, Integer> countOfFilesInFileCategory = new HashMap<FileCategory, Integer>();
    private String revisedSubFolderName;
    private int countOfMisplacedSubFolders = 0;

    public PhotoSubFolder(File subFolder) {
        this.subFolder = subFolder;
        this.revisedSubFolderName = subFolder.getName();
        determineSubFolderNameFormatIndicators(subFolder.getName());
        if (isOriginalSubFolderNameFormat()) {
            formatNewSubFolderName(subFolder.getName());
        }
        buildListOfPhotoFiles(subFolder);
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

    private void formatNewSubFolderName(String subFolderName) {
        revisedSubFolderName = (getCenturyAndYear(subFolderName)
                + DASH
                + getMonth(subFolderName)
                + DASH
                + getDay(subFolderName)
                + SPACE
                + Month.findAbbreviatedName(getMonth(subFolderName))
                + getYear(subFolderName)
                + SPACE).concat(getSubjectText(subFolderName));
    }

    private void buildListOfPhotoFiles(File subFolder) {
        for (File photo : subFolder.listFiles()) {
            if (!photo.isDirectory()) {
                photoFiles.add(allocatePhotoFile(photo));
            } else {
                System.out.println("* Warning - Non standard file '" + photo.getPath() + "' encountered.");
                countOfMisplacedSubFolders++;
            }
        }
    }

    private String getCenturyAndYear(String subFolderName) {
        return subFolderName.substring(0, 4);
    }

    private String getMonth(String subFolderName) {
        return subFolderName.substring(5, 7);
    }

    private String getDay(String subFolderName) {
        return subFolderName.substring(8, 10);
    }

    private String getYear(String subFolderName) {
        return subFolderName.substring(2, 4);
    }

    private String getSubjectText(String subFolderName) {
        return subFolderName.substring(21);
    }

    private PhotoFile allocatePhotoFile(File photo) {
        PhotoFile photoFile = new PhotoFile(photo);
        int newFileSequence = incrementCountOfFilesInFileCategory(photoFile.getFileType().getFileCategory());
        if (photoFile.getFileType().getFileCategory().isRenameFile() &
                isOriginalSubFolderNameFormat()) {
            photoFile.setRevisedFilename(renamePhotoFile(photoFile.getNameOfFile(), newFileSequence));
        }
        return photoFile;
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

    public String renamePhotoFile(String filename, int newSequenceNumber) {
        return getTimeStampFromNewSubFolderName()
                .concat(formatFileSequenceNumber(newSequenceNumber))
                .concat(getSubjectTextFromNewSubFolderName())
                .concat(getCommentFieldFromFilename(filename))
                .concat(getFileExtensionFromFilename(filename));
    }

    private String getTimeStampFromNewSubFolderName() {
        return revisedSubFolderName.substring(0, 17);
    }

    private String formatFileSequenceNumber(int newSequenceNumber) {
        return String.format("#%03d ", newSequenceNumber);
    }

    private String getSubjectTextFromNewSubFolderName() {
        return revisedSubFolderName.substring(17);
    }

    private String getCommentFieldFromFilename(String filename) {
        int commentStartPos = filename.indexOf(OPEN_SQUARE_BRACKET);
        int commentEndPos = filename.indexOf(CLOSE_SQUARE_BRACKET);

        if (commentStartPos >= 0
                & commentStartPos < commentEndPos) {
            return SPACE + filename.substring(commentStartPos, commentEndPos + 1);
        }
        return EMPTY_STRING;
    }

    private String getFileExtensionFromFilename(String filename) {
        return filename.substring(filename.indexOf(FULL_STOP));
    }

    public PhotoSubFolder(File subFolder, boolean originalSubFolderNameFormat, boolean newSubFolderNameFormat, ArrayList<PhotoFile> photoFiles, Map<FileCategory, Integer> countOfFilesInFileCategory, String revisedSubFolderName) {
        this.subFolder = subFolder;
        this.originalSubFolderNameFormat = originalSubFolderNameFormat;
        this.newSubFolderNameFormat = newSubFolderNameFormat;
        this.photoFiles = photoFiles;
        this.countOfFilesInFileCategory = countOfFilesInFileCategory;
        this.revisedSubFolderName = revisedSubFolderName;
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

    public void createRevisedFolderStructure(String revisedFolderNameTemplate) {
        for (Map.Entry<FileCategory, Integer> fileCategory : countOfFilesInFileCategory.entrySet()) {
            if (!fileCategory.getKey().getLibraryName().equals("")) {
                String revisedFolderName = revisedFolderNameTemplate.replaceFirst("XXXXX", fileCategory.getKey().getLibraryName());
                createRevisedFolder(revisedFolderName);
                createRevisedSubFolder(revisedFolderName, revisedSubFolderName);
            }
        }
    }

    private void createRevisedFolder(String revisedFolderName) {
        File folder = new File(revisedFolderName);
        if (!folder.exists()) {
            if (!folder.mkdir()) {
                throw new RuntimeException("Error - unable to create new subFolder '" + revisedFolderName + "'.");
            } else {
                System.out.println("Creating subFolder: " + folder.getPath());
            }
        }
    }

    private void createRevisedSubFolder(String revisedFolderName, String revisedSubFolderName) {
        File subFolder = new File(revisedFolderName.concat(SLASH_DELIMITER).concat(revisedSubFolderName));
        if (!subFolder.exists()) {
            if (!subFolder.mkdir()) {
                throw new RuntimeException("Error - unable to create new sub-subFolder '" + revisedSubFolderName + "'.");
            } else {
                System.out.println("Creating sub-subFolder: " + subFolder.getPath());
            }
        }
    }

    public void copyRevisedFileToRevisedSubFolder(String revisedFolderNameTemplate) {
        for (PhotoFile photoFile : photoFiles) {
            if (photoFile.getFileType().getFileCategory().isRetainFile()) {
                try {
                    String revisedFolderName = revisedFolderNameTemplate.replaceFirst("XXXXX", photoFile.getFileType().getFileCategory().getLibraryName());
                    File newFile = new File(revisedFolderName.concat(SLASH_DELIMITER).concat(revisedSubFolderName).concat(SLASH_DELIMITER).concat(photoFile.getRevisedFilename()));
                    Files.copy(photoFile.getFile().toPath(), newFile.toPath(), REPLACE_EXISTING);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
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

    public String getRevisedSubFolderName() {
        return revisedSubFolderName;
    }

    @Override
    public String toString() {
        return "PhotoSubFolder{" +
                "subFolder=" + subFolder +
                ", originalSubFolderNameFormat=" + originalSubFolderNameFormat +
                ", newSubFolderNameFormat=" + newSubFolderNameFormat +
                ", photoFiles=" + photoFiles +
                ", countOfFilesInFileCategory=" + countOfFilesInFileCategory +
                ", revisedSubFolderName='" + revisedSubFolderName + '\'' +
                '}';
    }

    public File getSubFolder() {
        return subFolder;
    }

    public int getCountOfMisplacedSubFolders() {
        return countOfMisplacedSubFolders;
    }
}
