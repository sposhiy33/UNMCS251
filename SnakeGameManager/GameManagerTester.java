import java.util.*;
import java.io.*;

/**
 * Simple class to test the functionality of snake game, logic of which is
 * defined in GameManager.java
 * @author Shrey Poshiya
 */
public class GameManagerTester {

    // board and snake of each game manager
    private GameManager board1;
    private GameManager.Snake snake1;
    private GameManager board2;
    private GameManager.Snake snake2;

    /**
     * Game Manager constructor class that:
     *  1. defines a random object and sets a seed 
     *  2. for each game manager:
     *      - creates a new instance of each game manager and specifies
     *        config file
     *      - populates snake and food
     *      - and prints initial game board
     */
    public GameManagerTester() throws IOException {

        // set random seed
        Random rand = new Random();
        rand.setSeed(98);

        // init game environment 1
        board1 = new GameManager("maze-simple.txt");
        board1.setRandom(rand);
        snake1 = board1.new Snake(6, 9, 8);
        board1.placeFood();
        System.out.println("GAME MANAGER 1");
        System.out.println(board1.toString());

        // init game environment 2
        board2 = new GameManager("maze-cross.txt");
        board2.setRandom(rand);
        snake2 = board2.new Snake(6, 15, 15);
        board2.placeFood();
        System.out.println("GAME MANAGER 2");
        System.out.println(board2.toString());

    }     

    /**
     * method to test the first game manager. moves snake in the direction
     * of food and eats the food ... grows in size
     */
    public void managerOneTestPartOne() {

        System.out.println(" \n \n TESTING MANAGER 1");
        System.out.println("\nmoving snake to the right");
        for (int i=0; i<8; i++) {
            snake1.updatePosition("E");
            System.out.println(board1.toString());
            board1.printState();
        }            
    
        System.out.println("\nmoving snake up");
        for (int i=0; i<5; i++) {
            snake1.updatePosition("N");
            System.out.println(board1.toString());
            board1.printState();
        }
    
        System.out.println("\neating food");
        snake1.updatePosition("N");
        System.out.println(board1.toString());
        board1.printState();

        System.out.println("\nmoving snake left");
        for (int i=0; i<3; i++) {    
            snake1.updatePosition("W");
            System.out.println(board1.toString());
            board1.printState();
        }

    }

    /** 
     * method to test the second game manager. Moves the snakes so that it 
     * first eats food and then intentionally collides with the wall
     */
    public void managerTwoTest() {

        System.out.println("\n\nTESTING MANAGER 2");
        System.out.println("\nmoving snake right");
        for (int i=0; i<2; i++) {
            snake2.updatePosition("E");
            System.out.println(board2.toString());
            board2.printState();
        }

        System.out.println("\nmoving snake up");
        for (int i=0; i<2; i++) {
            snake2.updatePosition("N");
            System.out.println(board2.toString());
            board2.printState();
        }

        System.out.println("\neating food");
        snake2.updatePosition("N");
        System.out.println(board2.toString());
        board2.printState();

        System.out.println("\nmoving snake left");
        for (int i=0; i<6; i++) {
            snake2.updatePosition("W");
            System.out.println(board2.toString());
            board2.printState();
        }

        System.out.println("\ncolliding with wall");
        snake2.updatePosition("W");
        System.out.println(board2.toString());
        board2.printState();
    }

    /**
     * method to resume testing the first game manager. Makes the snake
     * collide with itself
     */
    public void managerOneTestPartTwo() {
        
        System.out.println("\n\nRESUME MANAGER 1");
        
        System.out.println("\nmove down, then right");
        snake1.updatePosition("S");
        System.out.println(board1.toString());
        board1.printState();
        snake1.updatePosition("E");
        System.out.println(board1.toString());
        board1.printState();
        snake1.updatePosition("E");
        System.out.println(board1.toString());
        board1.printState();

        System.out.println("\nmove up to collide into snake");
        snake1.updatePosition("N");
        System.out.println(board1.toString());
        board1.printState();

    }

    /**
     * Instantiates the game manager tester and tests each game manager in the
     * following order:
     *      1. Game Manager 1
     *      2. Game Manager 2
     *      3. Game Manager 1
     * @param args - command line arguments, not used
     */
    public static void main(String[] args) throws IOException {
        GameManagerTester tester = new GameManagerTester();
        tester.managerOneTestPartOne();
        tester.managerTwoTest();
        tester.managerOneTestPartTwo();
    }
}

