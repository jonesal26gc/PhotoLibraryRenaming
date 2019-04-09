package enums;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

/**
 * Created by xm39 on 06/01/2017.
 */
public class MonthTest {

    @Test
    public void
    should_find_october_using_sequence() {
        for (Month m : Month.values()) {
            if (m.getNumber() == 10) {
                assertEquals(m.getName(), "October");
            }
        }
    }

    @Test
    public void
    should_return_abbreviatedName_valid() {
        assertThat(Month.findAbbreviatedName(2), is("Feb"));
    }

    @Test
    public void
    should_return_abbreviatedName_invalid() {
        assertThat(Month.findAbbreviatedName("A"), is("xxx"));
    }
}
