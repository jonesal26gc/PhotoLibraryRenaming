import builders.PhotoFileBuilder;
import builders.PhotoFolderBuilder;
import builders.PhotoSubFolderBuilder;
import classes.PhotoFile;
import classes.PhotoFolder;
import classes.PhotoSubFolder;
import enums.FileCategory;
import enums.FileType;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PhotoFolderShould {
    private static final String SLASH_DELIMITER = Character.toString(File.separatorChar);

    @Test(expected = RuntimeException.class)
    public void
    generate_the_corresponding_output_files() {
        PhotoFolder photoFolder = new PhotoFolder(new File(TestConstants.TEST_ORIGINAL_LIBRARY), TestConstants.DESTINATION_LOCATION, false);
        photoFolder.generateRevisedPhotoSubFolders();
        File library = new File(TestConstants.DESTINATION_LOCATION);
        File document_1 = new File(TestConstants.DESTINATION_LOCATION
                .concat(SLASH_DELIMITER)
                .concat("Family Document Library - Revised Folders")
                .concat(SLASH_DELIMITER)
                .concat("2001-01-01 Jan01 {2} Description")
                .concat(SLASH_DELIMITER)
                .concat("Document_1.txt"));
        File document_2 = new File(TestConstants.DESTINATION_LOCATION
                .concat(SLASH_DELIMITER)
                .concat("Family Document Library - Revised Folders")
                .concat(SLASH_DELIMITER)
                .concat("2001-01-01 Jan01 {2} Description")
                .concat(SLASH_DELIMITER)
                .concat("Document_2.txt"));
        File photo_1 = new File(TestConstants.DESTINATION_LOCATION
                .concat(SLASH_DELIMITER)
                .concat("Family Photo Library - Revised Folders")
                .concat(SLASH_DELIMITER)
                .concat("2001-01-01 Jan01 {1} Description")
                .concat(SLASH_DELIMITER)
                .concat("2001-01-01 Jan01 #001 Description [Amy on trampoline].jpg"));
        File video_1 = new File(TestConstants.DESTINATION_LOCATION
                .concat(SLASH_DELIMITER)
                .concat("Family Video Library - Revised Folders")
                .concat(SLASH_DELIMITER)
                .concat("2001-01-01 Jan01 {1} Description")
                .concat(SLASH_DELIMITER)
                .concat("2001-01-01 Jan01 #001 Description.MOV"));
        int countOfDirectories = 0;
        for (String directoryName : library.list()) {
            if (directoryName.matches("Family .* Library - Revised Folders")) countOfDirectories++;
        }
        assertThat(countOfDirectories,is(3));
        assertTrue(document_1.exists());
        assertTrue(document_2.exists());
        assertTrue(photo_1.exists());
        assertTrue(video_1.exists());
        // compare the original and new files for identical contents??
        //assertEquals(FileUtils.readLines(new File(TestConstants.TEST_ORIGINAL_DOCUMENT_1).getPath(), FileUtils.readLines(output));
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
