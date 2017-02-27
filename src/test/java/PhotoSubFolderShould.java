import classes.*;
import enums.FileCategory;
import enums.FileType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PhotoSubFolderShould {

    @Test
    public void
    be_a_folder() {
        PhotoSubFolder photoSubFolder = new PhotoSubFolder(null, "D:\\Family Photo Library\\2001-12-09 Dec01 Bournmouth (Sue & Paul's)");
    }

    @Test(expected = RuntimeException.class)
    public void
    throw_error_when_not_a_folder() {
        PhotoSubFolder photoSubFolder = new PhotoSubFolder(null, "D:\\Family Photo Library\\2001-12-09 Dec01 Bournmouth");
    }

    @Test
    public void
    contain_a_list_of_photoFiles() {
        // given
        PhotoSubFolder photoSubFolder = new PhotoSubFolder(null, "D:\\Family Photo Library\\2001-12-09 Dec01 Bournmouth (Sue & Paul's)");

        // then
        for (PhotoFile photoFile : photoSubFolder.getPhotoFiles()) {
            System.out.println(photoFile.getFilename());
        }
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

        // when
        HashMap<FileType, Integer> totals = photoSubFolder1.getPhotoFilesByFileTypeSubTotals();

        // then
        for (Map.Entry<FileType,Integer> i : totals.entrySet()){
            System.out.println(i.getKey().name() + "=" + i.getValue());
        }
        assertThat(totals.size(),is(2));
    }


    @Test
    public void
    create_revised_folderStructure(){

        HashMap<FileCategory,Integer> countOfFileCategories = new HashMap<FileCategory, Integer>();
        countOfFileCategories.put(FileCategory.PHOTO,1);
        countOfFileCategories.put(FileCategory.VIDEO,1);
        PhotoSubFolder photoSubFolder = PhotoSubFolderBuilder.aPhotoSubFolder()
                .withRevisedSubFolderName("revisedSubFolderName")
                .withCountOfFileCategories(countOfFileCategories)
                .build();
        photoSubFolder.createRevisedFolderStructure();
    }

    @Test
    public void
    copy_files_to_revised_folderStructure(){

        PhotoFile photoFile1 = PhotoFileBuilder.aPhotoFile()
                .withFilename("picture.jpg").withFileType(FileType.JPG)
                .withRevisedFilename("picture.jpg")
                .build();
        PhotoFile photoFile2 = PhotoFileBuilder.aPhotoFile()
                .withFilename("video.mp4").withFileType(FileType.MP4)
                .withRevisedFilename("video.mp4")
                .build();
        ArrayList<PhotoFile> photoFiles = new ArrayList<PhotoFile>();
        photoFiles.add(photoFile1);
        photoFiles.add(photoFile2);

        HashMap<FileCategory,Integer> countOfFileCategories = new HashMap<FileCategory, Integer>();
        countOfFileCategories.put(FileCategory.PHOTO,1);
        countOfFileCategories.put(FileCategory.VIDEO,1);

        PhotoSubFolder photoSubFolder = PhotoSubFolderBuilder.aPhotoSubFolder()
                .withPhotoFiles(photoFiles)
                .withCountOfFileCategories(countOfFileCategories)
                .withFolderName("D:\\folderName")
                .withSubFolderName("subFolderName")
                .withRevisedSubFolderName("revisedSubFolderName")
                .build();

        //System.out.println(photoSubFolder.toString());
        photoSubFolder.copyRevisedFileToRevisedSubFolder("D:\\Family Photo Library - Revised Copy");
    }

}
