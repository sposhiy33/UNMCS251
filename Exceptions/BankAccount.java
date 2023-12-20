/**
 * @author Shrey Poshiya
 *
 * This class implements a bank account that implements a 
 */

public class BankAccount {
    
    private int accountNum;
    private double accountBalance;
    
    /**
     * Constructor for the Bank Account class, must specify an account number
     * for the given bank account instance
     *
     * @param int accountNum - int representing the account number of the
     *                         Bank Account instance
     */
    public BankAccount(int accountNum) {
        this.accountNum = accountNum;
    }
    
    /**
     * Accessor method to return the account number of the given Bank Account
     * instance
     *
     * @return int representing the account number
     */
    public int getAccountNumber() {
        return this.accountNum;
    }
    
    /**
     * Accessor method to return the account balance of the given Bank Account
     * instance 
     *
     * @return double representing the account balance
     */
    public double getBalance() {
        return this.accountBalance;
    }

    /**
     * Function that adds specified amount of money intot the account balance. 
     * If the user attempts to deposit a negative amount of money, an
     * IllegalArgumentException will be thrown. 
     *
     * @param double amount - amount of monwy to be deposited into the account
     *                        account balance
     */
    public void deposit(double amount) throws IllegalArgumentException {
             
        // Throw exception if user tries to deposit a negative amount
        if (amount < 0.0) {
            System.out.println("attempted to deposit a negative amount:" +
                    amount);
            throw new IllegalArgumentException();
        }

        // deposit specified amount into the account balance
        this.accountBalance += amount;
    }

    /**
     * Function that withdraws specified amount from the account balance. If
     * the amount specified is negative, an IllegalArgumentException exception
     * will be thrown. If more money is being withdrawn than exists in the 
     * account balance, an InsufficientFundsException will be thrown. 
     *
     * @param double amount - amount of money to be withdarawn from the account
     *                        balance
     */
    public void withdraw(double amount) throws InsufficientFundsException,
        IllegalArgumentException {
        
        // throw exception is the user tries to withdraw a negative amount
        if (amount < 0.0) {
            System.out.println("attempted to withdraw a negative amount:" +
                    amount);
            throw new IllegalArgumentException();
        }

        // throw exception if the user tries to withdraw more money
        // than is present in their balance
        if (amount > this.accountBalance) {
            // calculate shortfall
            double shortAmount = amount - this.accountBalance;
            throw new InsufficientFundsException(shortAmount);
        }
        
        // subtract from the account balance
        this.accountBalance -= amount;
    }
}
