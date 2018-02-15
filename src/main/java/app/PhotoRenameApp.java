package app;

import classes.PhotoFolder;

import java.io.File;

public class PhotoRenameApp {
    public static void main(String[] args) {
        if (args.length == 0) {
            run(new PhotoFolder(new File("/Users/tonyjones/Family Video Library"),
                            "/Users/tonyjones",
                            false),
                    true);
        } else {
            run(new PhotoFolder(new File(args[0]),
                            "/Users/tonyjones",
                            translateUpdateParameter(args[2])),
                    translateUpdateParameter(args[1]));
        }
    }

    private static void run(PhotoFolder photoFolder, boolean isUpdate) {
        if (isUpdate & !photoFolder.getPhotoSubFolders().isEmpty()) {
            photoFolder.generateRevisedPhotoSubFolders();
        }
    }

    private static boolean translateUpdateParameter(String arg) {
        return arg.toUpperCase().equals("Y");
    }
}
