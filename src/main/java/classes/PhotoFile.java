package classes;

import enums.FileType;

import java.io.File;

public class PhotoFile {
    private String filename;
    private FileType fileType;

    private String newFilename = null;

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

    public void setNewFilename(String newSubFolderName, int newPhotoFileSequenceNumber) {
        int commentStartPos = filename.indexOf('[');
        int commentEndPos = filename.indexOf(']');

        String commentField;
        if (commentStartPos >= 0
                & commentEndPos > 0
                & commentStartPos < commentEndPos) {
            commentField = " " + filename.substring(commentStartPos, commentEndPos + 1);
        } else {
            commentField = "";
        }

        newFilename = newSubFolderName.substring(0, 17)
                .concat(String.format("#%03d ", newPhotoFileSequenceNumber))
                .concat(newSubFolderName.substring(17))
                .concat(commentField)
                .concat(filename.substring(filename.indexOf('.')));
    }

    public String getFilename() {
        return filename;
    }

    public FileType getFileType() {
        return fileType;
    }

    public String getNewFilename() {
        return newFilename;
    }
}
