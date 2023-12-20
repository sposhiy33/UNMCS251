/**
 * @author Shrey Poshiya
 * This is a simple class to demonstrate "FizzBuzz" 
 */

public class FizzBuzz {
    
    /**
     *
     * This program prints all numbers from 1 up to a user-defined limit,
     * and identifies all mutiples of two user-defined numbers, by printing 
     * "Fizz" if the value is a mutiple of the first number, "Buzz" if the
     * value is a multiple of the second number, or "FizzBuzz" if the value
     * is a multiple of both numbers.
     *
     * Assumes that user passes 3 positive intergers through command-line arguments     
     * 
     * @param args | string array of command line arguments
     *  
     */
    public static void main(String[] args) {

        // parse arguments from command line 
        // use static method from Integer to convert from string to int type 
        int factorOne = Integer.parseInt(args[0]);
        int factorTwo = Integer.parseInt(args[1]);
        int upperLimit = Integer.parseInt(args[2]);

        // if any one of the factors is bigger than the upperlimit, raise error
        if ((factorOne>upperLimit) || (factorTwo>upperLimit)) {
            
            System.out.println("EITHER FACTOR IS TOO LARGE \n FACTORS MUST BE < THE UPPERLIMIT"); 
        
        } else {
            
            // for loop interating from "1" to value of the "upperlimit"
            for (int i=1; i<=upperLimit; i++) {
        
                if ((i % factorOne == 0) && (i % factorTwo == 0)) {
                    System.out.println("FizzBuzz");    
                } else if ((i % factorOne == 0)) {
                    System.out.println("Fizz");
                } else if ((i % factorTwo == 0)) {
                    System.out.println("Buzz");
                } else {
                    System.out.println(i);
                }               				
             
            }
        }
    }
}
