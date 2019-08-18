package filtering;

import entities.DataHolder;
import weka.core.Stopwords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Cleaner {

    public static void cleanArffList(List<DataHolder> arffList) {
        Vocabulary vocabTitle = new Vocabulary();
        Vocabulary vocabBody = new Vocabulary();
        //cleans data of numbers, symbols and stop-words
        for (DataHolder dataHolder : arffList) {
            //removes non alphanumeric symbols
            String newTitle = dataHolder.getTitle().replaceAll("[^\\p{L}\\p{Nd}]+", " ");
            String newBody = dataHolder.getBody().replaceAll("[^\\p{L}\\p{Nd}]+", " ");

            //removes numbers only (not words containing numbers)
            newTitle = removeWholeNumbers(newTitle);
            newBody = removeWholeNumbers(newBody);

            //clears string of stop-words
            newTitle = removeStopWords(newTitle);
            newBody = removeStopWords(newBody);

            dataHolder.setTitle(newTitle.toLowerCase());
            dataHolder.setBody(newBody.toLowerCase());

            for (String word : dataHolder.getTitle().split(" ")) {
                vocabTitle.addWord(word, dataHolder.getLabel());
            }

            for (String word : dataHolder.getBody().split(" ")) {
                vocabBody.addWord(word, dataHolder.getLabel());
            }
        }

        /*//cleans the data of words that are found n and less times in data
        for (DataHolder dataHolder : arffList) {
            dataHolder.setTitle(removeVocabularyWords(vocabTitle, dataHolder.getTitle(), 3));
            dataHolder.setBody(removeVocabularyWords(vocabBody, dataHolder.getBody(), 5));
        }*/

        /*for (DataHolder dataHolder : arffList) {
            dataHolder.setTitle(adjustVocabularyWordsToNumber(vocabTitle, dataHolder.getTitle(), 3));
            dataHolder.setBody(removeVocabularyWords(vocabBody, dataHolder.getBody(), 4));
        }*/

        for (DataHolder dataHolder : arffList) {
            dataHolder.setTitle(removeWordsUsedInMultipleLabels(vocabTitle, dataHolder.getTitle()));
            dataHolder.setBody(removeWordsUsedInMultipleLabels(vocabBody, dataHolder.getBody()));
        }


    }

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

    //used to remove words with appearance less than minimum
    /*private static String removeVocabularyWords(Vocabulary vocab, String line, int minimum) {
        Set<String> delWords = vocab.findWordsWithCountLessThan(minimum);
        List<String> output = new ArrayList<>();

        for (String word : line.split(" ")) {
            if (!delWords.contains(word)) {
                output.add(word);
            }
        }

        return String.join(" ", output);
    }

    private static String adjustVocabularyWordsToNumber(Vocabulary vocab, String line, int number) {
        //if the number of words in line is less or even dont remove any words
        List<String> words = Arrays.asList(line.split(" "));
        if (words.size() <= number) {
            return line;
        }

        return vocab.reduceWordsToNumOfMostPopular(words, number);
    }*/

    private static String removeWordsUsedInMultipleLabels(Vocabulary vocab, String line) {
        List<String> words = Arrays.asList(line.split(" "));
        return vocab.removeWordsUsedInMultipleLabels(words);
    }
}
