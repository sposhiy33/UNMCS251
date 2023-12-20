/**
 * @author Shrey Poshiya
 *
 * A custom checked runtime exception that is raised when the user
 * tries to withdraw more money than is present in the specified
 * bank account balance.
 */

public class InsufficientFundsException extends Exception {
    
    private double shortfall;
   
    /**
     * Constructor of InsufficientFundsException that passes a message to
     * the "Exception" constructor that dispalys the message in the stack
     * trace. Also takes the amount of money that the user is short and
     * stores it as a member variable.
     *
     * @param double value - amount of money that the user is short
     */
    public InsufficientFundsException(double value) {
        super("You need more Money");
        this.shortfall = value;
    }
    
    /**
     * Accessor method that returns the amount of money that the user is short
     * @return double representing the shortfall amount
     */
    public double getShortfall() {
        return this.shortfall;
    }
}
