package classes;

import enums.FileType;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PhotoCount {
    /********************************************************************
     * This program loops through the photo album and renames the files
     * more appropriately.
     */

    private static final String NEW_LINE = "\n";

    public static void doIt(String parentFolderName) {

        Map<FileType, Integer> fileTypeTotalsTable = new HashMap<FileType, Integer>();

        System.out.println("Scanning: " + parentFolderName + " ..........");

        // Verify that the parent folder is actually a folder.
        File parentFolder = new File(parentFolderName);
        File[] listOfSubFolders = null;

        if (!parentFolder.isDirectory()) {
            System.out.println("ERROR - Parent library is not actually a folder.");
        } else

            // Iterate through the sub-folders of this parent directory.
            listOfSubFolders = parentFolder.listFiles();
        for (File subFolder : listOfSubFolders) {

            // Process a sub-folder and the files within it.
            try {

                // Process the sub-folder to return the file type counts.
                Map<FileType, Integer> fileTypeTable = processSubFolder(subFolder);

                // Add the counts for the file types in the sub-folder to totals.
                for (Map.Entry<FileType, Integer> e : fileTypeTable.entrySet()) {

                    if (fileTypeTotalsTable.containsKey(e.getKey())) {
                        int value = fileTypeTotalsTable.get(e.getKey());
                        fileTypeTotalsTable.put(e.getKey(), value + e.getValue());
                    } else {
                        fileTypeTotalsTable.put(e.getKey(), e.getValue());
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
        }

        // Show the total counts for the file types in the sub-folder.
        System.out.print(NEW_LINE + "Grand Totals:");
        for (Map.Entry<FileType, Integer> e : fileTypeTotalsTable.entrySet()) {
            System.out.print(NEW_LINE + "  " + e.getKey().name() + ": " + e.getValue());
            //if ( e.getKey().getFileCategory().equals(FileCategory.PICTURE) ) {
            System.out.print(String.format(" %s(s)", e.getKey().getFileCategory()));
            //}
        }
        System.out.print(NEW_LINE);
    }

    private static Map<FileType, Integer> processSubFolder(File subFolder) throws Exception {
        /*******************************************************************
         * process a sub-folder and the files found within it.
         */

        // Create a map of the file types to be counted.
        Map<FileType, Integer> fileTypeTable = new HashMap<FileType, Integer>();

        // Display sub-folder name.
        System.out.println(NEW_LINE + "Sub-folder: " + subFolder.getName());

        // Check that this is actually a sub-folder.
        if (!(subFolder.isDirectory())) {
            throw new Exception("ERROR - expected sub-folder but found something else.");
        }

        // Iterate through the files in the sub-folder that will require renaming themselves.
        File[] listOfFiles = subFolder.listFiles();
        int fileNumber = 0;
        for (File targetFile : listOfFiles) {
            fileNumber++;
            FileType ft = processFile(targetFile, fileNumber);

            // Add or increment the file type counter in the table.
            if (fileTypeTable.containsKey(ft)) {
                int value = fileTypeTable.get(ft);
                fileTypeTable.put(ft, value + 1);
            } else {
                fileTypeTable.put(ft, 1);
            }

        }

        // Show the counts for the file types in the sub-folder.
        System.out.print("  Sub-totals:");
        for (Map.Entry<FileType, Integer> e : fileTypeTable.entrySet()) {
            System.out.print("  " + e.getKey().name() + ": " + e.getValue());
        }
        System.out.print(NEW_LINE);

        return fileTypeTable;
    }

    private static FileType processFile(File targetFile, int fileNumber) throws Exception {
        /**************************************************************************
         * Process a file within the sub-folder.
         */

        // Check that this is indeed a file.
        if (!targetFile.isFile()) {
            throw new Exception("ERROR - expected file(s) but found sub-folder: " + targetFile.getName());
        }

        // Determine the type of the file.
        FileType ft = FileType.findFileTypeFromFilename(targetFile.getName());

        // display the original filename.
        if (ft.equals(FileType.XXX)) {
            System.out.println("> File " + String.format("%04d", fileNumber) + ": " + targetFile.getName());
        }

        return ft;
    }
}
