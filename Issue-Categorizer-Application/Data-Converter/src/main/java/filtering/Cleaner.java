package filtering;

import entities.DataHolder;
import weka.core.Stopwords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.FINE;
import static java.util.logging.Level.INFO;

/**
 * Class for cleaning data of unusable symbols, words, etc...
 *
 * @author xmokros
 */
public final class Cleaner {
    private final static Logger LOGGER = Logger.getLogger(Cleaner.class.getName());

    /**
     * Method for cleaning list of arff entries.
     *
     * @param arffList list of arff entries in form of DataHolders
     */
    public static void cleanArffList(List<DataHolder> arffList) {
        LOGGER.log(INFO, "Initialized cleaning of arffList.");

        Vocabulary vocabTitle = new Vocabulary();
        Vocabulary vocabBody = new Vocabulary();

        //cleans data of numbers, symbols and stop-words
        for (DataHolder dataHolder : arffList) {
            //removes non alphanumeric symbols
            LOGGER.log(FINE, "Removing non alpha-numeric symbols.");
            String newTitle = dataHolder.getTitle().replaceAll("[^\\p{L}\\p{Nd}]+", " ");
            String newBody = dataHolder.getBody().replaceAll("[^\\p{L}\\p{Nd}]+", " ");

            //removes numbers only (not words containing numbers)
            LOGGER.log(FINE, "Removing whole numbers.");
            newTitle = removeWholeNumbers(newTitle);
            newBody = removeWholeNumbers(newBody);

            //clears string of stop-words
            LOGGER.log(FINE, "Clearing stopwords.");
            newTitle = removeStopWords(newTitle);
            newBody = removeStopWords(newBody);

            dataHolder.setTitle(newTitle.toLowerCase());
            dataHolder.setBody(newBody.toLowerCase());

            LOGGER.log(FINE, "Creating vocabulary for arff entry.");
            for (String word : dataHolder.getTitle().split(" ")) {
                vocabTitle.addWord(word, dataHolder.getLabel());
            }

            for (String word : dataHolder.getBody().split(" ")) {
                vocabBody.addWord(word, dataHolder.getLabel());
            }
        }

        LOGGER.log(FINE, "Removing words used in multiple labels.");
        for (DataHolder dataHolder : arffList) {
            dataHolder.setTitle(removeWordsUsedInMultipleLabels(vocabTitle, dataHolder.getTitle()));
            dataHolder.setBody(removeWordsUsedInMultipleLabels(vocabBody, dataHolder.getBody()));
        }

        LOGGER.log(INFO, "Finished cleaning of arffList.");
    }

    /**
     * Method for removing whole numbers (whole number - does not contain letter, for example " 2345 " is a whole number, " 2d34 " is not)
     *
     * @param line to be processed
     * @return line without whole numbers
     */
    private static String removeWholeNumbers(String line) {
        String[] lineSplit = line.split(" ");
        List<String> output = new ArrayList<>();

        for (String word : lineSplit) {
            boolean digit = true;

            for (int i = 0; i < word.length(); i++) {

                if (!Character.isDigit(word.charAt(i))) {
                    digit = false;
                    break;
                }
            }

            if (!digit) {
                output.add(word);
            }
        }

        return String.join(" ", output);
    }

    /**
     * Method for removing most common stopwords.
     *
     * @param line to be preprocessed
     * @return line without stopwords.
     */
    private static String removeStopWords(String line) {
        String[] list = line.split(" ");
        List<String> output = new ArrayList<>();

        for (String word : list) {
            //removes stop-words and words with length one
            if (!Stopwords.isStopword(word) && word.length() > 1) {
                output.add(word);
            }
        }

        return String.join(" ", output);
    }

    /**
     * Method for removing words used in multiple labels, for more info {@link Vocabulary#removeWordsUsedInMultipleLabels(List)}
     *
     * @param vocab vocabulary instance to be used
     * @param line to be processed
     * @return line without words used in multiple labels
     */
    private static String removeWordsUsedInMultipleLabels(Vocabulary vocab, String line) {
        List<String> words = Arrays.asList(line.split(" "));
        return vocab.removeWordsUsedInMultipleLabels(words);
    }
}
