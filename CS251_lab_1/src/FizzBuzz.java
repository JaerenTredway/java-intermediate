/**
 * CS251 Spring 2020, lab section 003, TA: Ayush
 * Lab assignment 1: FizzBuzz and Command Line Arguments
 *
 * @author Jaeren William Tredway
 * jwtredway@unm.edu
 * @version 1.0
 *
 * The program will print the numbers from 1 to the limit (including the
 * limit value), each on a separate line, replacing the multiples
 * of the first argument with “Fizz”, replacing multiples of the second
 * argument with “Buzz”, and replacing multiples of both with “FizzBuzz”.
 */
public class FizzBuzz {

    /**
     * main method executes FizzBuzz results based on 3 command line arguments
     * @param args String[] : expects 3 command line arguments that are
     *             positive integers (initially stored in args as Strings):
     *      first argument: multiples will be replaced with "Fizz"
     *      second argument: multiples will be replaced with "Buzz"
     *      third argument: the limit of numbers that will be parsed
     */
    public static void main (String[] args) {

        //ERROR HANDLING:
        // check if there are 3 command line arguments:
        if (args.length != 3) {
            throw new IllegalArgumentException("Exactly 3 arguments required");
        }

        int fizzValue;
        int buzzValue;
        int limit;

        // check that all args are integers, while assigning them values:
        try {
            fizzValue = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe1) {
            throw new IllegalArgumentException("First arg must be an integer");
            // or assign a default value: fizzValue = 3;
        }
        try {
            buzzValue = Integer.parseInt(args[1]);
        } catch (NumberFormatException nfe2) {
            throw new IllegalArgumentException("Second arg must be an integer");
            // or assign a default value: buzzValue = 5;
        }
        try {
            limit = Integer.parseInt(args[2]);
        } catch (NumberFormatException nfe3) {
            throw new IllegalArgumentException("Third arg must be an integer");
            // or assign a default value: limit = 100;
        }


        for (int i = 0; i <= limit; i++) {
            if (i % fizzValue == 0 && i % buzzValue == 0) {
                System.out.println("FizzBuzz");
                continue;
            } else if (i % fizzValue == 0) {
                System.out.println("Fizz");
            } else if (i % buzzValue == 0) {
                System.out.println("Buzz");
            } else {
                System.out.println(i);
            }
        }
    } //END main() method

}
