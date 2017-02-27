package builders;

import classes.PhotoFolder;
import classes.PhotoSubFolder;

import java.util.ArrayList;

public final class PhotoFolderBuilder {
    private String folderName;
    private ArrayList<PhotoSubFolder> photoSubFolders = new ArrayList<PhotoSubFolder>();

    private PhotoFolderBuilder() {
    }

    public static PhotoFolderBuilder aPhotoFolder() {
        return new PhotoFolderBuilder();
    }

    public PhotoFolderBuilder withFolderName(String folderName) {
        this.folderName = folderName;
        return this;
    }

    public PhotoFolderBuilder withPhotoSubFolders(ArrayList<PhotoSubFolder> photoSubFolders) {
        this.photoSubFolders = photoSubFolders;
        return this;
    }

    public PhotoFolder build() {
        PhotoFolder photoFolder = new PhotoFolder(folderName, photoSubFolders);
        return photoFolder;
    }
}
