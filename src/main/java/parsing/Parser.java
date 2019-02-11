package parsing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class Parser {
    private static String csvFile = "/Users/mokro/Documents/git/IssueCategorizer/data/atom-data-titles.csv";
    private static String arffFile = "/Users/mokro/Documents/git/IssueCategorizer/data/atom-data-titles.arff";
    private static String line = "";
    private static String csvSplitBy = ",";

    public static void parse() {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] lineArray = line.split(csvSplitBy);
                System.out.println(lineArray[0] + " -----> " + lineArray[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
