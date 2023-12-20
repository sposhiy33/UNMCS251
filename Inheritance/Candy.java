/**
 * @author Shrey Poshiya
 *
 * This class is an implementation of a Candy class, a subclass of Dessert.
 * Implements additional members to track weight and price per pound of
 * candies to calculate price.
 */

public class Candy extends Dessert {

    private double pricePerPound;
    private double weight;

    /**
     * Constructor class for Candy class that defines the name, weight, and
     * price per pound of the specific instance of Candy.
     *
     * @param name - string definition of name of the candy
     * @param weight - double value of the weight of candy
     * @param price - double value of the price per pound of candy
     */
    public Candy(String name, double weight, double price) {
        super(name);
        this.pricePerPound = price;
        this.weight = weight;
    }
    
    /**
     * Accessor method for the weight of candy specified in this instance
     * @return dobule representing weight in lb
     */
    public double getWeightInPounds() {
        return this.weight;
    }
   
    /**
     * Accessor method for the price per pound of this candy instance
     * @return double representing price per pound
     */ 
    public double getPricePerPound() {
        return this.pricePerPound;
    }
    
    /**
     * Calculates and returns the price of candy based on the weight and
     * and price per pound 
     *
     * @return double representing price of the candy
     */
    public double getPrice() {
        return this.pricePerPound * this.weight;
    }

}
