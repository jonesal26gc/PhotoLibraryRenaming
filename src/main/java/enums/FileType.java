package enums;

// ENUM defines all the permanent instances of a Class upfront with all their
// instance variables.
// Getters, setters and constructors apply just the same as another class.
public enum FileType {
    JPG(FileCategory.PHOTO),
    MOV(FileCategory.VIDEO_SONY),
    TXT(FileCategory.DOCUMENT),
    DOC(FileCategory.DOCUMENT),
    DOCX(FileCategory.DOCUMENT),
    MPG(FileCategory.VIDEO),
    MOFF(FileCategory.VIDEO_SONY),
    MODD(FileCategory.VIDEO_SONY),
    DB(FileCategory.RUBBISH),
    BMP(FileCategory.PHOTO),
    M4V(FileCategory.VIDEO),
    THM(FileCategory.THUMBNAIL),
    PNG(FileCategory.PHOTO),
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
        String fileExtension = identifyFileExtension(filename);
        for (FileType fileType : FileType.values()) {
            if (fileType.name().equals(fileExtension)) {
                return fileType;
            }
        }
        return FileType.XXX;
    }

    private static String identifyFileExtension(String filename) {
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

