package builders;

import classes.PhotoFile;
import classes.PhotoSubFolder;
import enums.FileCategory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class PhotoSubFolderBuilder {
    private File subFolder;
    private boolean originalSubFolderNameFormat;
    private boolean newSubFolderNameFormat;
    private ArrayList<PhotoFile> photoFiles = new ArrayList<PhotoFile>();
    private Map<FileCategory, Integer> countOfFilesInFileCategory = new HashMap<FileCategory, Integer>();
    private int countOfMisplacedSubFolders = 0;

    private PhotoSubFolderBuilder() {
    }

    public static PhotoSubFolderBuilder aPhotoSubFolder() {
        return new PhotoSubFolderBuilder();
    }

    public PhotoSubFolderBuilder withSubFolder(File subFolder) {
        this.subFolder = subFolder;
        return this;
    }

    public PhotoSubFolderBuilder withOriginalSubFolderNameFormat(boolean originalSubFolderNameFormat) {
        this.originalSubFolderNameFormat = originalSubFolderNameFormat;
        return this;
    }

    public PhotoSubFolderBuilder withNewSubFolderNameFormat(boolean newSubFolderNameFormat) {
        this.newSubFolderNameFormat = newSubFolderNameFormat;
        return this;
    }

    public PhotoSubFolderBuilder withPhotoFiles(ArrayList<PhotoFile> photoFiles) {
        this.photoFiles = photoFiles;
        return this;
    }

    public PhotoSubFolderBuilder withCountOfFilesInFileCategory(Map<FileCategory, Integer> countOfFilesInFileCategory) {
        this.countOfFilesInFileCategory = countOfFilesInFileCategory;
        return this;
    }

    public PhotoSubFolderBuilder withCountOfMisplacedSubFolders(int countOfMisplacedSubFolders) {
        this.countOfMisplacedSubFolders = countOfMisplacedSubFolders;
        return this;
    }

    public PhotoSubFolder build() {
        PhotoSubFolder photoSubFolder = new PhotoSubFolder(subFolder, originalSubFolderNameFormat, newSubFolderNameFormat, photoFiles, countOfFilesInFileCategory, countOfMisplacedSubFolders);
        return photoSubFolder;
    }
}
