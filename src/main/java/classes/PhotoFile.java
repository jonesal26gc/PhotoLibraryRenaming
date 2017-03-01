package classes;

import enums.FileType;

import java.io.File;

public class PhotoFile {
    private File file;
    private FileType fileType;
    private String checkSumInHex;
    private boolean duplicateHasBeenFoundElsewhere = false;

    public PhotoFile(File file) {
        this.file = file;
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

    public PhotoFile(File file, FileType fileType, String checkSumInHex, boolean duplicateHasBeenFoundElsewhere) {
        this.file = file;
        this.fileType = fileType;
        this.checkSumInHex = checkSumInHex;
        this.duplicateHasBeenFoundElsewhere = duplicateHasBeenFoundElsewhere;
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

    public String getCheckSumInHex() {
        return checkSumInHex;
    }

    public boolean isDuplicateHasBeenFoundElsewhere() {
        return duplicateHasBeenFoundElsewhere;
    }

    public void setDuplicateHasBeenFoundElsewhere(boolean duplicateHasBeenFoundElsewhere) {
        this.duplicateHasBeenFoundElsewhere = duplicateHasBeenFoundElsewhere;
    }
}
