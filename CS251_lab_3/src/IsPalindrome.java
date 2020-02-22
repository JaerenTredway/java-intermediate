/**
 * CS251 Lab Assignment 3, question 2
 * @author Jaeren Tredway
 * @version 1.0
 */
public class IsPalindrome {

    /**
     * main() method checks input and prints the isPalindrome() method
     * @param args requires one command line argument that is a string
     */
    public static void main (String[] args) {

        if (args.length < 1) {
            throw new IllegalArgumentException("You must enter 1 argument " +
                    "that is a string");
        }

        String inputString = args[0];

        System.out.println(isPalindrome(inputString));
    }


    /**
     * @param input String: word to test
     * @return String: that says if the input is a palindrome
     */
    private static String isPalindrome (String input) {

        boolean isPal = true;
        int start = 0;
        int end = input.length() - 1;

        while (start < end) {
            if (input.charAt(start) != input.charAt(end)) {
                isPal = false;
            }
            start++;
            end--;
        }

        String verb = isPal ? " is" : " is not";

        return (input + verb + " a palindrome.\n");
    }

}
