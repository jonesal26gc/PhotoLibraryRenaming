package enums;

import enums.Month;
import org.junit.Test;

/**
 * Created by xm39 on 06/01/2017.
 */
public class MonthTest {

    @Test
    public void
    should_display_month_names() {
        System.out.println(Month.listMonths());
    }

    @Test
    public void
    should_find_month_seq(){
        for (Month m : Month.values()) {
            if ( m.getNumber() == 10 ) {
                System.out.println("Month 10 is " + m.getName());
            }
        }
    }

    @Test
    public void
    should_return_abbreviatedName(){
        System.out.println(Month.findAbbreviatedName(2));
        System.out.println(Month.findAbbreviatedName("A"));
    }
}
