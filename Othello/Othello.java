import cs251.lab2.*;
    
/** 
 * A simple class that implements the game logic of "Othello" and
 * allows player to play the game through a GUI
 *
 * Run program using the following command:
 * java -cp lab2.jar;. Othello.java [computerPlayer]
 *     
 *     - computerPlayer options = {NONE, COMPUTER}
 *     - assumes 'lab2.jar' file is in current working directory
 *  
 * @author Shrey Poshiya

 */ 
public class Othello implements OthelloInterface {
    
    // init variables
    private int size = this.getSize();
        
    // game board
    private Piece[][] board = new Piece[size][size];    
    
    // info about current move
    private int turnIndex = 0;
    private Piece currentPiece;
    private Piece currentOpposingPiece;
    
    private String computerPlayer;        
    
    private int[][] vectorDirections = { {1,0},
                                 {1,1},
                                 {0,1},
                                 {-1,1},
                                 {-1,0},
                                 {-1,-1},
                                 {0,-1},
                                 {1,-1} };
    /**
     * initialize a new game, computer player (is specified in cmdline args)
     * and GUI
     *
     * @param args - String array of command line arguments
     */
    public static void main(String[] args) {
        Othello game = new Othello();
        if (args.length > 0) {
            game.initComputerPlayer(args[0]);
        }
        OthelloGUI.showGUI(game);
    }
    
    /**
     * returns grid length of the board
     * @return int of board grid side length
     */
    public int getSize() { 
        return OthelloInterface.DEFAULT_SIZE;   
    }
    
    /**
     * Set up starting board position and reset turn index
     * 
     * 1. Populate board with empty pieces
     * 2. Place starting 4 pieces
     */ 
    public void initGame() {
       
        // reset turn index
        this.turnIndex = 0;
 
        // populate board with Empty pieces
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                this.board[x][y] = Piece.EMPTY;
            }
        }    
    
        // populatae the 4 inital pieces in correct starting positions
        board[(size/2)-1][(size/2)-1] = Piece.LIGHT;
        board[(size/2)][(size/2)] = Piece.LIGHT;
        board[(size/2)-1][(size/2)] = Piece.DARK;
        board[(size/2)][(size/2)-1] = Piece.DARK;
        
    }
    
    /**
     * turn board 2d-array into string represention.
     * black pieces = "B"
     * white pieces = "W"
     * empty spaces = "."
     * Each row of the board is seperated onto a new line in the string
     *
     * @return String representing current board configuration
     */
    public String getBoardString() {
        
        String boardString = ""; 
    
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                String pieceChar = String.valueOf(this.board[x][y].toChar());
                boardString += pieceChar;
            }
            boardString += "\n";    
        }
    
        return boardString;         
    }
   
    /**
     * based on the current turn index, determines the color of current piece
     *
     * @return Piece enum constant representing the color of the current move
     */ 
    public Piece getCurrentTurn() {
    
        if (turnIndex % 2 == 0) {
            this.currentPiece = Piece.DARK;
            this.currentOpposingPiece = Piece.LIGHT;
            return Piece.DARK;
        } else {
            this.currentPiece = Piece.LIGHT;
            this.currentOpposingPiece = Piece.DARK;
            return Piece.LIGHT;
        }        
    }
    
    /**
     * Checks if a given move is legal. Conditions:
     * 1. If piece already exists on a space, move is illegal
     * 2. See if a another piece of the same color is connected by piece(s) 
     *    of the opposite color in any of the 8 axes 
     *
     * @param row - row index of current move
     * @param col - col index of current move
     * @return boolean that specifies if a given move on the current board
     *         is legal
     */
    public boolean isLegal(int row, int col) {
        
        // only allow moves on empty board spaces        
        if (this.board[row][col] == Piece.EMPTY) {
            // pass
        } else {
            return false; 
        } 
        // check to see if current move lies on a valid axis
        if ( (isConnected(row, col, 1, 1)) ||
             (isConnected(row, col, 0, 1)) ||
             (isConnected(row, col, -1, 1)) ||
             (isConnected(row, col, -1, 0)) ||
             (isConnected(row, col, -1, -1)) ||
             (isConnected(row, col, 0, -1)) ||
             (isConnected(row, col, 1, -1)) ||
             (isConnected(row, col, 1, 0)) ) {
            return true;
        } else { return false; }
    }
    
    // iterate over board to check if a legal move exists
    private boolean legalExists() {
        for (int x=0; x<size; x++) {
            for (int y=0; y<size; y++) {
                if (isLegal(x,y)) {
                    return true;
                }   
            }
        }
        return false;
    }
    
    /** 
     * This method directly handles placing pieces on the board, and deciding
     * on the current game state depending on the board configuration.
     *
     * 1. When mouse is clicked on grid square, if the move is legal, place
     *    the piece.
     * 2. Flip any pieces that are bounded by two of the current piece on the
     *    same axis
     * 3. Increment turn to next player, if no legal moves exist for this
     *    end the game and majority wins
     * 4. If legal moves do exist
     *     * if: playing against computer, play computer move, then increment
     *       the human turn (if legal moves exist for human player)
     *     * else: handover to the human for their next turn
     *
     * @param row - row index of current move
     * @param col - column index of current move
     * @return Result enum constant representing the current game state.
     */
    public Result handleClickAt(int row, int col) {
            
        // if selected move is legal:
        // 1. place piece on board
        // 2. flip pieces on corresponding axis
        if (isLegal(row,col)) {

            this.board[row][col] = this.currentPiece;
            
            for (int x=0; x<8; x++) {
                if (isConnected(row, col, vectorDirections[x][0],
                                          vectorDirections[x][1])) {
                    flipPieces(row,col, vectorDirections[x][0],
                                        vectorDirections[x][1]);
                }
            }
            
            // incerement turn and check to see if any legal moves exist
            this.turnIndex++;
            this.getCurrentTurn();

            // if legal moves exist for the next turn, handover to the human
            // or computer player 
            if (legalExists()) {
        
                // if computer player exists, play computer move
        
                if (this.computerPlayer == "COMPUTER") {
                
                    this.simplePlayer();
            
                    // increment to human turn and check for any legal moves
                    // if not, end the game and majority wins
                    this.turnIndex++;
                    this.getCurrentTurn();
                    if (legalExists()) {
                        return Result.GAME_NOT_OVER;
                    }
            
                } else {
                    // handover to other human player
                    return Result.GAME_NOT_OVER;
                }
            }         
        } 
      
        if (legalExists()) {
            return Result.GAME_NOT_OVER;
        }   
 
        // if no legal moves remain, this loop will be exectued
        // 
        // keep count of number of pieces on board to make final
        // game state verdict
        int dark = 0;
        int light = 0;

        for (int x=0; x<size; x++) {
            for (int y=0; y<size; y++) {
                if (this.board[x][y] == Piece.LIGHT) { light=light+1; }
                else if (this.board[x][y] == Piece.DARK) { dark=dark+1; } 
            }
        }

        if (dark > light) {
            return Result.DARK_WINS;
        } else if (light > dark) {
            return Result.LIGHT_WINS;
        } else if (dark == light) {
            return Result.DRAW;
        }
        
        return Result.GAME_NOT_OVER;
    }

    // will flip all pieces on a given axis onto the opposite color as long as
    // they are bounded by pieces of that color
    private void flipPieces(int row, int col, int xdir, int ydir) {        
        
        row = row+ydir;
        col = col+xdir;
    
        while ( ((row>=0) && (row<size)) && ((col>=0) && (col<size)) ) {
            if (this.board[row][col] == this.currentOpposingPiece) {
                this.board[row][col] = this.currentPiece;

                row = row+ydir; 
                col = col+xdir;
            } else {
                break;
            }
        }
    } 

    /**
     * will check in the given direction: [xdir, ydir] from the current 
     * grid square to see if there is another piece of the same color 
     * bounding piece(s) of the opposite color. If so, return true.
     *
     * @param row - row index of current move
     * @param col - col index of current move
     * @param xdir - direction in which to iterate over row index
     * @param ydir - direction in which to iterate over col index
     * @return boolean that returns true if a current piece bounds 
     *         opposite piece(s) with another piece of the same color
     *         on a given axis
     */ 
    private boolean isConnected(int row, int col, int xdir, int ydir) {
       
        row = row+ydir;
        col = col+xdir; 
            
        if ( (((row>=0) && (row<size)) && ((col>=0) && (col<size))) &&
             (this.board[row][col] == this.currentOpposingPiece)) {
            
            row = row+ydir;
            col = col+xdir;
           
            while (((row>=0) && (row<size)) && ((col>=0) && (col<size))) {
                if (this.board[row][col] == this.currentOpposingPiece) {
                    row = row+ydir;
                    col = col+xdir;
                } else if (this.board[row][col] == this.currentPiece) {
                    return true;
                } else if (this.board[row][col] == Piece.EMPTY) {  
                    return false;  
                }
            } 
        } else {
            return false;
        }
        return false;      
    }    
 
    /**
     * this method assigns value to the private variable "computerPlayer", 
     * specifiying the type of computer player to play (moves are handled 
     * in the handleClickAt method)
     */
    public void initComputerPlayer(String opponent) {
        System.out.println("initializing computer player"); 
        if (opponent.equals("COMPUTER")) {
                this.computerPlayer = "COMPUTER";
        } else {
            this.computerPlayer = "NONE";
        }
    }

    // simple computer player that plays the first legal move it finds
    // if move is legal, place the peice and flip are corresponding pieces
    private void simplePlayer() {

        rowloop:
        for (int x=0; x<size; x++) {
            colloop:
            for (int y=0; y<size; y++) {    
                if (isLegal(x,y)) {
                   
                    System.out.println(x + " " + y); 
                    // place piece
                    this.board[x][y] = this.currentPiece;
                    System.out.println("Placing Piece");

                    // flip pieces on correct axes
                    for (int ind=0; ind<8; ind++) {
                        if (isConnected(x, y, vectorDirections[ind][0],
                                         vectorDirections[ind][1])) {
                            flipPieces(x, y, vectorDirections[ind][0],
                                          vectorDirections[ind][1]);
                        }
                    }
                    // once a piece has been placed, stop iterating completely
                    // by breaking the outerloop
                    break rowloop;
                 } 
            }
        }
    }
}

