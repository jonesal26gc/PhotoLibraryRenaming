package apps;

import classes.PhotoRenaming;

public class PhotoRenamingApp {

    public static void main(String[] args) {
        String parentFolderName;

        if (args.length == 1) {
            parentFolderName = args[0];
        } else {
            parentFolderName = "d:/Family Photo Library";
        }

        PhotoRenaming.doIt(parentFolderName);
    }
}
