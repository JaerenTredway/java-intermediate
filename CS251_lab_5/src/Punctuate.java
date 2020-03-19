import java.util.Scanner;

/**
 * This program takes a sentence and removes the punctuation.
 * @author JaerenTredway
 */
public class Punctuate {

//  input = "The conference has people who have come from Moscow, Idaho; Sprigfield, California; Alamo, Tennessee; and other places as well.";

    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a sentence: ");
        String input = scanner.nextLine();

        String result = input.replaceAll("[^a-zA-Z ]", "");
        System.out.println("\noriginal input: ");
        System.out.println(input);
        System.out.println("\noutput: ");
        System.out.println(result);
    }//END main()

}
