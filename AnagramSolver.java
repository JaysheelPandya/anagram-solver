// Jaysheel Pandya
// CSE 143 DD with Xunmei Liu
// Homework 6
// The AnagramSolver class takes a phrase and finds
// all perfect combinations of anagrams that can be
// made from that phrase using a given dictionary and
// a max number of anagrams to be made.

import java.util.*;

public class AnagramSolver {

    private Map<String, LetterInventory> dictionary;

    private Map<Integer, String> dictIndices;

    // Uses the given list to create a dictionary
    // for the anagram solver
    public AnagramSolver(List<String> list) {
        dictionary = new HashMap<>();
        dictIndices = new HashMap<>();
        int index = 0;
        for (String s : list) {
            dictionary.put(s, new LetterInventory(s));
            dictIndices.put(index, s);
            index++;
        }
    }

    // PRE: The max number of anagrams to be made must
    // be greater than or equal to 0 (throws IllegalArgumentException
    // otherwise).
    // POST: Prunes the dictionary to only contain words that
    // can be anagrams of the given phrase
    // If the given max is 0, explores the pruned dictionary
    // for an unlimited number of anagrams
    // Otherwise explores the pruned dictionary with the given
    // max number of anagrams to be made
    public void print(String s, int max) {
        if (max < 0) {
            throw new IllegalArgumentException();
        }
        Map<String, LetterInventory> newDict = new HashMap<>();
        Map<Integer, String> newDictIndices = new HashMap<>();
        LetterInventory s1 = new LetterInventory(s);
        int index = 0;
        for (int i = 0; i < dictIndices.size(); i++) {
            String word = dictIndices.get(i);
            if (s1.subtract(dictionary.get(word)) != null) {
                newDict.put(word, dictionary.get(word));
                newDictIndices.put(index, word);
                index++;
            }
        }
        if (max == 0) {
            explore(s1, s1.size(), newDict, newDictIndices, new ArrayList<>());
        }
        else {
            explore(s1, max, newDict, newDictIndices, new ArrayList<>());
        }
    }

    // Recursively explores every word in the pruned dictionary to find
    // anagrams of the phrase s, until the max number of anagrams is reached
    // Prints the list of anagram words, in dictionary order, if a perfect
    // combination of anagrams is created within the given maximum allowed
    private void explore(LetterInventory s, int max, Map<String, LetterInventory> newDict,
                         Map<Integer, String> newDictIndices, List<String> returnList) {
        if (s.isEmpty()) {
            System.out.println(returnList);
        }
        else if (max > 0) {
            for (int i = 0; i < newDictIndices.size(); i++) {
                String word = newDictIndices.get(i);
                if (s.subtract(newDict.get(word)) != null) {
                    returnList.add(word);
                    explore(s.subtract(newDict.get(word)), max - 1, newDict,
                            newDictIndices, returnList);
                    returnList.remove(returnList.size() - 1);
                }
            }
        }
    }
}