package filtering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static java.util.logging.Level.FINE;

/**
 * Vocabulary class for storing and analyzing words used in different labels.
 *
 * @author xmokros
 */
public class Vocabulary {
    private final static Logger LOGGER = Logger.getLogger(Vocabulary.class.getName());

    private Map<String, Map<String, Integer>> labelDictionary;

    Vocabulary() {
        labelDictionary = new HashMap<>();
    }

    public void addWord(String word, String label) {
        if (!labelDictionary.containsKey(label)) {
            labelDictionary.put(label, new HashMap<>());
        }

        Map<String, Integer> wordMap = labelDictionary.get(label);
        if (wordMap.containsKey(word)) {
            wordMap.put(word, wordMap.get(word) + 1);
        } else {
            wordMap.put(word, 1);
        }
    }

    /**
     * Method for comparing and removing words, by some given ratio, used in opposing labels
     *
     * @param words to be checked and filtered
     * @param label label of the entry for comparison with words from other labels
     * @return filtered line without words
     */
    public String removeWordsUsedInMultipleLabels(List<String> words, String label) {
        List<String> output = new ArrayList<>(words);

        Map<String, Integer> labelMap = labelDictionary.get(label);
        for (String word : words) {
            int labelWordCount = labelMap.getOrDefault(word, 0);

            for (Map.Entry<String, Map<String, Integer>> labelEntry : labelDictionary.entrySet()) {
                if (labelEntry.getKey().equals(label)) {
                    continue;
                }

                int labelEntryWordCount = labelEntry.getValue().getOrDefault(word, 0);

                if (labelWordCount <= labelEntryWordCount * 2) {
                    output.remove(word);
                    LOGGER.log(FINE, "Removing word '" + word + "' from label '" + label + "' because of bad ratio of occurrence.");
                    break;
                }
            }
        }

        return String.join(" ", output);
    }
}
