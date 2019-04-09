package enums;

/**
 * Created by xm39 on 06/01/2017.
 */
public enum Month {
     JAN ("Jan", "January", 1)
    ,FEB ("Feb", "February", 2)
    ,MAR ("Mar", "March", 3)
    ,APR ("Apr", "April", 4)
    ,MAY ("May", "May", 5)
    ,JUN ("Jun", "June", 6)
    ,JUL ("Jul", "July", 7)
    ,AUG ("Aug", "August", 8)
    ,SEP ("Sep", "September", 9)
    ,OCT ("Oct", "October", 10)
    ,NOV ("Nov", "November", 11)
    ,DEC ("Dec", "December", 12);

    private String abbreviatedName;
    private String name;
    private int number;

    Month(String abbrev, String name, int number) {
        this.abbreviatedName = abbrev;
        this.name = name;
        this.number = number;
    }

    public String getAbbreviatedName() {
        return abbreviatedName;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public static StringBuffer listMonths() {
        StringBuffer sb = new StringBuffer();
        for ( Month month : Month.values()) {
            sb.append(month.name() + ' ');
        }
        return sb;
    }

    public static String findAbbreviatedName(int monthNumber){
        for ( Month month : Month.values()){
            if ( month.getNumber() == monthNumber ) {
                return month.getAbbreviatedName();
            }
        }
        return "xxx";
    }

    public static String findAbbreviatedName(String monthNumberAsString) {
        try {
            return findAbbreviatedName(Integer.parseInt(monthNumberAsString));
        } catch ( NumberFormatException ex ) {
            return findAbbreviatedName(0);
        }
    }
}
