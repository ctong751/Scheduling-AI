package assignment7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Scheduler {
    public static void main(String args[]) throws FileNotFoundException {

        // If the arguments are wrong, print and exit.
        if (args.length != 1) {
            System.out.println("Invalid arguments: java -jar Scheduler.jar <InputFile>");
            System.exit(0);
        }

        // Get the input file name and read in the inputs to a directed graph.
        String inputFile = args[0];
        readFile(inputFile);
    }

    /**
     * This method reads a file that describes a basic partially-ordered graph structure of
     * tasks, successors, and durations represented as integers. This is then turned into a
     * directed graph with minimal edges.
     * @param inputFile the file being read
     * @throws FileNotFoundException
     */
    private static void readFile(String inputFile) throws FileNotFoundException {
        // TODO: Implement
        // Creates a new file to read from and scanner to read with.
        File input = new File(inputFile);
        Scanner scanner = new Scanner(input);

        scanner.close();
    }
}