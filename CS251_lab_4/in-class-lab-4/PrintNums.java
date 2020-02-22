/**
*@author Jaeren Tredway
*CS251 Lab 4 in class exercise-2
*/
public class PrintNums {
    
    static final int MAX = 10;
    
    /**
    *This prints out a stacked list of numbers from (0) increasing up to (0 to 10).
    */
    public static void main (String[] args) {
        System.out.println();
        for (int i = 0; i <= MAX; i++) {
            for (int j = 0; j < (i+1); j++) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }
    
}
