import builders.PhotoFileBuilder;
import builders.PhotoFolderBuilder;
import builders.PhotoSubFolderBuilder;
import classes.PhotoFile;
import classes.PhotoFolder;
import classes.PhotoSubFolder;
import enums.FileCategory;
import enums.FileType;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PhotoFolderShould {

    @Test
    public void
    process() {
        PhotoFolder photoFolder = new PhotoFolder(new File(TestConstants.TEST_ORIGINAL_LIBRARY), TestConstants.DESTINATION_LOCATION);
        photoFolder.displayFolderAndSubFolderSummary();
        photoFolder.generateRevisedPhotoSubFolders();
    }

    @Test
    public void
    check_that_its_a_folder() {
        PhotoFolder photoFolder = PhotoFolderBuilder.aPhotoFolder().build();
        photoFolder.checkThatItIsFolder(new File(TestConstants.TEST_ORIGINAL_LIBRARY));
    }

    @Test(expected = RuntimeException.class)
    public void
    check_when_its_not_a_folder() {
        PhotoFolder photoFolder = PhotoFolderBuilder.aPhotoFolder().build();
        photoFolder.checkThatItIsFolder(new File(TestConstants.TEST_ORIGINAL_DOCUMENT_1));
    }

    @Test
    public void
    provides_totals_by_fileType() {
        // given
        PhotoFile photoFile1 = PhotoFileBuilder.aPhotoFile().withFile(new File("a")).withFileType(FileType.BMP).build();
        PhotoFile photoFile2 = PhotoFileBuilder.aPhotoFile().withFile(new File("b")).withFileType(FileType.DOC).build();
        ArrayList<PhotoFile> photoFiles = new ArrayList<PhotoFile>();
        photoFiles.add(photoFile1);
        photoFiles.add(photoFile2);
        PhotoSubFolder photoSubFolder = PhotoSubFolderBuilder.aPhotoSubFolder()
                .withPhotoFiles(photoFiles)
                .build();
        ArrayList<PhotoSubFolder> photoSubFolders = new ArrayList<PhotoSubFolder>();
        photoSubFolders.add(photoSubFolder);
        PhotoFolder photoFolder = PhotoFolderBuilder.aPhotoFolder().withPhotoSubFolders(photoSubFolders).build();

        // when
        HashMap<FileType, Integer> totals = photoFolder.getPhotoFilesByFileTypeTotals();

        // then
        assertThat(totals.size(), is(2));
        assertThat(totals.get(FileType.BMP), is(1));
        assertThat(totals.get(FileType.DOC), is(1));
    }

    @Test
    public void
    provides_totals_by_fileCategory() {
        // given
        HashMap<FileCategory, Integer> fileCategoryTable = new HashMap<FileCategory, Integer>();
        fileCategoryTable.put(FileCategory.DOCUMENT, 1);
        fileCategoryTable.put(FileCategory.PHOTO, 1);
        fileCategoryTable.put(FileCategory.VIDEO, 6);
        PhotoSubFolder photoSubFolder = PhotoSubFolderBuilder.aPhotoSubFolder()
                .withCountOfFilesInFileCategory(fileCategoryTable)
                .build();
        ArrayList<PhotoSubFolder> photoSubFolders = new ArrayList<PhotoSubFolder>();
        photoSubFolders.add(photoSubFolder);
        PhotoFolder photoFolder = PhotoFolderBuilder.aPhotoFolder()
                .withPhotoSubFolders(photoSubFolders)
                .build();

        // when
        HashMap<FileCategory, Integer> totals = photoFolder.getPhotoFilesByFileCategoryTotals();

        // then
        assertThat(totals.size(), is(3));
        assertThat(totals.get(FileCategory.DOCUMENT), is(1));
        assertThat(totals.get(FileCategory.PHOTO), is(1));
        assertThat(totals.get(FileCategory.VIDEO), is(6));
    }
}
