import java.io.*;
import java.util.*;

/**
 * defines comparator that will be used to sort the output of puzzle (in
   the form of a set)
 * @author Shrey Poshiya
 */

public class OutputComparator implements Comparator<String> {
    
    // map each direction to a number (based on priority of sorting)
    // so they can be compared numerically
    private Map<String, Integer> dirInd = new HashMap<String, Integer>();
 
    /**
     * Constructor that populates the dirInd map (see above) 
     */
    public OutputComparator() {   
        dirInd.put("E", 0);
        dirInd.put("SE", 1);
        dirInd.put("S", 2);
        dirInd.put("SW", 3); 
        dirInd.put("W", 4);
        dirInd.put("NW", 5);
        dirInd.put("N", 6);
        dirInd.put("NE", 7); 
    }
          
    /**
     * splits up the string by white space (output string, row, col, direction)
     * first compares the strings by lexicological order
     * then by row order
     * then by col order 
     * finally then by direction with the following priority:
     *  E,SE,S,SW,W,NW,N,NE
     * 
     * @param compareString - string 1  
     * @param compareWithString - string 2
     */
    @Override
    public int compare(String compareString, String compareWithString) {

        String[] comp = compareString.split(" ");
        String[] compWith = compareWithString.split(" "); 
            
        if (comp[0].compareTo(compWith[0]) < 0) {
            return comp[0].compareTo(compWith[0]);
        } else if (comp[0].compareTo(compWith[0]) == 0) {

            //compare by row
            int compareRow = Integer.parseInt(comp[1]);
            int compareWithRow = Integer.parseInt(compWith[1]);

            if (compareRow < compareWithRow) {
                return compareRow - compareWithRow;
            } else if (compareRow == compareWithRow) {

                // compare by column
                int compareCol = Integer.parseInt(comp[2]);
                int compareWithCol = Integer.parseInt(compWith[2]);
                    
                if (compareCol < compareWithCol) {
                    return compareCol - compareWithCol;
                } else if (compareCol == compareWithCol) {

                    // compare by direction
                    int compareDir = dirInd.get(comp[3]);
                    int compareWithDir = dirInd.get(compWith[3]);
                        
                    if (compareDir < compareWithDir) {
                        return compareDir - compareWithDir;
                    }
                }
            }                    
        }
        
        return 1;
    }
}    
