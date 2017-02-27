package classes;

import enums.FileType;

import java.io.File;

public class PhotoFile {
    private File file;
    private FileType fileType;
    private String revisedFilename;

    public PhotoFile(File file){
        this.file = file;
        this.revisedFilename = file.getName();
        this.fileType = FileType.findFileTypeFromFilename(file.getName());
    }

    public PhotoFile(File file, FileType fileType, String revisedFilename) {
        this.file = file;
        this.fileType = fileType;
        this.revisedFilename = revisedFilename;
    }

    public File getFile() {
        return file;
    }

    public String getNameOfFile() {
        return file.getName();
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
