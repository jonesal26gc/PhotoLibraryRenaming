package app;

import classes.PhotoFolder;

import java.io.File;

public class PhotoRenameApp {

    /***
     * parameters are:
     * 1) original location;
     * 2) target location;
     * 3) ignore errors indicator
     * 4) update require indicator
     * @param args
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            doProcessing(new PhotoFolder(new File(separatorsToSystem("/Users/tonyjones/Family Video Library")),
                            separatorsToSystem("/Users/tonyjones"),
                            false),
                    true);
        } else {
            doProcessing(new PhotoFolder(new File(args[0]),
                            separatorsToSystem(args[1]),
                            translateUpdateParameter(args[2])),
                    translateUpdateParameter(args[3]));
        }
    }

    private static void doProcessing(PhotoFolder photoFolder, boolean isUpdate) {
        if (isUpdate & !photoFolder.getPhotoSubFolders().isEmpty()) {
            photoFolder.generateRevisedPhotoSubFolders();
        }
    }

    private static boolean translateUpdateParameter(String arg) {
        return arg.toUpperCase().equals("Y");
    }

    private static String separatorsToSystem(String path) {
        if (path == null) return null;
        if (File.separatorChar == '\\') {
            // From Windows to Linux/Mac
            return path.replace('/', File.separatorChar);
        } else {
            // From Linux/Mac to Windows
            return path.replace('\\', File.separatorChar);
        }
    }
}
