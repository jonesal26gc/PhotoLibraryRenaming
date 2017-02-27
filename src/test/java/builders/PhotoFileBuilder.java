package builders;

import classes.PhotoFile;
import enums.FileType;

import java.io.File;

public final class PhotoFileBuilder {
    private String revisedFilename;
    private File file;
    private FileType fileType;

    private PhotoFileBuilder() {
    }

    public static PhotoFileBuilder aPhotoFile() {
        return new PhotoFileBuilder();
    }

    public PhotoFileBuilder withRevisedFilename(String revisedFilename) {
        this.revisedFilename = revisedFilename;
        return this;
    }

    public PhotoFileBuilder withFile(File file) {
        this.file = file;
        return this;
    }

    public PhotoFileBuilder withFileType(FileType fileType) {
        this.fileType = fileType;
        return this;
    }

    public PhotoFile build() {
        PhotoFile photoFile = new PhotoFile(file, fileType, revisedFilename);
        return photoFile;
    }
}
