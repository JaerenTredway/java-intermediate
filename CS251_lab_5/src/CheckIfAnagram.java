import java.util.Arrays;
import java.util.Scanner;

/**
 * This program takes two strings and checks if they are anagrams of each other.
 * @author Jaeren Tredway
 */
public class CheckIfAnagram {
    //example input = "cinema iceman"
    //example output = true

    static String wordOne, wordTwo;

    //scans in user input of two strings:
    public static void getInput () {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ENTER string #1: ");
        wordOne = scanner.nextLine().toLowerCase();
        System.out.println("ENTER string #2: ");
        wordTwo = scanner.nextLine().toLowerCase();
    }

    //returns true if the two args are anagrams, false otherwise:
    public static boolean checkAnagram(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }

        char[] arr1 = str1.toCharArray();
        char[] arr2 = str2.toCharArray();

        Arrays.sort(arr1);
        Arrays.sort(arr2);

        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }

        return true;

    }//END checkAnagram()

    //check the input and report if the two strings are anagrams or not:
    public static void main (String[] args) {
        getInput();
        System.out.print(wordOne + " and " + wordTwo);
        System.out.println(checkAnagram(wordOne, wordTwo) ? " are anagrams!"
                : " are not anagrams :(");
    }//END main()

}//END class CheckIfAnagram
