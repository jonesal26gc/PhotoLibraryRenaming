package builders;

import classes.PhotoFolder;
import classes.PhotoSubFolder;

import java.io.File;
import java.util.ArrayList;

public final class PhotoFolderBuilder {
    private File file;
    private ArrayList<PhotoSubFolder> photoSubFolders = new ArrayList<PhotoSubFolder>();

    private PhotoFolderBuilder() {
    }

    public static PhotoFolderBuilder aPhotoFolder() {
        return new PhotoFolderBuilder();
    }

    public PhotoFolderBuilder withFile(File file) {
        this.file = file;
        return this;
    }

    public PhotoFolderBuilder withPhotoSubFolders(ArrayList<PhotoSubFolder> photoSubFolders) {
        this.photoSubFolders = photoSubFolders;
        return this;
    }

    public PhotoFolder build() {
        PhotoFolder photoFolder = new PhotoFolder(file, photoSubFolders);
        return photoFolder;
    }
}
