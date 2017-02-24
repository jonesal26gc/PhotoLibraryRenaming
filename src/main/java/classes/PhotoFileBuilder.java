package classes;

import enums.FileType;

public final class PhotoFileBuilder {
    private String revisedFilename;
    private String filename;
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

    public PhotoFileBuilder withFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public PhotoFileBuilder withFileType(FileType fileType) {
        this.fileType = fileType;
        return this;
    }

    public PhotoFile build() {
        PhotoFile photoFile = new PhotoFile(filename, fileType, revisedFilename);
        return photoFile;
    }
}
