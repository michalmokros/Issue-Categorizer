package filtering;

import java.util.*;

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

 /*   public Set<String> findWordsWithCountLessThan(int minimum) {
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
