/**
 * @author Jaeren Tredway
 * @version 1.0
 * CS251 Lab 4 in class exercise-1
*/
public class IsPrime {
    
    /**
     * This program checks exactly one command line argument (that is an
     * integer) to see if it is prime and reports the result on the command
     * line.
    */
    public static void main(String[] args) {
        
        if (args.length != 1) {
            throw new IllegalArgumentException("Exactly one command line " +
                    "argument required.");
        }
        
        int inputNumber;
        
        try {
            inputNumber = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe1) {
            throw new IllegalArgumentException("You must enter exactly one  " +
                    "command line argument that is an integer.");
        }
        
        int i = 2;
        boolean isPrime = true;
        
        if (inputNumber <= 1)  {
            isPrime = false;
        } else {
            while(i <= inputNumber / 2) {
                if(inputNumber % i == 0) {
                    isPrime = false;
                    break;
                }
                i++;
            }
        }

        
        String result = isPrime ? " is " : " is not ";
        
        System.out.println(inputNumber + result + "a prime number.");
    }//END main()
}//END class IsPrime
