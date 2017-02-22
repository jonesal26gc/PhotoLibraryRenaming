import classes.PhotoFile;
import classes.PhotoSubFolder;
import org.junit.Test;

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

}
