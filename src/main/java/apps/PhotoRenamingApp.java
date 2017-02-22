package apps;

import classes.PhotoRenaming;

/**
 * Created by xm39 on 04/01/2017.
 */
public class PhotoRenamingApp {

    public static void main(String[] args) {

        String parentFolderName;

        if ( args.length == 1) {
            parentFolderName = args[0];
        } else {
            parentFolderName = "d:/Family Photo Library";
        }

        PhotoRenaming.doIt(parentFolderName);
    }
}
