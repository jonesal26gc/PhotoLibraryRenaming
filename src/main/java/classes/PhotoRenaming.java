package classes;

import enums.FileCategory;
import enums.FileType;
import enums.Month;
import java.io.File;

public class PhotoRenaming {
/********************************************************************
 * This program loops through the photo album and renames the files
 * more appropriately.
 */

    private static final boolean UPDATE_INDICATOR = true;
    private static final String NEW_LINE = "\n";
    private static int pictureNumber;
    private static int videoNumber;

    public static void doIt(String parentFolderName) {

        // Verify that the parent folder is actually a folder.
        File parentFolder = new File(parentFolderName);
        File[] listOfSubFolders = null;

        if ( ! parentFolder.isDirectory() ) {
            System.out.println("ERROR - Parent library is not actually a folder.");
        } else

        // Iterate through the sub-folders of this parent directory.
        listOfSubFolders = parentFolder.listFiles();
        for (File subFolder : listOfSubFolders) {

            // Process a sub-folder and the files within it.
            try {
                processSubFolder(subFolder);
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }

        }
    }

    private static void processSubFolder(File subFolder) throws Exception {
    /*******************************************************************
     * process a sub-folder and the files found within it.
     */
        // Display sub-folder name.
        System.out.print( NEW_LINE +NEW_LINE + "Sub-folder: " + subFolder.getName());

        // Check that this is actually a sub-folder.
        if ( ! (subFolder.isDirectory()) ) {
            throw new Exception("ERROR - expected sub-folder but found something else.");
        }

        // If this sub-folder title is already correctly set, then skip it.
        if ( checkSubFolderNameAlreadySet(subFolder.getName())) {
            return;
        }

        // Check the original sub-folder name prior to reformatting.
        if ( ! checkSubFolderNameFormat(subFolder.getName()) ) {
            if ( UPDATE_INDICATOR ) {
                throw new Exception("ERROR - sub-folder name format is invalid: " + subFolder.getName());
            } else {
                System.out.println("Error - sub-folder format is invalid: " + subFolder.getName());
            }
        } else {

            //Long bytes = Files.copy(subFolder.getPath(),subFolder.getPath(), StandardCopyOption.REPLACE_EXISTING);

            // Derive the reformatted sub-folder name.
            String revisedSubFolderName = renameSubFolder(subFolder.getName());
            System.out.print(" ==> " + revisedSubFolderName);

            // Rename this sub-folder.
            File revisedSubFolder;
            if ( UPDATE_INDICATOR ) {
                revisedSubFolder = new File(subFolder.getPath().replace(subFolder.getName()
                        , "").concat(revisedSubFolderName));
                subFolder.renameTo(revisedSubFolder);
            } else {
                revisedSubFolder = subFolder;
            }

            // Iterate through the files in the sub-folder that will require renaming themselves.
            File[] listOfFiles = revisedSubFolder.listFiles();
            pictureNumber = videoNumber = 0;
            for (File targetFile : listOfFiles) {
                processFile(revisedSubFolder, targetFile);
            }
        }
    }

    private static boolean checkSubFolderNameAlreadySet(String subFolderName) {
        /***************************************************************
         * Check whether the sub-folder already has the new format.
         */
        CheckerForNewSubFolderName s = new CheckerForNewSubFolderName();
        return s.validate(subFolderName);
    }

    private static String renameSubFolder(String subFolderName) {
        /******************************************************************
         * Set the new name for the sub-folder.
         */

        // Split the original name into two parts.
        String part1 = subFolderName.substring(0,21);
        String part2 = subFolderName.substring(21);

        // Formulate the replacement part one.
        String revisedPart1 = part1.substring(0, 4)
                + part1.substring(4,5)
                + part1.substring(5, 7)
                + "-"
                + part1.substring(8, 10)
                + " "
                + Month.findAbbreviatedName(part1.substring(5, 7))
                + part1.substring(2, 4)
                + " ";

        // Return the revised name.
        return (revisedPart1 + part2);
    }

    private static boolean checkSubFolderNameFormat(String subFolderName) {
        /***************************************************************
         * Check the format of the original sub-folder name for suitability
         * prior to reformatting.
         */
        CheckerForOriginalSubFolderName s = new CheckerForOriginalSubFolderName();
        return s.validate(subFolderName);
    }

    private static void processFile(File subFolder, File targetFile) throws Exception {
        /**************************************************************************
         * Process a file within the sub-folder.
         */

        // Check that this is indeed a file.
        if ( ! targetFile.isFile() ) {
            throw new Exception("ERROR - expected file(s) but found sub-folder: " + targetFile.getName());
        }

        // display the original filename.
        //System.out.println("File: " + targetFile.getName());

        // Determine the type of the file.
        FileType ft = FileType.findFileTypeFromFilename(targetFile.getName());

        // Process the file according to type.
        if ( ft.getFileCategory().equals(FileCategory.PHOTO)) {
            System.out.print(NEW_LINE + "Information - Picture file found - renaming: " + targetFile.getName());

            // Rename the file.
            if ( UPDATE_INDICATOR ) {
                // Increment the file sequence number.
                pictureNumber++;

                // Set the output file name.
                String revisedTargetFileName = setNewNameForTargetFile(subFolder.getName(),targetFile.getName(), pictureNumber);
                System.out.print(" ==> " + revisedTargetFileName);

                File revisedTargetFile = new File(targetFile.getPath()
                        .replace(targetFile.getName(),revisedTargetFileName));
                targetFile.renameTo(revisedTargetFile);
            }

        } else  if ( ft.getFileCategory().equals(FileCategory.DOCUMENT) ) {
            System.out.print(NEW_LINE + "Information - Document/Text file found - retaining: " + targetFile.getName());

        } else  if ( ft.getFileCategory().equals(FileCategory.VIDEO) ) {
            System.out.print(NEW_LINE + "Information - Video file found - renaming and moving: " + targetFile.getName());

            if ( UPDATE_INDICATOR ) {

                // Increment the video number.
                videoNumber++;

                // Move the file to the video library.
                File videoSubFolder = new File(subFolder.getPath().replaceFirst("Photo", "Video"));
                if ( ! videoSubFolder.exists() ) {
                    if ( ! videoSubFolder.mkdir() ) {
                        throw new Exception("Error - failed to create video sub-folder: " + videoSubFolder.getName());
                    }
                }

                // Set the output file name.
                String revisedTargetFileName = setNewNameForTargetFile(subFolder.getName(),targetFile.getName(), videoNumber);
                System.out.print(" ==> " + revisedTargetFileName);

                File revisedTargetFile = new File(targetFile.getPath().replaceFirst("Photo", "Video")
                                                    .replace(targetFile.getName(),revisedTargetFileName));
                targetFile.renameTo(revisedTargetFile);
            }

        } else  if ( ft.getFileCategory().equals(FileCategory.VIDEO_ANALYSIS) ) {
            System.out.print(NEW_LINE + "Information - Video analysis file found - retaining: " + targetFile.getName());

        } else  if ( ft.getFileCategory().equals(FileCategory.RUBBISH) ) {
            System.out.print(NEW_LINE + "Warning - redundant file found - deleting: " + targetFile.getName());
            if (UPDATE_INDICATOR) {
                targetFile.delete();
            }
        }
    }

    public static String setNewNameForTargetFile(String folderName, String fileName, int fileNumber) {
        /********************************************************************
         * Set the new name for the file.
         */
        int commentStartPos = fileName.indexOf('[');
        int commentEndPos = fileName.indexOf(']');
        String commentField;

        if ( commentStartPos >=0
            & commentEndPos > 0
            & commentStartPos < commentEndPos ) {
            commentField=" " + fileName.substring(commentStartPos,commentEndPos+1);
        } else {
            commentField="";
        }

        return ( folderName.substring(0,17) +
                String.format("#%03d ", fileNumber) +
                folderName.substring(17) +
                commentField +
                fileName.substring(fileName.indexOf('.')) ) ;
    }
}
