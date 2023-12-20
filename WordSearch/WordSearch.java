import java.io.*;
import java.util.*;

/**
 * WordSearch class handles the logic of traversing over the board and
 * finding words from the given set in each of the given directions. At the
 * the class sorts the output into the given order then prints it out.
 *
 * @author Shrey Poshiya
 */
public class WordSearch {

    private Set<String> output = new TreeSet<String>(new OutputComparator());
    private Board puzzle; // represents the board
    private WordSet set; // set of words to find on the board

   
    /**
     * First specifies the x and y component of the directional vector (in
     * the context of the board). Then traverses in the given direction from
     * the specified cell (row, col) and searches for the combined string (from
     * the cell to the current position) in the set of possible answers.
     * NOTE: the function will stop traversing in a given direction once the
     * string formed has exceeded the length of the largest string in the set
     * of answers.
     *
     * @param row - row index of specified board cell
     * @param col - col index of specified board cell
     * @param maxLength - length of longest string in the set  
     * @param direction - the cardinal direction the function should traverse
     */
    public void traverse(int row, int col, int maxLength, String direction) {

        int xdir = 0;
        int ydir = 0;
        // specify x and y components of the direciton vector
        if (direction.equals("E")) {
            xdir = 1;
            ydir = 0;
        } else if (direction.equals("SE")) {
            xdir = 1;
            ydir = 1;
        } else if (direction.equals("S")) {
            xdir = 0;
            ydir = 1;
        } else if (direction.equals("SW")) {
            xdir = -1;
            ydir = 1;
        } else if (direction.equals("W")) {
            xdir = -1;
            ydir = 0; 
        } else if (direction.equals("NW")) {
            xdir = -1;
            ydir = -1;
        } else if (direction.equals("N")) {
            xdir = 0;
            ydir = -1; 
        } else if (direction.equals("NE")) {
            xdir = 1;
            ydir = -1; 
        }

        String str = ""; // empty string       
        
        // starting from given cell, create a string containing all elements
        // in the specified direction. As strings are added to the master 
        // string, search the set of answers to see if the current string
        // is a plausible string. If so, add it to the output set. 
        int i = row;
        int j = col;
        while ( (((i>=0) && (j>=0)) && 
                ((i<=(puzzle.getRowCount())-1) && 
                 (j<=(puzzle.getColCount())-1))) &&
                (str.length() <= maxLength)) {
        
            str = str + puzzle.getCell(i,j);

            if (set.contains(str)) {
                output.add(str + " " + row + " " + col + " " + direction);
            }

            i = i + ydir;
            j = j + xdir;
        } 
    }        
    
    /**
     * Use traverse() function to search over the board. Print out the output.
     *
     * @param args - command line arguments
     */
    public static void main(String[] args) throws IOException {
        // init a new game
        WordSearch game = new WordSearch();
                
        // define set of words to search for and the board
        game.set = new WordSet(args[0]);
        game.puzzle = new Board(args[1]);

        int max = game.set.largestString(); // bound of search space         

        int rowBound = game.puzzle.getRowCount(); // row size of board
        int colBound = game.puzzle.getColCount(); // col size of board

        String dir = args[2]; // direction specification
     
        // go to each cell and the puzzle and traverse in the specified
        // direction from there 
        for (int i=0; i<rowBound; i++) {
            for (int j=0; j<colBound; j++) {
                if (dir.equals("ALL")) {
                    game.traverse(i,j,max,"E");
                    game.traverse(i,j,max,"SE");
                    game.traverse(i,j,max,"S");
                    game.traverse(i,j,max,"SW");
                    game.traverse(i,j,max,"W");
                    game.traverse(i,j,max,"NW");  
                    game.traverse(i,j,max,"N");
                    game.traverse(i,j,max,"NE");      
                } else if (dir.equals("HORIZVERT")) {
                    game.traverse(i,j,max,"N");
                    game.traverse(i,j,max,"E");
                    game.traverse(i,j,max,"S");
                    game.traverse(i,j,max,"W"); 
                } else if (dir.equals("DIAGONAL")) {
                    game.traverse(i,j,max,"NE");
                    game.traverse(i,j,max,"SE");
                    game.traverse(i,j,max,"SW");
                    game.traverse(i,j,max,"NW");
                } else if (dir.equals("FORWARD")) {
                    game.traverse(i,j,max,"NE");
                    game.traverse(i,j,max,"E");
                    game.traverse(i,j,max,"SE");
                    game.traverse(i,j,max,"S");
                } else {
                    System.out.println("Direction was not valid");
                }
            }
        } 

        // print out results
        Iterator<String> it = game.output.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        } 
    
    }  
}     
