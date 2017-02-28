package classes;

import enums.FileType;

import java.io.File;

public class PhotoFile {
    private File file;
    private FileType fileType;
    private String revisedFilename;
    private String checkSumInHex;

    public PhotoFile(File file) {
        this.file = file;
        this.revisedFilename = file.getName();
        this.fileType = FileType.findFileTypeFromFilename(file.getName());
        this.checkSumInHex = setCheckSum(file);
        //System.out.println("File: " + file.getName() + " has checksum of: " + checkSumInHex);
    }

    private String setCheckSum(File file) {
        try {
            return CheckSum.generate(file);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
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

    public String getCheckSumInHex() {
        return checkSumInHex;
    }

    public void setRevisedFilename(String revisedFilename) {
        this.revisedFilename = revisedFilename;
    }
}
