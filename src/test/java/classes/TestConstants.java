package classes;

import java.io.File;

public class TestConstants {

    public static final String REPOSITORY_LOCATION_PREFIX = "c:/Users/tonyj";
    public static final String TEST_ORIGINAL_LIBRARY = REPOSITORY_LOCATION_PREFIX.concat("/Projects/PhotoLibraryRenaming/src/test/data/Original Library");
    public static final String TEST_ORIGINAL_SUBFOLDER = TEST_ORIGINAL_LIBRARY + "/2001-01-01 Jan \'01 - Description";
    public static final String TEST_ORIGINAL_DOCUMENT_1 = TEST_ORIGINAL_SUBFOLDER + "/Document_1.txt";
    public static final String TEST_ORIGINAL_DOCUMENT_2 = TEST_ORIGINAL_SUBFOLDER + "/Document_2.txt";
    public static final String TEST_ORIGINAL_PICTURE_1 = TEST_ORIGINAL_SUBFOLDER + "/Picture_1 [Amy on trampoline].jpg";
    public static final String TEST_ORIGINAL_VIDEO_1 = TEST_ORIGINAL_SUBFOLDER + "/IMG_0389 (2).MOV";
    public static final String TEST_ORIGINAL_VIDEO_2 = TEST_ORIGINAL_SUBFOLDER + "/IMG_0389.MOV";

    public static final String TEST_NEW_SUBFOLDER = REPOSITORY_LOCATION_PREFIX.concat("/Projects/PhotoLibraryRenaming/src/test/data/New Library/2001-01-01 Jan01 {2} Description");
    public static final String TEST_NEW_DOCUMENT_1 = TEST_NEW_SUBFOLDER + "/2001-01-01 Jan01 #0001 Description.txt";
    public static final String TEST_NEW_DOCUMENT_2 = TEST_NEW_SUBFOLDER + "/2001-01-01 Jan01 #0002 Description.txt";
    public static final String TEST_NEW_PICTURE_1 = TEST_NEW_SUBFOLDER + "/2001-01-01 Jan01 #0003 Description [Amy on trampoline].jpg";

    public static final String TEST_MISPLACED_SUBFOLDER = REPOSITORY_LOCATION_PREFIX.concat("/Projects/PhotoLibraryRenaming/src/test/data/Misplaced Library");

    public static final String DESTINATION_LOCATION = REPOSITORY_LOCATION_PREFIX.concat("/Projects/PhotoLibraryRenaming/src/test/data");

    public static String separatorsToSystem(String path) {
        if (path == null) return null;
        if (File.separatorChar == '\\') {
            // From Windows to Linux/Mac
            return path.replace('/', File.separatorChar);
        } else {
            // From Linux/Mac to Windows
            return path.replace('\\', File.separatorChar);
        }
    }
}