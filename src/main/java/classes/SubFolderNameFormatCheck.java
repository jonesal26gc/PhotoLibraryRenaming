package classes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubFolderNameFormatCheck {

        private Pattern pattern;
        private Matcher matcher;
        private static final String FILENAME_PATTERN_VALID =
        "^(19|20)[0-9]([0-9]|x)-([0-9][0-9]|xx)-([0-9][0-9]|xx) " +
        "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec|xxx) '([0-9][0-9]|xx) - .*";

        public SubFolderNameFormatCheck() {
            pattern = Pattern.compile(FILENAME_PATTERN_VALID);
        }

        public boolean validate(final String subFolderName) {
            matcher = pattern.matcher(subFolderName);
            return matcher.matches();
        }




}
