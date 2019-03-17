package filtering;

import java.util.*;

public class Vocabulary {
    private Map<String, Integer> dictionary;

    Vocabulary() {
        dictionary = new HashMap<>();
    }

    public void addWord(String word) {
        if (dictionary.containsKey(word)) {
            dictionary.put(word, dictionary.get(word) + 1);
        } else {
            dictionary.put(word, 1);
        }
    }

    public Set<String> findWordsWithCountLessThan(int minimum) {
        Set<String> output = new HashSet<>();

        for (Map.Entry<String, Integer> dict : dictionary.entrySet()) {
            if (dict.getValue() <= minimum) {
                output.add(dict.getKey());
            }
        }

        return output;
    }

    public String reduceWordsToNumOfMostPopular(List<String> words, int number) {
        SortedMap<Integer, String> resultMap = new TreeMap<>();
        List<String> output = new ArrayList<>();

        for (String word : words) {
            int wordCount = this.dictionary.get(word);
            resultMap.put(wordCount, word);
        }

        resultMap = ((TreeMap<Integer, String>) resultMap).descendingMap();

        for (SortedMap.Entry<Integer, String> entry : resultMap.entrySet()) {
            if (number == 0) {
                break;
            }

            output.add(entry.getValue());
            number--;
        }

        return String.join(" ", output);
    }
}
