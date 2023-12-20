import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Exectuable class that compiles the GUI, takes user keyboard input, and
 * updates the GUI according to the logic laid out in the game manager.
 * @author Shrey Poshiya
 */
public class Nibbles {

    // init game manager and snake
    private GameManager manager;
    private GameManager.Snake snake;
    
    // size of squares to be painted on the game board GUI
    private int blockSize;
    // game board dimensions
    private int rowLength;
    private int colLength;
     
    private String direction = "N"; // keeps track of current key input
    private String currentMove = "N"; // records current direction of snake
   
    // booleans that keep track of the game state
    private boolean paused = true;
    private boolean gameOver = false;
    
    /** 
     * Nibbles contructor, intializes the game manager, the snake, places
     * intial piece of food
     *
     * @param fileName - name of config file
     */
    public Nibbles(String fileName) throws IOException {

        blockSize = 20;        

        // new Random object
        Random rand = new Random();   

        // initialize the game manager, snake, and food
        manager = new GameManager(fileName);
        int[] cell = manager.randomCell(); // random cell to populate snake
        snake = manager.new Snake(4, cell[0], cell[1]);
        manager.placeFood();

        // specifiy row and col length of the board
        rowLength = manager.returnRow();
        colLength = manager.returnCol(); 
         
    }

    /**
     * nested helper class that defines and paints the game board panel,
     * extends JPanel
     */
    public class snakePanel extends JPanel {

        /**
         * defines the panel size: number of blocks per axis times the pixel
         * width of each block (blockSize)
         */
        public Dimension getPreferredSize() {
            return new Dimension(colLength*blockSize, rowLength*blockSize);
        }
        
        /**
         * iterates over wall indicies (contained within an array list in 
         * the game manager) and paints each block individually
         * @param g - graphics object
         */                  
        public void paintWalls(Graphics g) {
            ArrayList<int[]> wallIndicies = manager.getWallIndex();
            
            this.paintSnake(g);
            for (int[] wall: wallIndicies) {
                g.fillRect(wall[1]*blockSize, wall[0]*blockSize,
                           blockSize, blockSize);
            }
        }
    
        /**
         * iterates over snake indicies (contained within an array list in 
         * the game manager) and paints each block individually
         * @param g - graphics object
         */         
        public void paintSnake(Graphics g) {
            ArrayList<int[]> snakeIndicies = manager.getSnakeIndex();
            
            for (int[] snake: snakeIndicies) {
                g.fillRect(snake[1]*blockSize, snake[0]*blockSize,
                            blockSize, blockSize);        
            }
        }

        /**
         * iterates over food indicies (contained within an array list in 
         * the game manager) and paints each block individually
         * @param g - graphics object
         */          
        public void paintFood(Graphics g) {
            ArrayList<int[]> foodIndicies = manager.getFoodIndex();
            
            for (int[] food: foodIndicies) {
                g.fillRect(food[1]*blockSize, food[0]*blockSize, 
                           blockSize, blockSize);
            } 
        }
        
        /**
         * overrides paintComponent method within the JPanel implementation.
         * Defines what to paint for the entirety of the game panel. If the 
         * is not over, paint all walls, snake, and food. If the game is over
         * simply print a "game over" dialouge in the panel
         * @param g - graphics object
         */    
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (gameOver) {
                g.drawString("GAME OVER", 100, 100);
            } else {
                g.setColor(new Color(200,100,100));
                this.paintWalls(g);
                g.setColor(new Color(100,200,100));
                this.paintSnake(g);
                g.setColor(new Color(100,100,200)); 
                this.paintFood(g);
            }
        }
    }

    /** 
     * Main Method: when this file is run, a new instance of nibbles is created
     * providing the backbone for all the snake game logic. Next the GUI is 
     * instantiated. Within the frame, exists a game board panel which is 
     * an instance of the snakePanel class in nibbles. The game board is given
     * a keyboard listener to take in key inputs to move the snake. A button
     * to start/pause the game is created. A label describing the score (# of
     * food pellets eaten) is shown.
     *
     * @param args - string array of command line arguments, only first arg
     *               is used in this program, to specify the name of the level
     *               config file
     */
    public static void main(String[] args) throws IOException {       

        // if no argument is given, use the default simple level configuration
        String file; 
        if (args.length > 0) {
            file = args[0];
        } else { 
            file = "maze-simple.txt";
        }
        
        // new instance of nibbles
        Nibbles nibbles = new Nibbles(file);
        
        // new JFrame
        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
    
        // game board panel with a key listener
        JPanel gameBoard = nibbles.new snakePanel();
        gameBoard.addKeyListener(new KeyListener() {
            
            // throwaway implementions of abstract class methods
            @Override
            public void keyReleased(KeyEvent ev) { }
            @Override 
            public void keyTyped(KeyEvent ev) { } 
            
            /**
             * defines keyboard input, and changes direction of the snake
             * depending on the keyboard input. This implementation does allow
             * the user to input a direction that is opposite to the snake's
             * current direction of movement (so as to not allow the snake to
             * bump into itself on the same row or col).
             * 
             * @param ev - some KeyEvent when this panel is in focus
             */
            @Override
            public void keyPressed(KeyEvent ev) {
                if (nibbles.currentMove.equals("N")) {   
                    switch (ev.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            nibbles.direction = "N";
                            break;
                        case KeyEvent.VK_RIGHT:
                            nibbles.direction = "E";
                            break;
                        case KeyEvent.VK_LEFT:
                            nibbles.direction = "W";
                            break;
                    }
                } else if (nibbles.currentMove.equals("S")) {
                    switch (ev.getKeyCode()) {
                        case KeyEvent.VK_DOWN:
                            nibbles.direction = "S";
                            break;
                        case KeyEvent.VK_RIGHT:
                            nibbles.direction = "E";
                            break;
                        case KeyEvent.VK_LEFT:
                            nibbles.direction = "W";
                            break;
                    } 
                } else if (nibbles.currentMove.equals("E")) {
                    switch (ev.getKeyCode()) {
                        case KeyEvent.VK_DOWN:
                            nibbles.direction = "S";
                            break;
                        case KeyEvent.VK_UP:
                            nibbles.direction = "N";
                            break;
                        case KeyEvent.VK_RIGHT:
                            nibbles.direction = "E";
                            break;
                    }
                } else if (nibbles.currentMove.equals("W")) {
                    switch (ev.getKeyCode()) {
                        case KeyEvent.VK_DOWN:
                            nibbles.direction = "S";
                            break;
                        case KeyEvent.VK_LEFT:
                            nibbles.direction = "W";
                            break;
                        case KeyEvent.VK_UP:
                            nibbles.direction = "N";
                            break;
                    }
                } 
            }                
        });
        gameBoard.setFocusable(true);

        // start/pause botton (default position is paused)
        JButton startPause = new JButton("START");
        startPause.setFocusable(true);
        startPause.addActionListener(new ActionListener() {
            
            /**
             * when the button is pressed
             *   If the game is currently paused: unpause the game and put
             *    the focus on the game board.
             *   If the game is currently running: set the pause state to
             *    true set the focus on the button (as to not allow the
             *    game board recognize any keyboard input.
             */ 
            @Override
            public void actionPerformed(ActionEvent ev) {
                if (nibbles.paused) {
                    nibbles.paused = false;
                    gameBoard.requestFocus();
                    startPause.setText("PAUSE");
                    frame.pack(); // repack frame since button size changes
                } else {
                    nibbles.paused = true;
                    startPause.requestFocus();
                    startPause.setText("START");
                    frame.pack();
                }
            }
        });
    
        // label with score
        JLabel scoreLabel = new JLabel("Score: 0");

        // panel containing label and button
        JPanel statPanel = new JPanel();
        statPanel.setLayout(new BoxLayout(statPanel, BoxLayout.Y_AXIS));
        statPanel.add(scoreLabel);
        statPanel.add(startPause);

        // compile main panel
        mainPanel.add(gameBoard);
        mainPanel.add(statPanel);
       
        // show the main frame
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setVisible(true);

        // when the GUI first appears put the focus on the start/pause button
        startPause.requestFocus();

        // infer update loop speed based on the size of the map
        // time (ms) = 1000 / ln(row*col)
        int delay = 1000 / (int) java.lang.Math.log(nibbles.rowLength * 
                            nibbles.colLength);
        System.out.println(delay);

        // MAIN UPDATE LOGIC //
        java.util.Timer timer = new java.util.Timer();
        timer.schedule( new TimerTask() {
            /**
             * run method to be exectued at every timer step. only when the
             * game is unpaused, is the snake position updated, the game
             * board is redrawn, and the score label is updated. At every run
             * call, the game state is checked. If the game is over, simply
             * display a game over screen and end the timer.
             */
            @Override
            public void run() {
                if (nibbles.paused) { 
                    // if game is paused do nothing (timer is still running)
                }
                else {
                    // update snake position
                    System.out.println(nibbles.direction);
                    nibbles.snake.updatePosition(nibbles.direction);
                    nibbles.currentMove = nibbles.direction;
                    gameBoard.repaint(); 
                    scoreLabel.setText("Score: " + nibbles.manager.getScore());
                    frame.pack();
                }
            
                if (nibbles.manager.getGameOver()) {
                    nibbles.gameOver = true;
                    System.out.println("Game Over");
                    timer.cancel();
                    gameBoard.repaint();
                } 
            }
        }, 0, delay);   
    }
} 
