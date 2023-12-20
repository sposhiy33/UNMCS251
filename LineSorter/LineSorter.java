import java.io.*;
import java.util.*;
import java.nio.file.*;
   
/**
 * Simple class to sort a list of lines from an input file based on 
 * length (shortest to largest)
 *
 * @author Shrey Poshiya
 */ 
public class LineSorter {

    /**
     * Takes lines from an input file and sorts them by length (if lines are
     * the same length, lines are sorted by lexicological order) and writes
     * to an output file in this order
     * 
     * Pseudo Code: 
     * 1. Read command line arguments as files
     * 2. Create line input stream from the input file
     * 3. Read lines from input stream to a String list
     * 4. Sort list
     * 5. Output ordered lines to the specified output file
     *
     * @param args - string of command line arguments
     */
    public static void main(String[] args) { 
        
        // empty list that all strings from input file will be added to 
        LinkedList<String> stringList = new LinkedList<String>();
        Path inputFile = Paths.get(args[0]);
       
        // read input file and each line to a list of Strings  
        try (InputStream in = Files.newInputStream(inputFile);
             BufferedReader reader = new BufferedReader(
                                        new InputStreamReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
               
                // if the line is not blank, check to see if the first
                // character of the string is a "%"
                // if not, then add it to the list 
                if (line.length() > 0) {
                    char firstChar = line.charAt(0);
                    String firstStringChar = Character.toString(firstChar);
                    
                    if ((firstStringChar.equals("%"))) { }
                    else { stringList.add(line); }
                }
                // if the line is blank, add it anyway
                else {
                    stringList.add(line);
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }


        // sort the list
        // basic gist: iterate over each element of the list. If the preceding
        // string is of greater length, switch their positions. Continue
        // decending the string until there are no preceding strings that are
        // higher in value. If the preceding string is of the same length,
        // put the string with a higher lexicological value in front
        for (int i=1; i<stringList.size(); i++) {
            
            String compare = stringList.get(i);
            
            int j = i-1;
            String compareWith = stringList.get(j);
            
            while ( (j >= 0) && (compare.length() <= compareWith.length())) {

                if (compare.length() == compareWith.length()) {
                    if (compare.compareTo(compareWith) > 0) {
                        stringList.set(j+1, compareWith);
                        stringList.set(j, compare);
                    }
                } else {
                    // switch positions of the two strings
                    stringList.set(j+1, compareWith);
                    stringList.set(j, compare);
                }

                System.out.println(Integer.toString(i) + stringList + "\n");
           
                if (j > 0) { 
                    compare = stringList.get(j);
                    compareWith = stringList.get(j-1);
                }                

                j--;
                       
            }     
        } 
        
        // specify the path of the output file        
        Path outputFile = Paths.get(args[1]);

        // write sorted lines out to the output file using a buffered output
        // stream (BufferedWriter class)
        try (OutputStream out = Files.newOutputStream(outputFile);
             Writer writer = new BufferedWriter(
                                        new OutputStreamWriter(out))) {

            for (String s: stringList) {
                writer.write(s+"\n");
            }        

        } catch (IOException ex) {
            System.err.println(ex);
        }           
    }
}

