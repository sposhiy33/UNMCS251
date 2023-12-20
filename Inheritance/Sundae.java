/**
 * @author Shrey Poshiya
 * 
 * A Sundae class that extends the IceCream class, and thus inherently extends
 * the Dessert class. Sundae object represents an IceCream object "topped" with
 * a Dessert object.
 */

public class Sundae extends IceCream {

    /**
     * Constructor class for Sundae that take an IceCream object and tops it
     * with any Dessert object (candy, cookie, ice cream, even another sundae) 
     *
     * name = [ name of IceCream object ] + "topped with" + [ name of Dessert
     *          object ]
     *
     * price = [ price of IceCream object] + [ price of Dessert object ]
     *
     * @param iceCream - any IceCream object
     * @param dessert - any Dessert object
     */
    public Sundae(IceCream iceCream, Dessert dessert) {
        
        super( iceCream.getName() + " topped with " + dessert.getName(), 
               iceCream.getPrice() + dessert.getPrice() );
    }

}
