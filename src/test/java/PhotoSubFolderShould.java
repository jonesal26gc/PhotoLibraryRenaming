import classes.PhotoSubFolder;
import enums.FileCategory;
import enums.FileType;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class PhotoSubFolderShould {

    @Test
    public void
    identify_3_files_of_an_original_format_subfolder() {
        File file = new File(TestConstants.TEST_ORIGINAL_SUBFOLDER);
        PhotoSubFolder photoSubFolder = new PhotoSubFolder(file);
        assertTrue(photoSubFolder.isOriginalSubFolderNameFormat());
        assertFalse(photoSubFolder.isNewSubFolderNameFormat());
        assertThat(photoSubFolder.getPhotoFiles().size(), is(5));
        assertThat(photoSubFolder.getPhotoFiles().get(0).getFile().getPath(), is(TestConstants.TEST_ORIGINAL_DOCUMENT_1));
        assertThat(photoSubFolder.getPhotoFiles().get(0).getFileType(), is(FileType.TXT));
        System.out.println(photoSubFolder.getPhotoFiles().get(0).getFile().getPath());
        assertThat(photoSubFolder.getPhotoFiles().get(1).getFile().getPath(), is(TestConstants.TEST_ORIGINAL_DOCUMENT_2));
        assertThat(photoSubFolder.getPhotoFiles().get(1).getFileType(), is(FileType.TXT));
        System.out.println(photoSubFolder.getPhotoFiles().get(1).getFile().getPath());

        assertThat(photoSubFolder.getPhotoFiles().get(2).getFile().getPath(), is(TestConstants.TEST_ORIGINAL_VIDEO_1));
        assertThat(photoSubFolder.getPhotoFiles().get(2).getFileType(), is(FileType.MOV));
        System.out.println(photoSubFolder.getPhotoFiles().get(2).getFile().getPath());
        assertThat(photoSubFolder.getPhotoFiles().get(3).getFile().getPath(), is(TestConstants.TEST_ORIGINAL_VIDEO_2));
        assertThat(photoSubFolder.getPhotoFiles().get(3).getFileType(), is(FileType.MOV));
        System.out.println(photoSubFolder.getPhotoFiles().get(3).getFile().getPath());

        assertThat(photoSubFolder.getPhotoFiles().get(4).getFile().getPath(), is(TestConstants.TEST_ORIGINAL_PICTURE_1));
        assertThat(photoSubFolder.getPhotoFiles().get(4).getFileType(), is(FileType.JPG));
        System.out.println(photoSubFolder.getPhotoFiles().get(4).getFile().getPath());
    }

    @Test
    public void
    identify_3_files_of_a_new_format_subfolder() {
        File file = new File(TestConstants.TEST_NEW_SUBFOLDER);
        PhotoSubFolder photoSubFolder = new PhotoSubFolder(file);
        assertFalse(photoSubFolder.isOriginalSubFolderNameFormat());
        assertTrue(photoSubFolder.isNewSubFolderNameFormat());
        assertThat(photoSubFolder.getPhotoFiles().size(), is(3));
        assertThat(photoSubFolder.getPhotoFiles().get(0).getFile().getPath(), is(TestConstants.TEST_NEW_DOCUMENT_1));
        assertThat(photoSubFolder.getPhotoFiles().get(0).getFileType(), is(FileType.TXT));
        System.out.println(photoSubFolder.getPhotoFiles().get(0).getFile().getPath());
        assertThat(photoSubFolder.getPhotoFiles().get(1).getFile().getPath(), is(TestConstants.TEST_NEW_DOCUMENT_2));
        assertThat(photoSubFolder.getPhotoFiles().get(1).getFileType(), is(FileType.TXT));
        System.out.println(photoSubFolder.getPhotoFiles().get(1).getFile().getPath());
        assertThat(photoSubFolder.getPhotoFiles().get(2).getFile().getPath(), is(TestConstants.TEST_NEW_PICTURE_1));
        assertThat(photoSubFolder.getPhotoFiles().get(2).getFileType(), is(FileType.JPG));
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
        assertThat(photoSubFolder.getPhotoFiles().size(), is(5));

        HashMap<FileType, Integer> fileTypeCount = photoSubFolder.getPhotoFilesByFileTypeSubTotals();
        assertThat(fileTypeCount.size(), is(3));
        assertThat(fileTypeCount.get(FileType.TXT), is(2));
        assertThat(fileTypeCount.get(FileType.JPG), is(1));

        Map<FileCategory, Integer> fileCategoryCount = photoSubFolder.getCountOfFilesInFileCategory();
        assertThat(fileCategoryCount.size(), is(3));
        assertThat(fileCategoryCount.get(FileCategory.DOCUMENT), is(2));
        assertThat(fileCategoryCount.get(FileCategory.PHOTO), is(1));

        photoSubFolder.ignoreDuplicatedPhotoFile(photoSubFolder.getPhotoFiles().get(0));
        photoSubFolder.ignoreDuplicatedPhotoFile(photoSubFolder.getPhotoFiles().get(1));

        HashMap<FileType, Integer> fileTypeCountAfterwards = photoSubFolder.getPhotoFilesByFileTypeSubTotals();
        assertThat(fileTypeCountAfterwards.size(), is(3));
        assertThat(fileTypeCountAfterwards.get(FileType.TXT), is(2));
        assertThat(fileTypeCountAfterwards.get(FileType.JPG), is(1));

        Map<FileCategory, Integer> fileCategoryCountAfterwards = photoSubFolder.getCountOfFilesInFileCategory();
        assertThat(fileCategoryCountAfterwards.size(), is(3));
        assertThat(fileCategoryCountAfterwards.get(FileCategory.DOCUMENT), is(0));
        assertThat(fileCategoryCountAfterwards.get(FileCategory.PHOTO), is(1));
    }


    @Test
    public void
    check_for_misplaced_SubFolder() {
        File file = new File(TestConstants.TEST_MISPLACED_SUBFOLDER);
        PhotoSubFolder photoSubFolder = new PhotoSubFolder(file);
        assertThat(photoSubFolder.getPhotoFiles().size(), is(0));
        assertThat(photoSubFolder.getCountOfMisplacedSubFolders(), is(1));
    }
}
