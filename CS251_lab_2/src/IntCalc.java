
/**
 * CS251 Spring 2020, lab section 003, TA: Ayush
 * Lab assignment 2: Integer Calculator!
 *
 * @author Jaeren William Tredway
 * jwtredway@unm.edu
 * @version 1.0
 *
 * This program takes exactly 3 arguments from the command line and performs
 * basic integer arithmetic
 */
public class IntCalc {


    /**
     * main method executes calculator expression based on 3 command line
     * arguments
     *
     * @param args String[] : expects 3 command line arguments:
     *      first argument: an integer
     *      second argument: an arithmetic operator: + - x or /
     *      third argument: an integer
     */
    public static void main (String[] args) {
        //ERROR HANDLING:
        // check if there are 3 command line arguments:
        if (args.length != 3) {
            System.out.println(usage());
            throw new IllegalArgumentException("Exactly 3 arguments required");
        }

        int intA;
        String op;
        int intB;

        // check that intA and intB are integers, while assigning them values
        // harvested from the command line args[]:
        try {
            intA = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe1) {
            System.out.println(usage());
            throw new IllegalArgumentException("First arg must be an integer");
        }
        try {
            intB = Integer.parseInt(args[2]);
        } catch (NumberFormatException nfe2) {
            System.out.println(usage());
            throw new IllegalArgumentException("Third arg must be an integer");
        }

        //assign the second argument:
        op = args[1];

        //check that second arg is an allowed arithmetic operator:
        if (!isOp(op)) {
            System.out.println(usage());
            System.exit(1);
        }

        //process arithmetic:
        int result = op.equals("+") ? intA + intB :
                op.equals("-") ? intA - intB :
                        op.equals("x") ? intA * intB :
                                intA / intB;


        //output to console:
        System.out.println(intA + " " + op + " " + intB);
        System.out.println(result);

    }//END main()

    /**
     * @return String: returns a String with instructions on what command
     * line arguments this program expects and what the program does.
     */
    private static String usage () {
        return "\n    IntCalc Usage:\n" +
        "    Please run the calculator by typing in:\n" +
        "    >java IntCalc intA op intB\n" +
        "    intA - an integer value\n    intB - an integer value\n" +
        "    op - one of the following arithmetic operations +, -, x, /\n" +
        "    IntCalc performs the arithmetic operation intA op intB\n" +
        "    and prints the results.\n";
    }//END usage()


    /**
     * @param op String: the second command line argument
     * @return boolean: true if the argument is an accepted operator,
     * false otherwise
     */
    private static boolean isOp (String op) {
        return op.equals("+") || op.equals("-") || op.equals("x") ||
                op.equals("/");
    }//END isOp()

}//END class IntCalc
