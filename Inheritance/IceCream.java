/**
 * @author Shrey Poshiya
 *
 * An IceCream class that extends the Dessert super class.
 */

public class IceCream extends Dessert {

    private double price;

    /**
     * Constructor method that defines the name and price of the given
     * instance of ice cream
     * @param name - string definition of name
     * @param price - price of the ice cream
     */
    public IceCream(String name, double price) {
        super(name);
        this.price = price;
    }
    
    /**
     * Returns the price of the given instance of ice cream based off of the
     * price provided in the constructor (basically an accessor method)
     * @return double representing the price
     */
    public double getPrice() {
        return this.price;    
    }

}
