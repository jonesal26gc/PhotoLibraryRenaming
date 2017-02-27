package app;

import classes.PhotoFolder;

public class PhotoRenameApp {
    public static void main(String[] args) {
        String PhotoDirectoryName = "D:\\Family Photo Library";
        boolean isUpdate = true;

        run(new PhotoFolder(PhotoDirectoryName),isUpdate);
    }

    private static void run(PhotoFolder photoFolder, boolean isUpdate) {
        if (isUpdate) {
            photoFolder.update();
        }
    }
}
