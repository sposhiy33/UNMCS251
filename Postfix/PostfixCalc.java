import java.util.*;

/**
 * Class to evaulate a given postfix expression
 * @author Shrey Poshiya
 */
public class PostfixCalc {

    // stack of double values in expression
    private Stack<Double> stack = new DoubleStack();
    // map relating string expression to operators
    private Map<String, Operator> operatorMap = new HashMap<>();    

    /**
     * Constructor Method - when calculator is instantiated, populate the
     * operatorMap, relating operater string expressions to Operator objects
     * defined later in the program
     */
    public PostfixCalc() {
        // populate operatorMap
        operatorMap.put("+", new addition());
        operatorMap.put("add", new addition());
        operatorMap.put("-", new subtraction());
        operatorMap.put("sub", new subtraction());
        operatorMap.put("*", new multiplication());
        operatorMap.put("mult", new multiplication());
        operatorMap.put("/", new division());
        operatorMap.put("div", new division());
        operatorMap.put("=", new print());
        operatorMap.put("print", new print());
    }
    
    /**
     * pushes specified double value to the top of the double stack
     * @param val - double value
     */
    public void storeOperand(double val) {
        stack.push(val);
    }
    
    /**
     * Given the operator string, complete the operation and push
     * the resulting double value to the stack
     * @param String - string expression of operator
     */
    public void evalOperator(String operatorString) {
   
        Operator operator = operatorMap.get(operatorString);
        List<Double> operandList = new ArrayList<Double>();
   
        // depending on the number of arguments the specified argument,
        // remove that number of double values of the stack and add
        // them to an ArrayList to be evaluated by the operator.
        for (int i=0; i<operator.numArgs(); i++) {
            operandList.add(stack.pop());
        }
        
        double val = operator.eval(operandList);
        // push result of the operator back onto the stack
        stack.push(val);
    }

    /**
     * implementation of addition operator
     */
    private class addition implements Operator { 
        
        /**
         * Specifies the number of arugments ther operator requires
         * @return int - number of arguments
         */
        public int numArgs() {
            return 2;
        }    
        
        /**
         * add the given double values in the List
         * @param List - ArrayList of double values
         * @return double - result of evaluation
         */
        public double eval(List<Double> args) {
            double val = args.get(0) + args.get(1);
            return val;
        }
    }
    
    /**
     * implementation of subtraction operator
     */
     private class subtraction implements Operator {

        /**
         * Specifies the number of arugments ther operator requires
         * @return int - number of arguments
         */ 
        public int numArgs() {
            return 2;
        }    

        /**
         * subtract the given double values in the List
         * @param List - ArrayList of double values
         * @return double - result of evaluation
         */
         public double eval(List<Double> args) {
            double val = args.get(1) - args.get(0); 
            return val;
        }
    }

    /**
     * implementation of multiplication operator
     */
    private class multiplication implements Operator {

        /**
         * Specifies the number of arugments ther operator requires
         * @return int - number of arguments
         */  
        public int numArgs() {
            return 2;
        }    

        /**
         * multiply the given double values in the List
         * @param List - ArrayList of double values
         * @return double - result of evaluation
         */
         public double eval(List<Double> args) {
            double val = args.get(0) * args.get(1);
            return val; 
        }
    }

    /**
     * implementation of division operator
     */
     private class division implements Operator {

        /** 
         * Specifies the number of arugments ther operator requires
         * @return int - number of arguments
         */  
        public int numArgs() {
            return 2;
        }
        
        /**
         * divide the given double values in the List
         * @param List - ArrayList of double values
         * @return double - result of evaluation
         */
         public double eval(List<Double> args) {
            double val = args.get(1) / args.get(0);
            return val;
        }
    }


    /**
     * implementation of print operator
     */
     private class print implements Operator {

        /**
         * Specifies the number of arugments ther operator requires
         * @return int - number of arguments
         */ 
        public int numArgs() {
            return 1;
        }    

        /**
         * print the given double values in the List
         * @param List - ArrayList of double values
         * @return double - result of evaluation
         */
         public double eval(List<Double> args) {
            System.out.println(args.get(0));
            return args.get(0);
        }
    }     
}
