package builders;

import classes.PhotoFile;
import classes.PhotoSubFolder;
import enums.FileCategory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class PhotoSubFolderBuilder {
    private File file;
    private boolean originalSubFolderNameFormat;
    private boolean newSubFolderNameFormat;
    private ArrayList<PhotoFile> photoFiles = new ArrayList<PhotoFile>();
    private Map<FileCategory, Integer> countOfFilesInFileCategory = new HashMap<FileCategory, Integer>();
    private String revisedSubFolderName;

    private PhotoSubFolderBuilder() {
    }

    public static PhotoSubFolderBuilder aPhotoSubFolder() {
        return new PhotoSubFolderBuilder();
    }

    public PhotoSubFolderBuilder withFile(File file) {
        this.file = file;
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

    public PhotoSubFolderBuilder withRevisedSubFolderName(String revisedSubFolderName) {
        this.revisedSubFolderName = revisedSubFolderName;
        return this;
    }

    public PhotoSubFolder build() {
        PhotoSubFolder photoSubFolder = new PhotoSubFolder(file, originalSubFolderNameFormat, newSubFolderNameFormat, photoFiles, countOfFilesInFileCategory, revisedSubFolderName);
        return photoSubFolder;
    }
}
