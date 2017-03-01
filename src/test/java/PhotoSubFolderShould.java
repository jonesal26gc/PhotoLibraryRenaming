import builders.PhotoSubFolderBuilder;
import classes.*;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PhotoSubFolderShould {

    @Test
    public void
    identify_an_original_subfolder_format() {
        File file = new File(TestConstants.ORIGINAL_TEST_DOCUMENT_SUBFOLDER);
        PhotoSubFolder photoSubFolder = new PhotoSubFolder(file);
        System.out.println(photoSubFolder.getSubFolder().getName());
        assertTrue(photoSubFolder.isOriginalSubFolderNameFormat());
        assertFalse(photoSubFolder.isNewSubFolderNameFormat());
    }

    @Test
    public void
    identify_a_new_subfolder_format() {
        File file = new File(TestConstants.NEW_TEST_DOCUMENT_SUBFOLDER);
        PhotoSubFolder photoSubFolder = new PhotoSubFolder(file);
        System.out.println(photoSubFolder.getSubFolder().getName());
        assertFalse(photoSubFolder.isOriginalSubFolderNameFormat());
        assertTrue(photoSubFolder.isNewSubFolderNameFormat());
        assertThat(photoSubFolder.getPhotoFiles().size(),is(1));
        //assertThat(photoSubFolder.getPhotoFiles().get(0).getFile().getName(),is(file.getName()));
    }


    @Test(expected = RuntimeException.class)
    public void
    throw_error_when_not_a_folder() {
        PhotoSubFolder photoSubFolder = new PhotoSubFolder(new File("D:\\Family Photo Library\\2001-12-09 Dec01 Bournmouth"));
    }

    @Test
    public void
    contain_a_list_of_photoFiles() {
        // given
        PhotoSubFolder photoSubFolder = new PhotoSubFolder(new File("D:\\Family Photo Library\\2001-12-09 Dec01 Bournmouth (Sue & Paul's)"));

        // then
        for (PhotoFile photoFile : photoSubFolder.getPhotoFiles()) {
            System.out.println(photoFile.getFile().getName());
        }
    }

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
