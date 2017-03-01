package app;

import classes.PhotoFolder;

import java.io.File;

public class PhotoRenameApp {
    public static void main(String[] args) {
        if (args.length == 0) {
            run(new PhotoFolder(new File("D:\\Full Photo Library")), true);
        } else {
            run(new PhotoFolder(new File(args[0])), translateUpdateParameter(args[1]));
        }
    }

    private static boolean translateUpdateParameter(String arg) {
        return arg.equals("Y");
    }

    private static void run(PhotoFolder photoFolder, boolean isUpdate) {
        if (isUpdate) {
            photoFolder.generateRevisedPhotoSubFolders();
        }
    }
}
