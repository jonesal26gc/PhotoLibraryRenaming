package builders;

import classes.PhotoFile;
import enums.FileType;

import java.io.File;

public final class PhotoFileBuilder {
    private boolean duplicateHasBeenFoundElsewhere = false;
    private File file;
    private FileType fileType;
    private String checkSumInHex;

    private PhotoFileBuilder() {
    }

    public static PhotoFileBuilder aPhotoFile() {
        return new PhotoFileBuilder();
    }

    public PhotoFileBuilder withDuplicateHasBeenFoundElsewhere(boolean duplicateHasBeenFoundElsewhere) {
        this.duplicateHasBeenFoundElsewhere = duplicateHasBeenFoundElsewhere;
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

    public PhotoFileBuilder withCheckSumInHex(String checkSumInHex) {
        this.checkSumInHex = checkSumInHex;
        return this;
    }

    public PhotoFile build() {
        PhotoFile photoFile = new PhotoFile(file, fileType, checkSumInHex, duplicateHasBeenFoundElsewhere);
        return photoFile;
    }
}
