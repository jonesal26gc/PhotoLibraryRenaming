import classes.PhotoFile;
import classes.PhotoSubFolder;
import classes.PrimaryPhotoFolder;
import enums.FileType;
import org.junit.Test;
import java.util.Map;

public class PrimaryPhotoLibraryShould {

    @Test
    public void
    be_a_folder(){
        PrimaryPhotoFolder primaryPhotoFolder = new PrimaryPhotoFolder("D:\\Family Photo Library");
    }

    @Test(expected = RuntimeException.class)
    public void
    throw_error_when_not_a_folder(){
        PrimaryPhotoFolder primaryPhotoFolder = new PrimaryPhotoFolder("D:\\amily Photo Library");
    }

    @Test
    public void
    contain_a_list_of_sub_folders(){
        // given
        PrimaryPhotoFolder primaryPhotoFolder = new PrimaryPhotoFolder("D:\\Family Photo Library");

        // then
        for (PhotoSubFolder photoSubFolder : primaryPhotoFolder.getPhotoSubFolders()) {
            System.out.println(photoSubFolder.getSubFolderName());
            for (PhotoFile photoFile : photoSubFolder.getPhotoFiles()){
                System.out.println(photoFile.getFilename() + " of type " + photoFile.getFileType().name());
            }
            for (Map.Entry<FileType,Integer> i : photoSubFolder.getSummaryOfFileTypes().entrySet()){
                System.out.println(i.getKey().name() + "=" + i.getValue() );
            }
        }
        System.out.println("");
        for (Map.Entry<FileType,Integer> i : primaryPhotoFolder.getSummaryOfFileTypes().entrySet()){
            System.out.println(i.getKey().name() + "=" + i.getValue() );
        }
    }
}
