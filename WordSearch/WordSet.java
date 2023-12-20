import java.io.*; 
import java.util.*;

/**
 * loads in the input file (containing possible answers) into a HashSet
 * @author Shrey Poshiya
 */
public class WordSet {

    private Set<String> wordSet = new HashSet<String>();
    
    /**
     * Takes strings from an input file and puts them into a set using a    
     * scanner
     *
     * @param fileName - filename containing possible answers
     */
    public WordSet(String fileName) throws IOException {

        try (Scanner scanner = new Scanner(
                            new BufferedReader(
                                new FileReader(fileName)))) {

            while (scanner.hasNext()) {
                wordSet.add(scanner.next()); 
            } 
        }
    }

    /**
     * searches set of possible answers to see if the specifed string is 
     * contained in the set.
     *
     * @param obj - string
     */
    public boolean contains(String obj) {
        return wordSet.contains(obj);
    }

    /**
     * returns the length of the largest string in the set
     */    
    public int largestString() { 
       
        int size = 0;
        for (String elem: wordSet) {
            if (elem.length() > size) {
                size = elem.length();
            }
        }
        return size;

    }    
    
    /**
     * used to test this class. use first command line argument to specify the
     * input file name
     *
     * @param arg - command line arguments
     */    
    public static void main(String[] args) throws IOException {
        
        WordSet wordSet = new WordSet(args[0]);
        System.out.println(wordSet.largestString());

    } 
}

