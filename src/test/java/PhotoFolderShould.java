import builders.PhotoFileBuilder;
import builders.PhotoFolderBuilder;
import builders.PhotoSubFolderBuilder;
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
        photoFolder.displayFolderSummary();
        assertThat(totals.size(),is(2));
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
            for (Map.Entry<FileType, Integer> i : photoSubFolder.getPhotoFilesByFileTypeSubTotals().entrySet()) {
                System.out.println(i.getKey().name() + "=" + i.getValue());
            }
        }
        System.out.println("");
        for (Map.Entry<FileType, Integer> i : photoFolder.getPhotoFilesByFileTypeTotals().entrySet()) {
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
