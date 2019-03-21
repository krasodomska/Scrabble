package com.mygdx.game.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class LetterBag {
    private static List bag = new LinkedList();
    public static Map<String, Integer> pointTable = new HashMap<>();

    public static void createBag()throws Exception {
        File file = new File("core/assets/Letters");
        BufferedReader letterDataBuffer = new BufferedReader(new FileReader(file));

        /*
         * tab[0] -> letter
         * tab[1] -> how many tiles
         * tab[2] -> how many points
         */
        letterDataBuffer.lines()
                .map(line -> line.split(" "))
                .forEach(tab -> {
                            addToBag(tab[0], Integer.parseInt(tab[1]));
                            addLetterAndPoint(tab[0], Integer.parseInt(tab[2]));
                        }
                );
        System.out.println(pointTable);
    }

    private static void addToBag(String letter, int howManyTiles){
        for (int i = howManyTiles; i > 0; i--){
            bag.add(letter);
        }
    }

    private static void addLetterAndPoint(String letter, int points){
        pointTable.put(letter,points);
    }

    public static LinkedList takeRandomLetterFromBag(int howMany){
        LinkedList drewLetter = new LinkedList();
        Random randomLetter = new Random();

        for(int i = howMany; i > 0; i--) {
            int letterIndex = randomLetter.nextInt(bag.size());
            drewLetter.add(bag.get(letterIndex));
            bag.remove(letterIndex);
        }
        return drewLetter;
    }

    public static LinkedList letterExchange(LinkedList<String> returnedLetter){
        LinkedList newLetters = takeRandomLetterFromBag(returnedLetter.size());
        returnedLetter.forEach(letter -> bag.add(letter));
        return newLetters;
    }
}
