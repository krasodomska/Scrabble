package com.mygdx.game.system;

public class Word {
    String word = "";
    int score;

    public static int getWordScore (String addedWord) {
        int wordScore = 0;
        for (int i = 0; i < addedWord.length(); i++) {
            wordScore += LetterBag.pointTable.get(String.valueOf(addedWord.charAt(i)));
        }
        return wordScore;
    }
}
