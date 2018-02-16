package app;

import classes.PhotoFolder;

import java.io.File;

public class PhotoRenameApp {
    public static void main(String[] args) {
        if (args.length == 0) {
            run(new PhotoFolder(new File(separatorsToSystem("/Users/tonyjones/Family Video Library")),
                            separatorsToSystem("/Users/tonyjones"),
                            false),
                    true);
        } else {
            run(new PhotoFolder(new File(args[0]),
                            separatorsToSystem("/Users/tonyjones"),
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

    private static String separatorsToSystem(String path) {
        if (path==null) return null;
        if (File.separatorChar=='\\') {
            // From Windows to Linux/Mac
            return path.replace('/', File.separatorChar);
        } else {
            // From Linux/Mac to Windows
            return path.replace('\\', File.separatorChar);
        }
    }
}
