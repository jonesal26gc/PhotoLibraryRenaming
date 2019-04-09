package builders;

import classes.PhotoFolder;
import classes.PhotoSubFolder;

import java.io.File;
import java.util.ArrayList;

public final class PhotoFolderBuilder {
    private File folder;
    private ArrayList<PhotoSubFolder> photoSubFolders = new ArrayList<PhotoSubFolder>();
    private int countOfMisplacedSubFolders = 0;
    private int countOfMisplacedFiles = 0;
    private String destinationLocation;

    private PhotoFolderBuilder() {
    }

    public static PhotoFolderBuilder aPhotoFolder() {
        return new PhotoFolderBuilder();
    }

    public PhotoFolderBuilder withFolder(File folder) {
        this.folder = folder;
        return this;
    }

    public PhotoFolderBuilder withPhotoSubFolders(ArrayList<PhotoSubFolder> photoSubFolders) {
        this.photoSubFolders = photoSubFolders;
        return this;
    }

    public PhotoFolderBuilder withCountOfMisplacedSubFolders(int countOfMisplacedSubFolders) {
        this.countOfMisplacedSubFolders = countOfMisplacedSubFolders;
        return this;
    }

    public PhotoFolderBuilder withCountOfMisplacedFiles(int countOfMisplacedFiles) {
        this.countOfMisplacedFiles = countOfMisplacedFiles;
        return this;
    }

    public PhotoFolderBuilder withDestinationLocation(String destinationLocation) {
        this.destinationLocation = destinationLocation;
        return this;
    }

    public PhotoFolder build() {
        PhotoFolder photoFolder = new PhotoFolder(folder, photoSubFolders, countOfMisplacedSubFolders, countOfMisplacedFiles, destinationLocation);
        return photoFolder;
    }
}
