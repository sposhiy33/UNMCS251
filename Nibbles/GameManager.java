import java.util.*;
import java.io.*;

/**
 * game manager for snake 
 * @author Shrey Poshiya
 */
public class GameManager {

    // 2d array of Tiles (only used to create string representation)
    private Tile[][] board;
    
    // list arrays containing coordinate representations of empty space,
    // walls, food, and the snake
    private ArrayList<int[]> emptyIndicies = new ArrayList<int[]>();
    private ArrayList<int[]> wallIndicies = new ArrayList<int[]>();
    private ArrayList<int[]> foodIndicies = new ArrayList<int[]>();
    private ArrayList<int[]> snake = new ArrayList<int[]>();

    // dims of board
    private int rowLength; 
    private int colLength;
    
    private int score = 0; // score: number of food eaten by snake  
    
    // game state; updated in the collision detection function
    private boolean gameOver = false; 
    private Random rand = new Random(); // a random object (pun)

    /**
     * Enum containing all possible tiles on the board.
     * only used to populate 2d board array
     */
    public enum Tile {
        EMPTY("."), WALL("X"), FOOD("F"), SNAKE("S");

        private String asString;
        
        Tile(String repr) {
            this.asString = repr;
        }
           
        /**
         * return string representaion of the enum element
         */
        @Override
        public String toString() {
            return this.asString;
        } 
    }

    /**
     * Constructor of the game manager, reads in input file to construct
     * the level. Populates both the 2d array (used to contruct the string
     * representation of the level state) and the ArrayLists of walls and
     * empty space
     * 
     * @param inputFile - file name of config file for level setup
     */
    public GameManager(String inputFile) throws IOException {

        // array list that contains the lines in the config files that
        // contain the sepcifications of each wall
        ArrayList<String> wallStrings = new ArrayList<String>();
    
        // scanner to read the input file
        try (Scanner scanner = new Scanner(
                                new BufferedReader(
                                    new FileReader(inputFile)))) {
            // get board dimensions
            String[] dim = (scanner.nextLine()).split(" ");
            colLength = Integer.parseInt(dim[0]);
            rowLength = Integer.parseInt(dim[1]);            

            // add line boundaries into the board
            while (scanner.hasNextLine()) {
                wallStrings.add(scanner.nextLine());
            }
        }

        // popualate 2d board array with empty tiles
        board = new Tile[rowLength][colLength];
        for (int row=0; row<rowLength; row++) {
            for (int col=0; col<colLength; col++) {
                board[row][col] = Tile.EMPTY; 
            }
        }       

        // populate walls on 2d array and add coordinates of the walls in an
        // ArrrayList
        for (String str: wallStrings) {
            String[] wall = str.split(" ");
            for (int col=Integer.parseInt(wall[0]); 
                     col<=Integer.parseInt(wall[1]); col++) {
                for (int row=Integer.parseInt(wall[2]); 
                         row<=Integer.parseInt(wall[3]); row++) {
                    board[row][col] = Tile.WALL;
                    wallIndicies.add(new int[]{row, col});
                } 
            }
        }
        
        // collect indicies of every empty cell (every thing that isn't a wall)
        // and add them to an ArrayList
        for (int row = 0; row < rowLength; row++) {
            for (int col = 0; col < colLength; col++) {
                int[] cell = new int[]{row, col};
                boolean overlap = false;
                for (int[] wall: wallIndicies) {
                    if (Arrays.equals(wall, cell)) {
                        overlap = true;
                    }
                } 
                if (overlap) { }
                else {
                    emptyIndicies.add(cell);
                } 
            }
        }           
    }

    /**
     * A function to redefine the Random object with an external Random object
     * this Random object is used for placing food at random locations
     * @ param r - Random object
     */
    public void setRandom(Random r) {
        rand = r;
    }        

    /**
     * returns a string represention of the level state, constructed from
     * the 2d Array of the board
     */
    @Override
    public String toString() {
        String boardString = "";
        for (int row=0; row<rowLength; row++) {
            for(int col=0; col<colLength; col++) {
                boardString = boardString + board[row][col].toString(); 
            }
            boardString = boardString + "\n";
        }
        return boardString; 
    }

    /**
     * function that places food at a random location on the board by selecting 
     * a random coordinate from the list of empty coordinatees.
     * Adds food coordinates to an ArrayList
     */
    public void placeFood() {
        // random index from list of empty indicies
        int randIndex = rand.nextInt(emptyIndicies.size());
        int[] foodPos = emptyIndicies.get(randIndex);
        foodIndicies.add(foodPos);
        board[foodPos[0]][foodPos[1]] = Tile.FOOD;  
    }

    /**
     * function that returns a random cell on the game board   
     */     
    public int[] randomCell() {
        // uses random number generator to get a random number within the range
        // of the list of empty indicies
        int randIndex = rand.nextInt(emptyIndicies.size());
        int[] cell = emptyIndicies.get(randIndex);
        return cell;
    }    


    /**
     * public accessor for array list of wall indicies
     */
    public ArrayList<int[]> getWallIndex() {
        return wallIndicies;
    }

    /**
     * public accessor for array list of snake indicies
     */ 
    public ArrayList<int[]> getSnakeIndex() {
        return snake;
    } 

    /**
     * public accessor for array list of food indicies
     */ 
    public ArrayList<int[]> getFoodIndex() {
        return foodIndicies;
    }

    /**
     * public accessor for row length of the board
     */ 
    public int returnRow() {
        return rowLength;
    } 

    /**
     * public accessor for the col length of the board
     */
    public int returnCol() {
        return colLength;
    }
    
    /**
     * public accessor for the game over boolean (internal variable to keep
     * track of the game state) 
     */ 
    public boolean getGameOver() {
        return gameOver;
    }
    
    /** 
     * public accessor method to return the score of the game
     */
    public int getScore() {
        return score;
    }

    /**
     * Snake nested class that contains all the logic to control the snake
     */
    public class Snake {
        private int snakeLen; // length of the snake
        private int[] headPos; // coordinates of the head of the snake
            
        // direction vectors
        private Map<String, int[]> dirVec = new HashMap<String, int[]>();         

        /**
         * Snake constructor that defines initial parmaters of the snake. Also
         * populates the direction vectors associated with each string
         * representation of the cardinal directions
         *
         * @param snakeSize - intial length of the snake
         * @param rowPos - initial y position of the snake
         * @param colPos - initial x position of the snake
         */
        public Snake(int snakeSize, int rowPos, int colPos) {
            this.snakeLen = snakeSize;
            this.headPos = new int[]{rowPos, colPos};
            
            // now that the snake is taking up a tile on board, remove
            // the tile from the list of empty tiles
            emptyIndicies.remove(this.headPos);            

            board[rowPos][colPos] = Tile.SNAKE;
            snake.add(this.headPos);
            
            // populate correspoding vectors to direction
            dirVec.put("N", new int[]{0,-1});
            dirVec.put("E", new int[]{1,0});
            dirVec.put("S", new int[]{0,1});
            dirVec.put("W", new int[]{-1,0});
        }

        /**
         * fucntion meant for debugging ... prints the coordinates of each part
         * of the snake
         */
        public void printSnake() {
            for (int[] vec: snake) {
                System.out.println(vec[0] + " " + vec[1]);
            }
        }

        /**
         * Checks if a given coordinate is colliding with any wall or food item
         * if the given coordinate is in the ArrayList of wall coordinates,
         * change the game to be "over". If the coordinate is in the 
         * ArrayList of food coordinates, simply increment the snake size
         * and place a new food item
         *
         * @param position - coordinate to check collision with
         */
        public void checkCollision(int[] position) {
            // check for wall collision
            for (int[] wallInd: wallIndicies) {
                if (Arrays.equals(position, wallInd)) {
                    gameOver = true;
                }
            }
            // check for collision the snake itself
            ArrayList<int[]> checkSnake = new ArrayList<int[]>(snake);
            // remove head of snake from the snake as that is the part of the
            // snake we are using to check for collisions
            checkSnake.remove(0);
            for (int[] vec: checkSnake) {
                if (Arrays.equals(position, vec)) {
                    gameOver = true;
                }       
            }
            
            boolean ateFood = false;
            // check for collision with food
            for (int[] foodInd: foodIndicies) {
                if (Arrays.equals(position, foodInd)) {
                    this.snakeLen = this.snakeLen + 1; 
                    ateFood = true;
                }
            }
            // if food is eaten, increment the score counter, remove the 
            // previous food block, and place a new food at a random location
            if (ateFood) {
                score++; 
                foodIndicies.remove(0); 
                placeFood(); 
            }
        }

        /**
         * update the position of the snake depending on the specified
         * direction. If the snake has not yet reached its full length,
         * simply move the head of the snake. If the snake is already at 
         * capacity, move the head of the snake and delete the tail. Append
         * the snake and empty space ArrayLists accordingly. At each step
         * check for collision
         *
         * @param direction - specify direction to move [N,E,S,W]
         */
        public void updatePosition(String direction) {

            int[] vector = dirVec.get(direction);
            int row = headPos[0] + vector[1];
            int col = headPos[1] + vector[0];

            int[] movePos = new int[]{row, col};            

            // if snake has not reached its max length, simply move it
            // forward 
            if(snake.size() < snakeLen) {
                snake.add(0, movePos);
                // try, catch clause to place a tile on the 2d array 
                // represenetation of the board, if the snake attempts to go
                // outside of the board, and ArrayIndexOutOfBounds exception
                // will be thrown; if so, the game is over.
                try {
                    board[movePos[0]][movePos[1]] = Tile.SNAKE;
                } catch (Exception ex) {
                    gameOver = true;
                }
                // as snake moves, remove tile from list of empty tiles
                emptyIndicies.remove(movePos);
            }    
            // if snake is fully developed, move ahead one step, then delete
            // its tails, retaining its size
            else if(snake.size() >= snakeLen) {
                int[] tail = snake.get( (snake.size() - 1) );
                board[tail[0]][tail[1]] = Tile.EMPTY;
                snake.remove( (snake.size() - 1) );
                // since the tail of the snake will no longer occupy the
                // a tile on the board, add the tile to the list of empty
                // indicies 
                emptyIndicies.add(tail);
                
                snake.add(0, movePos);
                try {
                    board[movePos[0]][movePos[1]] = Tile.SNAKE;
                } catch (Exception ex) {
                    gameOver = true;
                    System.out.println("Position " + "(" + movePos[0] + "," +
                                       movePos[1] + ")" + " is out of bounds");
                }       
                // remove tile from list of empty indicies as the snake moves
                emptyIndicies.remove(movePos);
            }
            checkCollision(movePos);
            headPos = movePos;
        }        
    
    }

    /**
     * simply for debugging, creates a new game, populates a snake and food,
     * moves snake
     *
     * @param args - command line args, first argument is used to specify the
     *               config file
     */
    public static void main(String[] args) throws IOException {
        GameManager board = new GameManager(args[0]);
        System.out.println(board.toString());
    
        GameManager.Snake snake = board.new Snake(4, 9, 8);
        board.placeFood();
        System.out.println(board.toString());
       
        for (int i = 0; i < 6; i++) { 
            snake.updatePosition("N");
            System.out.println(board.toString());
            System.out.println(board.gameOver);
            System.out.println(" ");
        }
    }    
}
