import enums.FileType;
import org.junit.Test;

public class FileTypeShould {

    @Test
    public void shouldFindComment(){


//        System.out.println(PhotoRenaming.setNewNameForTargetFile("2017-01-04 Jan17 xxxxx","Picture [something in here] 0001.jpg",1));
//        System.out.println(PhotoRenaming.setNewNameForTargetFile("2017-01-04 Jan17 xxxxx","Picture 0001.jpg",1));
//        System.out.println(PhotoRenaming.setNewNameForTargetFile("2017-01-04 Jan17 x","[something in here].jpg",1));

    }

    @Test
    public void
    record_varying_fileTypes() {

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
