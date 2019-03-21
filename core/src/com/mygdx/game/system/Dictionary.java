package com.mygdx.game.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Dictionary {
    static Map<Character, Map<Integer,Set<String>>> byLetter = new HashMap<>();

    public static void createDictionary() throws Exception {
        File file = new File("core/assets/Dictionary");
        BufferedReader dictionaryBuffer = new BufferedReader(new FileReader(file));

        dictionaryBuffer.lines()
                .forEach(Dictionary::addToSet);
    }

    private static void addToSet(String word) {
        Character firstLetter = word.charAt(0);
        Integer wordLen = word.length();
        Set<String> wordSet = new HashSet<>();
        Map<Integer,Set<String>> byWordLength = new HashMap<>();
        if (byLetter.containsKey(firstLetter)) {
            if (byLetter.get(firstLetter).containsKey(wordLen)) {
                byLetter.get(firstLetter).get(wordLen).add(word);
            }
            else{
                wordSet.add(word);
                byLetter.get(firstLetter).put(wordLen, wordSet);
            }
        }
        else {
            wordSet.add(word);
            byWordLength.put(wordLen, wordSet);
            byLetter.put(firstLetter,byWordLength);
        }

    }

    public static boolean wordExist(String word){
        Character firstLetter = word.charAt(0);
        Integer wordLen = word.length();
        return byLetter.get(firstLetter).get(wordLen).contains(word);
    }
}
