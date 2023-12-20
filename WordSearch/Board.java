import java.io.*;
import java.util.*;

/**
 * class representing the board of the word search game
 * 
 * @author Shrey Poshiya
 */
public class Board {
    
    private int rowCount;
    private int colCount;  
    private String[][] board;

    /**
     * Contrcutor loads in the board elems from an input file using a scanner
     * @param fileName - specifies name of file that contains the board elems
     */    
    public Board(String fileName) throws IOException {
        
        List<String> contents = new ArrayList<String>();
    
        try (Scanner scanner = new Scanner(
                            new BufferedReader(
                                new FileReader(fileName)))) {

            while (scanner.hasNext()) {
                contents.add(scanner.next()); 
            }
        
        }
    
        // retrieve the number of rows and cols (from the first line of 
        // every puzzle line)
        rowCount = Integer.parseInt(contents.get(0));
        contents.remove(0);
        colCount = Integer.parseInt(contents.get(0));
        contents.remove(0);

        board = new String[rowCount][colCount];
        
        // populate the board with file contents 
        for (int i=0; i<contents.size(); i++) {
            String line = contents.get(i);
            for (int j=0; j<line.length(); j++) {
                board[i][j] = Character.toString(line.charAt(j));
            }
        }
       
    }
    
    /** 
     * retrieves element at specified board location (row, col)
     *
     * @param row - row ind
     * @param col - col ind
     */
    public String getCell(int row, int col) {
        return board[row][col]; 
    }
   
    /**
     * get number of rows on board
     */
    public int getRowCount() {
         return rowCount;
    }
    
    /**
     * get number of columns on the board
     */
    public int getColCount() {
        return colCount;
    }
   
    /**
     * used to test this class and see if file can be loaded onto the board
     * correctly. First command line arg is used to specify the input file name
     * 
     * @param args - command line arguments
     */
    public static void main(String[] args) throws IOException {
        Board b1 = new Board(args[0]);
    }

}
