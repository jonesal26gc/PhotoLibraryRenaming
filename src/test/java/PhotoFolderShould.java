import classes.PhotoFolder;
import classes.PhotoFolderBuilder;
import enums.FileType;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class PhotoFolderShould {

    @Test
    public void
    check_for_folder() {
        PhotoFolder photoFolder = PhotoFolderBuilder.aPhotoFolder().withFolderName("D:\\").build();
        photoFolder.checkThatItIsFolder();
    }

    @Test(expected = RuntimeException.class)
    public void
    check_for_not_folder() {
        PhotoFolder photoFolder = PhotoFolderBuilder.aPhotoFolder().withFolderName("D:\\A").build();
        photoFolder.checkThatItIsFolder();
    }

    @Test
    public void
    provide_totals_by_fileType() {
        // given
        PhotoFolder photoFolder = PhotoFolderBuilder.aPhotoFolder().withPhotoSubFolders(null).build();

        // when
        HashMap<FileType, Integer> totals = photoFolder.getPhotoFilesByFileTypeTotals();

        // then
        for (Map.Entry<FileType,Integer> i : totals.entrySet()){
            System.out.println(i.getKey().name() + "=" + i.getValue());
        }

    }
}
