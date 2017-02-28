package enums;

public enum FileCategory {
     PHOTO(true,true,"Photo")
    ,VIDEO(true,true,"Video")
    ,VIDEO_SONY(true,false,"Video (SONY)")
    ,MUSIC(true,false,"Music")
    ,DOCUMENT(true,false,"Document")
    ,THUMBNAIL(false,false,"")
    ,RUBBISH(false,false,"");

    private boolean retainFile;
    private boolean renameFile;
    private String libraryName;

    FileCategory(boolean retainFile, boolean renameFile, String libraryName) {
        this.retainFile = retainFile;
        this.renameFile = renameFile;
        this.libraryName = libraryName;
    }

    public boolean isRetainFile() {
        return retainFile;
    }

    public boolean isRenameFile() {
        return renameFile;
    }

    public String getLibraryName() {
        return libraryName;
    }
}
