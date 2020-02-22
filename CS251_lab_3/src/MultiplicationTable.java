/**
 * CS251 Lab Assignment 3, question 3
 * @author Jaeren Tredway
 * @version 1.0
 */
public class MultiplicationTable {

    /**
     * main() method checks input and prints the makeTable() method
     * @param args requires one command line argument that is an integer
     *             from 1 to 10
     */
    public static void main (String[] args) {

        int number = 0;

        if (args.length < 1) {
            throw new IllegalArgumentException("You must enter 1 argument " +
                    "that is an integer between 1 and 10");
        }

        try {
            number = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe1) {
            throw new IllegalArgumentException("Input must be an integer.");
        }

        if ((number < 1) || (number > 10)) {
            System.out.println("Provide an integer between 1 and 10");
            System.exit(1);
        }

        System.out.println(makeTable(number));
    }

    /**
     * @param number int: that will be used to make table
     * @return String: a table of the input multiplied up to x10
     */
    private static String makeTable (int number) {
        String table = "";

        for (int i = 1; i < 11; i++) {
            table += (number + " X " + i + " = " + (number * i) + "\n");
        }

        return table;
    }
}
