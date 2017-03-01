import classes.PhotoFile;
import enums.FileType;
import org.junit.Test;
import java.io.File;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PhotoFileShould {

    @Test
    public void
    perform_checksum(){
        File document = new File(TestConstants.ORIGINAL_TEST_DOCUMENT_FILE);
        PhotoFile photoFile = new PhotoFile(document);
        System.out.println("Hex value of CheckSum=" + photoFile.getCheckSumInHex());
        assertThat(photoFile.getCheckSumInHex(),is("7DE5112FC8A6BDEE859ED5D5C50DF766"));
        assertThat(photoFile.getFileType(),is(FileType.TXT));
        assertThat(photoFile.getFile().getName(),is("2001-01-01 Jan01 Description.txt"));
        assertThat(photoFile.isDuplicateHasBeenFoundElsewhere(),is(false));
    }
}
