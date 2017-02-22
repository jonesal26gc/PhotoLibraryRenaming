package apps;

import classes.PhotoCount;

public class PhotoCountApp {

    public static void main(String[] args) {
        String parentFolderName;

        if ( args.length == 1) {
            parentFolderName = args[0];
        } else {
            parentFolderName = "d:/Family Photo Library";
        }
        PhotoCount.doIt(parentFolderName);
    }
}
