import classes.*;
import enums.FileType;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

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
        PhotoFile photoFile1 = PhotoFileBuilder.aPhotoFile().withFilename("a").withFileType(FileType.BMP).build();
        PhotoFile photoFile2 = PhotoFileBuilder.aPhotoFile().withFilename("a").withFileType(FileType.DOC).build();
        ArrayList<PhotoFile> photoFiles = new ArrayList<PhotoFile>();
        photoFiles.add(photoFile1);
        photoFiles.add(photoFile2);
        PhotoSubFolder photoSubFolder1 = PhotoSubFolderBuilder.aPhotoSubFolder().withPhotoFiles(photoFiles).build();
        ArrayList<PhotoSubFolder> photoSubFolders = new ArrayList<PhotoSubFolder>();
        photoSubFolders.add(photoSubFolder1);
        PhotoFolder photoFolder = PhotoFolderBuilder.aPhotoFolder().withPhotoSubFolders(photoSubFolders).build();

        // when
        HashMap<FileType, Integer> totals = photoFolder.getPhotoFilesByFileTypeTotals();

        // then
        for (Map.Entry<FileType,Integer> i : totals.entrySet()){
            System.out.println(i.getKey().name() + "=" + i.getValue());
        }
        assertThat(totals.size(),is(2));
    }

}
