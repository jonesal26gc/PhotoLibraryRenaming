package classes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatCheckerForNewSubFolderName {

    private Pattern pattern;
    private static final String FORMAT_FOR_NEW_SUB_FOLDER_NAME =
            "^(19|20)[0-9]([0-9]|x)-([0-9][0-9]|xx)-([0-9][0-9]|xx) " +
                    "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec|xxx)([0-9][0-9]|xx) " +
                    ".*";

    public FormatCheckerForNewSubFolderName() {
        pattern = Pattern.compile(FORMAT_FOR_NEW_SUB_FOLDER_NAME);
    }

    public boolean validate(final String subFolderName) {
        Matcher matcher= pattern.matcher(subFolderName);
        return matcher.matches();
    }
}