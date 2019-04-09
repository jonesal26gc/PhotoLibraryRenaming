package enums;

import enums.FileType;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

public class FileTypeTest {

    @Test
    public void
    should_encounter_each_fileType() {
        FileType ft;

        ft = FileType.findFileTypeFromFilename("xxxx.jpg");
        assertThat(ft.getFileCategory(), is(FileCategory.PHOTO));

        ft = FileType.findFileTypeFromFilename("xxxx.mov");
        assertThat(ft.getFileCategory(), is(FileCategory.VIDEO));

        ft = FileType.findFileTypeFromFilename("xxxx.doc");
        assertThat(ft.getFileCategory(), is(FileCategory.DOCUMENT));

        ft = FileType.findFileTypeFromFilename("xxxx.txt");
        assertThat(ft.getFileCategory(), is(FileCategory.DOCUMENT));

        ft = FileType.findFileTypeFromFilename("xxxx.cbl");
        assertThat(ft.getFileCategory(), is(FileCategory.RUBBISH));
    }
}
