package classes;

import classes.PhotoFile;
import enums.FileType;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PhotoFileTest {

    @Test
    public void
    should_perform_checksum() {
        File document = new File(TestConstants.TEST_ORIGINAL_DOCUMENT_1);
//        System.out.println(document.getPath());
        PhotoFile photoFile = new PhotoFile(document);
//        System.out.println("Hex value of CheckSum=" + photoFile.getCheckSumInHex());
        assertThat(photoFile.getCheckSumInHex(), is("CE2B4BE385447FBC0F12DD7731F4C9E1"));
        assertThat(photoFile.getFileType(), is(FileType.TXT));
        assertThat(photoFile.getFile().getName(), is("Document_1.txt"));
        assertThat(photoFile.isDuplicateHasBeenFoundElsewhere(), is(false));
    }
}
