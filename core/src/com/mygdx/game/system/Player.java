package com.mygdx.game.system;

import java.util.LinkedList;

public class Player {
    public int score = 0;
    public LinkedList<Turn> turnsList = new LinkedList<>();
    public LinkedList currentLetter;
    private int howManyLetterOnStart = 8;


    public void addWord(String word, int[] position, Direction direction) {
        Turn turnToList = Board.addWord(word, position, direction);
        turnToList.direction = direction;
        turnToList.createFullWordList();
        if (wordsExist(turnToList)) {
            System.out.println("exist");
            turnToList.getScore();
            turnsList.add(turnToList);
            score += turnToList.score;
            Board.checkedLetterBoard = Board.letterBoard;
        }
        else {
            Board.letterBoard = Board.checkedLetterBoard;
            System.out.println("not");
        }
//        Board.showBoard(Board.letterBoard);
    }

    public boolean wordsExist(Turn turns) {
        for (String word : turns.words) {
            if (Dictionary.wordExist(word)) continue;
            else return false;
        }
        return true;
    }

    public void startWithRandomLetter() {
        this.currentLetter = LetterBag.takeRandomLetterFromBag(howManyLetterOnStart);
        System.out.println(this.currentLetter);
    }

    public void showMyWord(){
        for(Turn turn: turnsList){
            System.out.println(turn.words.get(0) + " " + turn.score);
        }
    }
}
