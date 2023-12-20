import java.util.*;

/** 
 * Class that implements a stack (last-in, first-out) for doubles, 
 * built on LinkedList
 * @author Shrey Poshiya
 */
public class DoubleStack implements Stack<Double> {

    private LinkedList<Double> stack = new LinkedList<Double>();

    /**
     * Uses Linkedlist method .size() to retireve lenght of the stack. Method
     * returns whether or not the stack is empty.
     * @return boolean indiciating if list is empty of not
     */
    @Override
    public boolean isEmpty() { 
        if (stack.size() == 0) {
            return true;
        }
        else { return false; }
    } 

    /**
     * Method that adds specified double element to the end of the list, or
     * the "top of the stack"
     * @param val - a double value
     */
    @Override
    public void push(Double val) {
        stack.push(val);
    }   
  
    /**
     * Removes element at the top of the stack, and returns its value
     * @return double - value of element at top of the stack 
     */
    @Override
    public Double pop() {
        return stack.pop();
    }

    /**
     * Simple "peeks" at the top of the stack and returns the value of the
     * element at the top of the stack (last element of the LinkedList)
     * @return double - value of element at the top of the stack
     */
    @Override
    public Double peek() {
        return stack.peek();
    }
}
