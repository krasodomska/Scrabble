package com.mygdx.game.system;

import com.google.gwt.thirdparty.guava.common.collect.ArrayListMultimap;
import com.google.gwt.thirdparty.guava.common.collect.HashMultimap;
import com.google.gwt.thirdparty.guava.common.collect.ListMultimap;
import com.google.gwt.thirdparty.guava.common.collect.Multimap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Stream;

public class Dictionary {
    //wczytaj słowa z pliku na literę a i wrzuć do setu A itd
    static Map byLetter = new HashMap<Character, HashMap<Integer,Set<String>>>();
    static Map byWordLength = new HashMap<Integer, Set<String>>();
    static Set words = new HashSet<String>();

    public static void createDictionary() throws Exception {
        File file = new File("core/assets/Dictionary");
        BufferedReader dictionaryBuffer = new BufferedReader(new FileReader(file));

        dictionaryBuffer.lines()
                .forEach(word->addToSet(word));
    }

    private static void addToSet(String word) {
        Character firstLetter = Character.valueOf(word.charAt(0));
        Integer wordLen = Integer.valueOf(word.length());
        if (byLetter.containsKey(firstLetter)){
            byWordLength = (Map) byLetter.get(firstLetter);
            if (byWordLength.containsKey(wordLen)){
                words = (Set) byWordLength.get(wordLen);
            }

        }

        words.add(word);
        byWordLength.put(wordLen, words);
        Set test = new HashSet<String>();
        if(byWordLength.containsKey(Integer.valueOf(3))){
            test = (Set) byWordLength.get(3);
        }
        test.forEach(x ->System.out.println(x));
        byLetter.put(firstLetter, byWordLength);

        words.removeAll(words);

    }
}
