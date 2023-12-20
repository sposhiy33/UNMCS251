/**
 * @author Shrey Poshiya
 * 
 * A Cookie class that extends the general Dessert class. Adds additional 
 * members to track the number of cookies and price per batch in the given
 * instance to calculate price.
 */

public class Cookie extends Dessert {

    private double pricePerDozen;
    private int count;


    /**
     * Cookie constructer method that defines the name of the cookie instance,
     * defined in the parent class, number of cookies, and the price per
     * dozen cookies
     *
     * @param name - string defining the name of the cookie
     * @param count - int defining the number of cookies
     * @param price - double defining the price per dozen
     */ 
    public Cookie(String name, int count, double price) {
        super(name);
        this.pricePerDozen = price;
        this.count = count;
    }
    
    /**
     * Accessor method for integer amount of cookies in the given instance
     * @return integer representing the number of cookies
     */
    public int getItemCount() {
        return this.count;
    }
    
    /**
     * Accessor method for the price per dozen of the given cookie
     * @return double representing price per dozen cookies
     */
    public double getPricePerDozen() {
        return this.pricePerDozen;
    }
    
    /**
     * Calculates the price of given batch of cookies based on the number
     * of cookies and the price per dozen cookies.  
     *
     * @return double representing the price
     */
    public double getPrice() { 
        // calculate the price per item from the dozen price
        double pricePerItem = this.pricePerDozen / 12;
        return ((double) this.count) * pricePerItem;
    }

}
