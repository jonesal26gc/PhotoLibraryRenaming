package classes;

import enums.FileCategory;
import enums.FileType;
import enums.Month;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class PhotoFolder {
    public static final String NEW_LINE = "\n";
    public static final String REVISED_FOLDER_NAME_TEMPLATE = "D:\\Family XXXXX Library - Revised Folders";
    private static final String SLASH_DELIMITER = "\\";
    private static final String DASH = "-";
    private static final String SPACE = " ";
    private static final String FILE_COUNT_TEMPLATE = "{fileCount}";
    private static final char OPEN_SQUARE_BRACKET = '[';
    private static final char CLOSE_SQUARE_BRACKET = ']';
    private static final String EMPTY_STRING = "";
    private static final char FULL_STOP = '.';
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
        subFolderUpdates();
//        for (PhotoSubFolder photoSubFolder : photoSubFolders) {
//            photoSubFolder.createRevisedFolderStructure(REVISED_FOLDER_NAME_TEMPLATE);
//            photoSubFolder.copyFileToRevisedSubFolder(REVISED_FOLDER_NAME_TEMPLATE);
//        }
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

    public void subFolderUpdates() {
        for (FileCategory fileCategory : FileCategory.values()) {
            if (fileCategory.isRetainFile()) {
                for (PhotoSubFolder photoSubFolder : photoSubFolders) {
                    if (photoSubFolder.getCountOfFilesInFileCategory().containsKey(fileCategory)
                            && photoSubFolder.getCountOfFilesInFileCategory().get(fileCategory) > 0) {

                        String revisedFolderName = REVISED_FOLDER_NAME_TEMPLATE.replaceFirst("XXXXX", fileCategory.getLibraryName());
                        createRevisedFolder(revisedFolderName);

                        String revisedSubFolderName;
                        if (photoSubFolder.isOriginalSubFolderNameFormat()
                                & fileCategory.isRenameFile()) {
                            revisedSubFolderName = formatNewSubFolderName(photoSubFolder.getSubFolderName(),
                                    photoSubFolder.getCountOfFilesInFileCategory().get(fileCategory));
                        } else {
                            revisedSubFolderName = photoSubFolder.getSubFolder().getName();
                        }
                        createRevisedSubFolder(revisedFolderName, revisedSubFolderName);
                        String revisedSubFolderNameWithoutCount = revisedSubFolderName.replace(
                                String.format(" {%d}", photoSubFolder.getCountOfFilesInFileCategory().get(fileCategory)),
                                EMPTY_STRING);

                        for (PhotoFile photoFile : photoSubFolder.getPhotoFiles()) {
                            if (photoFile.getFileType().getFileCategory() == fileCategory
                                    & (!photoFile.isDuplicateHasBeenFoundElsewhere())) {
                                //System.out.println("file: " + photoFile.getFile().getName());
                                if (photoSubFolder.isOriginalSubFolderNameFormat()
                                        & fileCategory.isRenameFile()) {
                                    copyFileToRevisedSubFolder(photoFile.getFile(),
                                            revisedFolderName,
                                            revisedSubFolderName,
                                            renamePhotoFile(revisedSubFolderNameWithoutCount,
                                                    photoFile.getFile().getName(),
                                                    photoFile.getSequenceNumber()));
                                } else {
                                    copyFileToRevisedSubFolder(photoFile.getFile(),
                                            revisedFolderName,
                                            revisedSubFolderName,
                                            photoFile.getFile().getName());
                                }
                            }
                        }
                    }
                }
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

    private String formatNewSubFolderName(String subFolderName, int fileCount) {
        return getCenturyAndYear(subFolderName)
                + DASH
                + getMonth(subFolderName)
                + DASH
                + getDay(subFolderName)
                + SPACE
                + Month.findAbbreviatedName(getMonth(subFolderName))
                + getYear(subFolderName)
                + SPACE
                + FILE_COUNT_TEMPLATE.replace(FILE_COUNT_TEMPLATE, String.format("{%d}", fileCount))
                + SPACE
                + getSubjectText(subFolderName);
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

    public void copyFileToRevisedSubFolder(File photoFile, String revisedFolderName, String revisedSubFolderName, String revisedFilename) {
        try {
            File file = new File(revisedFolderName
                    .concat(SLASH_DELIMITER)
                    .concat(revisedSubFolderName)
                    .concat(SLASH_DELIMITER)
                    .concat(revisedFilename));
            Files.copy(photoFile.toPath(), file.toPath(), REPLACE_EXISTING);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String renamePhotoFile(String revisedSubFolderName, String filename, int newSequenceNumber) {
        return getTimeStampFromNewSubFolderName(revisedSubFolderName)
                .concat(formatFileSequenceNumber(newSequenceNumber))
                .concat(getSubjectTextFromNewSubFolderName(revisedSubFolderName))
                .concat(getCommentFieldFromFilename(filename))
                .concat(getFileExtensionFromFilename(filename));
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

    private String getTimeStampFromNewSubFolderName(String revisedSubFolderName) {
        return revisedSubFolderName.substring(0, 17);
    }

    private String formatFileSequenceNumber(int newSequenceNumber) {
        return String.format("#%03d ", newSequenceNumber);
    }

    private String getSubjectTextFromNewSubFolderName(String revisedSubFolderName) {
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

    @Override
    public String toString() {
        return "PhotoFolder{" +
                "folder=" + folder +
                ", photoSubFolders=" + photoSubFolders +
                '}';
    }
}
