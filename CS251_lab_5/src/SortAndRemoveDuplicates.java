import java.util.Arrays;

/**
 * This sorts an array and removes duplicates.
 * @author Jaeren Tredway
 */
public class SortAndRemoveDuplicates {
    //example input = "1 2 2 3 4 5 5 9 10 10"
    //example output = "1 2 3 4 5 9 10"

    //CLASS VARIABLES:
    static String usage = "\nThis program takes a string of numbers separated" +
            " by white space, sorts them, removes duplicates, and returns the" +
            " sorted list.";
    static String[] stringNums;
    static int[] nums;

    //this removes the duplicates and prints the numbers:
    public static void printWithoutDuplicates (int[] nums) {
        int current = nums[0];
        boolean found = false;

        for (int i = 0; i < nums.length; i++) {
            if (current == nums[i] && !found) {
                found = true;
            } else if (current != nums[i]) {
                System.out.print(" " + current);
                current = nums[i];
                found = false;
            }
        }
        System.out.print(" " + current + "\n");
    }

    public static void main (String[] args) {
        //ERROR HANDLING and GATHER INPUT:
        if (args.length != 1) {
            System.out.println(usage);
            throw new IllegalArgumentException("\nExactly one command line " +
                    "argument required.");
        }

        try {
            stringNums = args[0].split(" ");
            nums = new int[stringNums.length];
            for(int i = 0; i < stringNums.length; i++) {
                nums[i] = Integer.parseInt(stringNums[i]);
            }
        } catch (NumberFormatException nfe_1) {
            System.out.println(usage);
            throw new IllegalArgumentException("\n\nA string of numbers " +
                    "separated by white spaces is required as the command " +
                    "line argument.");
        }//END gather input + error handling

        Arrays.sort(nums);

        printWithoutDuplicates(nums);

    }//END main()

}//END class SortAndRemoveDuplicates
