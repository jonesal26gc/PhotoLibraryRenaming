package classes;

import enums.FileType;
import java.io.File;

public class PhotoFile {
    private String filename;
    private FileType fileType;
    private String revisedFilename;

    public PhotoFile(String filename) {
        this.filename = filename;
        this.revisedFilename = filename;
        checkThatItIsFile();
        this.fileType = FileType.findFileTypeFromFilename(filename);
    }

    public PhotoFile(String filename, FileType fileType, String revisedFilename) {
        this.filename = filename;
        this.fileType = fileType;
        this.revisedFilename = revisedFilename;
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

    public String getRevisedFilename() {
        return revisedFilename;
    }

    public void setRevisedFilename(String revisedFilename) {
        this.revisedFilename = revisedFilename;
    }
}
