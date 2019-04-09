package enums;

import enums.FileType;
import org.junit.Test;

public class FileTypeTest {

    @Test
    public void
    should_encounter_varying_fileTypes() {

        FileType ft;
        ft = FileType.findFileTypeFromFilename("xxxx.jpg");
        System.out.println(ft.toString());
        ft = FileType.findFileTypeFromFilename("xxxx.mov");
        System.out.println(ft.toString());
        ft = FileType.findFileTypeFromFilename("xxxx.doc");
        System.out.println(ft.toString());
        ft = FileType.findFileTypeFromFilename("xxxx.txt");
        System.out.println(ft.toString());
        ft = FileType.findFileTypeFromFilename("xxxx.cbl");
        System.out.println(ft.toString());
    }
}
