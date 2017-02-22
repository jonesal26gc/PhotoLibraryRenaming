package enums;

// ENUM defines all the permanent instances of a Class upfront with all their
// instance variables.
// Getters, setters and constructors apply just the same as another class.
public enum FileType {
    JPG(FileCategory.PICTURE),
    MOV(FileCategory.VIDEO),
    TXT(FileCategory.DOCUMENT),
    DOC(FileCategory.DOCUMENT),
    DOCX(FileCategory.DOCUMENT),
    MPG(FileCategory.VIDEO),
    MOFF(FileCategory.VIDEO_ANALYSIS),
    MODD(FileCategory.VIDEO_ANALYSIS),
    DB(FileCategory.RUBBISH),
    BMP(FileCategory.PICTURE),
    M4V(FileCategory.VIDEO),
    THM(FileCategory.THUMBNAIL),
    PNG(FileCategory.PICTURE),
    MP4(FileCategory.VIDEO),
    INI(FileCategory.RUBBISH),
    XXX(FileCategory.RUBBISH),
    MP3(FileCategory.MUSIC);

    private final FileCategory fileCategory;

    FileType(FileCategory fileCategory) {
        this.fileCategory = fileCategory;
    }

    public FileCategory getFileCategory() {
        return fileCategory;
    }

    public static FileType findFileTypeFromFilename(String filename) {
        String fileExtension = getFileExtension(filename);
        for (FileType fileType : FileType.values()) {
            if (fileType.name().equals(fileExtension)) {
                return fileType;
            }
        }
        return FileType.XXX;
    }

    private static String getFileExtension(String filename) {
        try {
            return filename.substring(filename.lastIndexOf('.') + 1).toUpperCase();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "FileType{" +
                "fileCategory=" + fileCategory +
                '}';
    }
}

