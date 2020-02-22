/**
 * CS251 Lab Assignment 3, question 1
 * @author Jaeren Tredway
 * @version 1.0
 */
public class IsLeapYear {

    /**
     * main() method checks input and prints the isLeapYear() method
     * @param args requires one command line argument that is a year between
     *             0 and 9999
     */
    public static void main (String[] args) {

        int year = 0;

        if (args.length < 1) {
            throw new IllegalArgumentException("You must enter 1 argument " +
                    "that is a year");
        }

        try {
            year = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe1) {
            throw new IllegalArgumentException("Input must be an integer.");
        }

        if ((year < 0) || (year > 9999)) {
            System.out.println("Enter a year from 0 to 9999");
            System.exit(1);
        }

        System.out.println(isLeapYear(year));

    }

    /**
     * @param year int: from 0 to 9999
     * @return String: that says if the input is a leap year
     */
    private static String isLeapYear (int year) {

        boolean isLeap;

        if ((year % 4 == 0) && (year % 100 != 0)) {
            isLeap = true;
        } else if ((year % 100 == 0) && (year % 400 == 0)) {
            isLeap = true;
        } else {
            isLeap = false;
        }


        String verb = isLeap ? "is" : "is not";
        return ("\n" + year + " " + verb + " a leap year.");
    }
}
