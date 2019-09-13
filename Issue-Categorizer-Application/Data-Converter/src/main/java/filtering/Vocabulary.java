package filtering;

import java.util.*;

/**
 * Vocabulary class for storing and analyzing words used in different labels.
 *
 * @author xmokros
 */
public class Vocabulary {
    private Map<String, Integer> bugDictionary;
    private Map<String, Integer> enhDictionary;
    private int bugCount;
    private int enhCount;

    Vocabulary() {
        bugDictionary = new HashMap<>();
        enhDictionary = new HashMap<>();
    }

    public int getBugCount() {
        return bugCount;
    }

    public int getEnhCount() {
        return enhCount;
    }

    public void addWord(String word, String label) {
        if (label.equals("bug")) {
            if (bugDictionary.containsKey(word)) {
                bugDictionary.put(word, bugDictionary.get(word) + 1);
            } else {
                bugDictionary.put(word, 1);
            }
            bugCount++;
        } else {
            if (enhDictionary.containsKey(word)) {
                enhDictionary.put(word, enhDictionary.get(word) + 1);
            } else {
                enhDictionary.put(word, 1);
            }
            enhCount++;
        }
    }

    /**
     * Method for comparing and removing words, by some given ratio, used in opposing labels
     *
     * @param words to be checked and filtered
     * @return filtered line without words
     */
    public String removeWordsUsedInMultipleLabels(List<String> words) {
        List<String> output = new ArrayList<>();

        for (String word : words) {
            int bugOccurence = bugDictionary.get(word) == null ? 0 : bugDictionary.get(word);
            int enhOccurence = enhDictionary.get(word) == null ? 0 : enhDictionary.get(word);

            if ((enhOccurence > bugOccurence * 2) || (bugOccurence > enhOccurence * 2)) {
                output.add(word);
            } else {
                System.out.println("Removing word '" + word + "' with bugOcc -> " + bugOccurence + " and enhOcc -> " + enhOccurence);
            }
        }

        return String.join(" ", output);
    }
}
