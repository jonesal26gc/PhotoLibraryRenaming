import classes.PhotoFile;
import classes.PhotoFolder;
import classes.PhotoSubFolder;
import enums.FileType;
import org.junit.Test;

import java.util.Map;

public class PrimaryPhotoLibraryShould {

    @Test
    public void
    be_a_folder() {
        PhotoFolder photoFolder = new PhotoFolder("D:\\Family Photo Library");
    }

    @Test(expected = RuntimeException.class)
    public void
    throw_error_when_not_a_folder() {
        PhotoFolder photoFolder = new PhotoFolder("D:\\amily Photo Library");
    }

    @Test
    public void
    contain_a_list_of_sub_folders() {
        // given
        PhotoFolder photoFolder = new PhotoFolder("D:\\Family Photo Library");

        // then
        for (PhotoSubFolder photoSubFolder : photoFolder.getPhotoSubFolders()) {
            System.out.println(photoSubFolder.getSubFolderName());
            for (PhotoFile photoFile : photoSubFolder.getPhotoFiles()) {
                System.out.println(photoFile.getFilename());
                if (photoFile.getRevisedFilename() != null) {
                    System.out.println(photoFile.getRevisedFilename());
                }
            }
            for (Map.Entry<FileType, Integer> i : photoSubFolder.getSummaryOfFileTypes().entrySet()) {
                System.out.println(i.getKey().name() + "=" + i.getValue());
            }
        }
        System.out.println("");
        for (Map.Entry<FileType, Integer> i : photoFolder.getSummaryOfFileTypes().entrySet()) {
            System.out.println(i.getKey().name() + "=" + i.getValue());
        }

        // tally "old" style folders.
        int n = 0, o = 0;
        for (PhotoSubFolder photoSubFolder : photoFolder.getPhotoSubFolders()) {
            if (photoSubFolder.isNewSubFolderNameFormat()) n++;
            if (photoSubFolder.isOriginalSubFolderNameFormat()) o++;
            System.out.println(photoSubFolder.getRevisedSubFolderName());
        }
        System.out.println("");
        System.out.println(o + " original subfolder names encountered.");
        System.out.println(n + " new subfolder names encountered.");
    }
}
