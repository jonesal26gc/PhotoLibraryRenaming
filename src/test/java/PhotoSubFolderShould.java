import classes.*;
import enums.FileCategory;
import enums.FileType;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PhotoSubFolderShould {

    @Test
    public void
    identify_3_files_of_an_original_format_subfolder() {
        File file = new File(TestConstants.TEST_ORIGINAL_SUBFOLDER);
        PhotoSubFolder photoSubFolder = new PhotoSubFolder(file);
        assertTrue(photoSubFolder.isOriginalSubFolderNameFormat());
        assertFalse(photoSubFolder.isNewSubFolderNameFormat());
        assertThat(photoSubFolder.getPhotoFiles().size(),is(3));
        assertThat(photoSubFolder.getPhotoFiles().get(0).getFile().getPath(),is(TestConstants.TEST_ORIGINAL_DOCUMENT_1));
        assertThat(photoSubFolder.getPhotoFiles().get(0).getFileType(),is(FileType.TXT));
        System.out.println(photoSubFolder.getPhotoFiles().get(0).getFile().getPath());
        assertThat(photoSubFolder.getPhotoFiles().get(1).getFile().getPath(),is(TestConstants.TEST_ORIGINAL_DOCUMENT_2));
        assertThat(photoSubFolder.getPhotoFiles().get(1).getFileType(),is(FileType.TXT));
        System.out.println(photoSubFolder.getPhotoFiles().get(1).getFile().getPath());
        assertThat(photoSubFolder.getPhotoFiles().get(2).getFile().getPath(),is(TestConstants.TEST_ORIGINAL_PICTURE_1));
        assertThat(photoSubFolder.getPhotoFiles().get(2).getFileType(),is(FileType.JPG));
        System.out.println(photoSubFolder.getPhotoFiles().get(2).getFile().getPath());
    }

    @Test
    public void
    identify_3_files_of_a_new_format_subfolder() {
        File file = new File(TestConstants.TEST_NEW_SUBFOLDER);
        PhotoSubFolder photoSubFolder = new PhotoSubFolder(file);
        assertFalse(photoSubFolder.isOriginalSubFolderNameFormat());
        assertTrue(photoSubFolder.isNewSubFolderNameFormat());
        assertThat(photoSubFolder.getPhotoFiles().size(),is(3));
        assertThat(photoSubFolder.getPhotoFiles().get(0).getFile().getPath(),is(TestConstants.TEST_NEW_DOCUMENT_1));
        assertThat(photoSubFolder.getPhotoFiles().get(0).getFileType(),is(FileType.TXT));
        System.out.println(photoSubFolder.getPhotoFiles().get(0).getFile().getPath());
        assertThat(photoSubFolder.getPhotoFiles().get(1).getFile().getPath(),is(TestConstants.TEST_NEW_DOCUMENT_2));
        assertThat(photoSubFolder.getPhotoFiles().get(1).getFileType(),is(FileType.TXT));
        System.out.println(photoSubFolder.getPhotoFiles().get(1).getFile().getPath());
        assertThat(photoSubFolder.getPhotoFiles().get(2).getFile().getPath(),is(TestConstants.TEST_NEW_PICTURE_1));
        assertThat(photoSubFolder.getPhotoFiles().get(2).getFileType(),is(FileType.JPG));
        System.out.println(photoSubFolder.getPhotoFiles().get(2).getFile().getPath());
    }

    @Test(expected = RuntimeException.class)
    public void
    throw_exception_when_file_rather_than_folder() {
        File file = new File(TestConstants.TEST_ORIGINAL_DOCUMENT_1);
        PhotoSubFolder photoSubFolder = new PhotoSubFolder(file);
    }

    @Test
    public void
    ignore_specific_files_when_requested() {
        File file = new File(TestConstants.TEST_ORIGINAL_SUBFOLDER);
        PhotoSubFolder photoSubFolder = new PhotoSubFolder(file);
        assertThat(photoSubFolder.getPhotoFiles().size(),is(3));

        HashMap<FileType,Integer> fileTypeCount = photoSubFolder.getPhotoFilesByFileTypeSubTotals();
        assertThat(fileTypeCount.size(),is(2));
        assertThat(fileTypeCount.get(FileType.TXT),is(2));
        assertThat(fileTypeCount.get(FileType.JPG),is(1));

        Map<FileCategory,Integer> fileCategoryCount = photoSubFolder.getCountOfFilesInFileCategory();
        assertThat(fileCategoryCount.size(),is(2));
        assertThat(fileCategoryCount.get(FileCategory.DOCUMENT),is(2));
        assertThat(fileCategoryCount.get(FileCategory.PHOTO),is(1));

        photoSubFolder.ignoreDuplicatedPhotoFile(photoSubFolder.getPhotoFiles().get(0));
        photoSubFolder.ignoreDuplicatedPhotoFile(photoSubFolder.getPhotoFiles().get(1));

        HashMap<FileType,Integer> fileTypeCountAfterwards = photoSubFolder.getPhotoFilesByFileTypeSubTotals();
        assertThat(fileTypeCountAfterwards.size(),is(2));
        assertThat(fileTypeCountAfterwards.get(FileType.TXT),is(2));
        assertThat(fileTypeCountAfterwards.get(FileType.JPG),is(1));

        Map<FileCategory,Integer> fileCategoryCountAfterwards = photoSubFolder.getCountOfFilesInFileCategory();
        assertThat(fileCategoryCountAfterwards.size(),is(2));
        assertThat(fileCategoryCountAfterwards.get(FileCategory.DOCUMENT),is(0));
        assertThat(fileCategoryCountAfterwards.get(FileCategory.PHOTO),is(1));
    }


    @Test
    public void
    check_for_misplaced_SubFolder() {
        File file = new File(TestConstants.TEST_MISPLACED_SUBFOLDER);
        PhotoSubFolder photoSubFolder = new PhotoSubFolder(file);
        assertThat(photoSubFolder.getPhotoFiles().size(),is(0));
        assertThat(photoSubFolder.getCountOfMisplacedSubFolders(),is(1));
    }

//    @Test
//    public void
//    contain_a_list_of_photoFiles() {
//        // given
//        PhotoSubFolder photoSubFolder = new PhotoSubFolder(new File("D:\\Family Photo Library\\2001-12-09 Dec01 Bournmouth (Sue & Paul's)"));
//
//        // then
//        for (PhotoFile photoFile : photoSubFolder.getPhotoFiles()) {
//            System.out.println(photoFile.getFile().getName());
//        }
//    }

//    @Test
//    public void
//    provide_totals_by_fileType() {
//        // given
//        PhotoFile photoFile1 = PhotoFileBuilder.aPhotoFile()
//                .withFile(new File("a"))
//                .withFileType(FileType.BMP)
//                .build();
//        PhotoFile photoFile2 = PhotoFileBuilder.aPhotoFile()
//                .withFile(new File("a"))
//                .withFileType(FileType.DOC)
//                .build();
//        ArrayList<PhotoFile> photoFiles = new ArrayList<PhotoFile>();
//        photoFiles.add(photoFile1);
//        photoFiles.add(photoFile2);
//        PhotoSubFolder photoSubFolder1 = PhotoSubFolderBuilder.aPhotoSubFolder()
//                .withPhotoFiles(photoFiles)
//                .build();
//
//        // when
//        HashMap<FileType, Integer> totals = photoSubFolder1.getPhotoFilesByFileTypeSubTotals();
//
//        // then
//        for (Map.Entry<FileType,Integer> i : totals.entrySet()){
//            System.out.println(i.getKey().name() + "=" + i.getValue());
//        }
//        assertThat(totals.size(),is(2));
//    }


//    @Test
//    public void
//    create_revised_folderStructure(){
//
//        HashMap<FileCategory,Integer> countOfFileCategories = new HashMap<FileCategory, Integer>();
//        countOfFileCategories.put(FileCategory.PHOTO,1);
//        PhotoSubFolder photoSubFolder = PhotoSubFolderBuilder.aPhotoSubFolder()
//                .withFile(new File("A"))
//                .withCountOfFilesInFileCategory(countOfFileCategories)
//                .withRevisedSubFolderName("revisedSubFolderName")
//                .build();
//        photoSubFolder.createRevisedFolderStructure("D:\\New XXXXX folder - Revised Version");
//    }

//    @Test
//    public void
//    copy_files_to_revised_folderStructure(){
//
//        PhotoFile photoFile1 = PhotoFileBuilder.aPhotoFile()
//                .withFile(new File("picture.jpg")).withFileType(FileType.JPG)
//                .withRevisedFilename("picture.jpg")
//                .build();
//        PhotoFile photoFile2 = PhotoFileBuilder.aPhotoFile()
//                .withFile(new File("video.mp4")).withFileType(FileType.MP4)
//                .withRevisedFilename("video.mp4")
//                .build();
//        ArrayList<PhotoFile> photoFiles = new ArrayList<PhotoFile>();
//        photoFiles.add(photoFile1);
//        photoFiles.add(photoFile2);
//
//        HashMap<FileCategory,Integer> countOfFileCategories = new HashMap<FileCategory, Integer>();
//        countOfFileCategories.put(FileCategory.PHOTO,1);
//        countOfFileCategories.put(FileCategory.VIDEO,1);
//
//        PhotoSubFolder photoSubFolder = PhotoSubFolderBuilder.aPhotoSubFolder()
//                .withFile(new File("A"))
//                .withPhotoFiles(photoFiles)
//                .withCountOfFilesInFileCategory(countOfFileCategories)
//                .withRevisedSubFolderName("revisedSubFolderName")
//                .build();
//
//        System.out.println(photoSubFolder.toString());
//        photoSubFolder.copyRevisedFileToRevisedSubFolder("D:\\New XXXXX folder - Revised Version");
//    }

}
