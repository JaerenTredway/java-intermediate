import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Jaeren William Tredway
 * This class builds an object that will read a .txt file and store the data as
 * individual words in an ArrayList. It will then pass that data to the NGram
 * class to create a HashMap of NGrams. It then uses the TransitionRule class
 * to generate random text from the NGrams, and finally prints out the random
 * text.
 */
public class MarkovTextGenerator {

    //MEMBER VARIABLES:


    //CONSTRUCTOR:


    //METHODS:

    //this method returns an ArrayList with each word extracted from the .txt
    //file passed into it:
    private static ArrayList<String> readTextFile (String uri) throws Exception {
        //pick up and print out the file names (that were input from the command
        //line):
//        if (uri.length < 1) {
//            System.out.println("\n.txt filename(s) required as command line " +
//                    "argument(s)\n");
//        }
        String filename = uri;
        //System.out.println(uri.length + " command line arguments:");
//        for (String input : uri) {
//            System.out.println(" " + input);
//        }

        //create 3 objects: a File, a FileReader, and a BufferedReader:
        //first declare them outside of try block:
        File file;
        FileReader fileReader;
        BufferedReader bufferedReader;
        //then assign values inside try/catch block:
        try {
            file = new File(filename);
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
        } catch (Exception ex1) {
            System.out.println("Unable to open file.");
            ex1.printStackTrace();
            throw new Exception("Example exception message goes here.");
            //System.exit(1); unreachable statement
        }

        //display the file you are extracting data from:
        System.out.println("Text extracted from " + filename + ":");

        //set up storage variables for line, lines, and words:
        int count = 0;
        String line;
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<String> words = new ArrayList<>();

        //set 'line' equal to the next line of text and report:
        try {
            while ( (line = bufferedReader.readLine()) != null) {
                // the line of text you are currently reading:
                System.out.println(count + ": " + line);
                count++;
                lines.add(line);
                for (String word : line.split("[, :.;]")) {
                    //regex notes:
                    // * operator: match zero or more of preceding regex
                    // + operator: match 1 or more of preceding regex
                    // \ operator: escape character for next char
                    if (word.length() > 0) {
                        words.add(word.toLowerCase());
                    }
                }
            }
        } catch (IOException ex2) {
            System.out.println("One of the readLines has failed.");
            ex2.printStackTrace();
        }

        //report number of lines and number of words harvested from .txt file:
        System.out.println();
        System.out.println("line count: " + lines.size());
        System.out.println("word count: " + words.size());

        //print 20 of the individual words:
        for (int i = 0; i < 20; i++) {
            System.out.println("word " + i + ": " + words.get(i));
        }

        return words;
    }//END readTextFile()..........................................

    //this method creates an ArrayList of NGrams. Each NGram is a String with
    // "n" words in it:
    private static ArrayList<String> makeNGrams (int n,
                                                ArrayList<String> words) {
        ArrayList<String> NGrams = new ArrayList<>();

        for (int i = 0; i < words.size() - n; i++) {
            String temp = "";
            for (int j = 0; j < n; j++) {
                temp += words.get(i+j);
                temp += " ";
            }
            NGrams.add(temp);
        }

        return NGrams;
    }

    //main method collects the command line arguments,
    //which are an int "n" that is the NGram size, and a .txt file URI:
    public static void main (String[] args) throws Exception{
        int n = Integer.parseInt(args[0]);
        ArrayList<String> words = readTextFile(args[1]);
        ArrayList<String> nGrams = makeNGrams(n, words);
        for (int i = 0; i < 5; i++) {
            System.out.println("NGram " + i + ": " + nGrams.get(i));
        }

    }//END main()

}
