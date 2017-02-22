package classes;

import enums.FileType;
import java.io.File;

public class PhotoFile {
    private String filename;
    private FileType fileType;

    public PhotoFile(String filename) {
        this.filename = filename;
        checkThatItIsFile();
        this.fileType = FileType.findFileTypeFromFilename(filename);
    }

    private void checkThatItIsFile() {
        File file = new File(filename);
        if (file.isDirectory()) {
            throw new RuntimeException("Photo File '" + filename + "' is a folder rather than a photo file.");
        }
    }

    public String getFilename() {
        return filename;
    }

    public FileType getFileType() {
        return fileType;
    }
}
