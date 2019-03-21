package com.mygdx.game.system;

import java.util.LinkedList;
import com.mygdx.game.system.LetterBag;

public class Player {
    public int score;
    public LinkedList<Word> wordsList = new LinkedList<>();
    public LinkedList currentLetter;
    private int howManyLetterOnStart = 8;


    //czy w linked List jest zachowana kolejność???
    //mam problem jak ująć kwetię innych słów, które tworzą się przypadkiem
    public void addWord(String word, int[] position, Direction.Diretions direction){
        Word wordToList = new Word();
        wordToList.score = Board.addWord(word, position, direction);
        wordToList.word = (direction.down)?Board.getFullWordDown(position):Board.getFullWordAcross(position);
        score += wordToList.score;
        wordsList.add(wordToList);
    }

    public void startWithRandomLetter (){
        this.currentLetter = LetterBag.takeRandomLetterFromBag(howManyLetterOnStart);
        System.out.println(this.currentLetter);
    }

    public void showMyWord(){
        for(Word word: wordsList){
            System.out.println(word.word + " " + word.score);
        }
    }
}
