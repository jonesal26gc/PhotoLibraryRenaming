package enums;

public enum FileCategory {
     PHOTO(true,true)
    ,VIDEO(true,true)
    ,VIDEO_ANALYSIS(true,false)
    ,MUSIC(true,false)
    ,RUBBISH(false,false)
    ,DOCUMENT(true,false)
    ,THUMBNAIL(false,false);

    private boolean retainFile;
    private boolean renameFile;

    FileCategory(boolean retainFile, boolean renameFile) {
        this.retainFile = retainFile;
        this.renameFile = renameFile;
    }

    public boolean isRetainFile() {
        return retainFile;
    }

    public boolean isRenameFile() {
        return renameFile;
    }
}
