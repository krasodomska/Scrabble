package com.mygdx.game.system;

import java.util.LinkedList;
import java.util.List;

public class Turn {
    //String word = "";
    int score = 0;
    Direction direction;
    List<LetterOnBoard> letterInWord = new LinkedList<>();
    List<String> words = new LinkedList<>();

    public static int getWordScore(String word) {
        int wordScore = 0;
        for (int i = 0; i < word.length(); i++) {
            wordScore += LetterBag.pointTable.get(String.valueOf(word.charAt(i)));
        }
        return wordScore;
    }

    public void getScore() {
        scoreFromMainWord();
        socoreFromSideWord();
        // uzyskaj punkty z bazowego słowa jest
        //uzykaj punkty z innych słów
    }

    private void scoreFromMainWord() {
        int mainScore = 0;
        int wordBonus = 1;
        for (LetterOnBoard letter : letterInWord) {
            mainScore += LetterBag.pointTable.get(letter.letter) * Board.getLetterBonus(letter.position);
            wordBonus = Board.getWordBonus(letter.position, wordBonus);
        }
        mainScore += (getWordScore(Board.getFullWord(letterInWord.get(0).position, direction.down)) - scoreFormLetters());
        mainScore *= wordBonus;
        score += mainScore;
        System.out.println();
    }

    private void socoreFromSideWord(){
        int wordBonus = 1;
        int sideWordScore = 0;
        for(LetterOnBoard letter : letterInWord){
            if (letter.createSecondWord){
                sideWordScore += LetterBag.pointTable.get(letter.letter) * Board.getLetterBonus(letter.position);
                wordBonus = Board.getWordBonus(letter.position, wordBonus);
                sideWordScore += (getWordScore(Board.getFullWord(letterInWord.get(0).position, !direction.down)) - LetterBag.pointTable.get(letter.letter));
                sideWordScore *= wordBonus;
                score+=sideWordScore;
            }
        }

    }

    private int scoreFormLetters() {
        return letterInWord.stream().mapToInt(letter -> LetterBag.pointTable.get(letter.letter)).sum();
    }

    public void createFullWordList() {
        words.add(Board.getFullWord(letterInWord.get(0).position, direction.down));

        letterInWord.forEach(letter -> {
                    if (Board.getFullWord(letter.position, !direction.down).length() > 1) {
                        words.add(Board.getFullWord(letter.position, !direction.down));
                        letter.createSecondWord = true;
                    }
                }
        );
    }
}
