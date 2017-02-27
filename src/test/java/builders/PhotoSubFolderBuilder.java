package builders;

import classes.PhotoFile;
import classes.PhotoSubFolder;
import enums.FileCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class PhotoSubFolderBuilder {
    private String folderName;
    private String subFolderName;
    private boolean originalSubFolderNameFormat;
    private boolean newSubFolderNameFormat;
    private ArrayList<PhotoFile> photoFiles = new ArrayList<PhotoFile>();
    private Map<FileCategory, Integer> countOfFileCategories = new HashMap<FileCategory, Integer>();
    private String revisedSubFolderName;

    private PhotoSubFolderBuilder() {
    }

    public static PhotoSubFolderBuilder aPhotoSubFolder() {
        return new PhotoSubFolderBuilder();
    }

    public PhotoSubFolderBuilder withFolderName(String folderName) {
        this.folderName = folderName;
        return this;
    }

    public PhotoSubFolderBuilder withSubFolderName(String subFolderName) {
        this.subFolderName = subFolderName;
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

    public PhotoSubFolderBuilder withCountOfFileCategories(Map<FileCategory, Integer> countOfFileCategories) {
        this.countOfFileCategories = countOfFileCategories;
        return this;
    }

    public PhotoSubFolderBuilder withRevisedSubFolderName(String revisedSubFolderName) {
        this.revisedSubFolderName = revisedSubFolderName;
        return this;
    }

    public PhotoSubFolder build() {
        PhotoSubFolder photoSubFolder = new PhotoSubFolder(folderName, subFolderName, originalSubFolderNameFormat, newSubFolderNameFormat, photoFiles, countOfFileCategories, revisedSubFolderName);
        return photoSubFolder;
    }
}
