package com.mygdx.game.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Board {
    public static String[][] baseBoard;
    public static String[][] letterBoard;
    public static String[][] checkedLetterBoard;
    public static Map<String, Integer> bonusPlace = new HashMap<>();

    public static void crateBoards() throws Exception {
        File boardFile = new File("core/assets/Board");
        BufferedReader boardDataBuffer = new BufferedReader(new FileReader(boardFile));
        String[] boardDimension = boardDataBuffer.readLine().split(" ");

        letterBoard = new String[Integer.parseInt(boardDimension[0])][Integer.parseInt(boardDimension[1])];
        fillBoard(letterBoard);

        checkedLetterBoard = new String[Integer.parseInt(boardDimension[0])][Integer.parseInt(boardDimension[1])];
        fillBoard(checkedLetterBoard);

        baseBoard = new String[Integer.parseInt(boardDimension[0])][Integer.parseInt(boardDimension[1])];
        fillBoard(baseBoard);


        boardDataBuffer.lines()
                .map(line -> line.split(" "))
                .forEach(tab -> {
                            baseBoard[Integer.parseInt(tab[1]) - 1][Integer.parseInt(tab[2]) - 1] = tab[0];
                            createBonusPlaceMap(tab[0]);
                        }
                );
        showBoard(baseBoard);
    }

    private static void fillBoard(String[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = "*";
            }
        }
    }

    public static void showBoard(String[][] board) {
        String showedLine = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                showedLine += board[i][j];
            }
            System.out.println(showedLine);
            showedLine = "";
        }
    }

    private static void createBonusPlaceMap(String placeName) {
        bonusPlace.put("s", 2);
        if (!bonusPlace.containsKey(placeName)) {
            bonusPlace.put(placeName, Integer.valueOf(String.valueOf(placeName.charAt(0))));
        }
    }

    public static Turn addWord(String myWord, int[] startPos, Direction direction) {
        Turn word = new Turn();
        for (int i = 0, index = 0, j = 0; index < myWord.length() && startPos[0] + i < 15 && startPos[1] + j < 15; ) {
            if (letterBoard[startPos[0] + i][startPos[1] + j].equals("*")) {
                letterBoard[startPos[0] + i][startPos[1] + j] = String.valueOf(myWord.charAt(index));

                addLetterToWord(String.valueOf(myWord.charAt(index)), startPos[0] + i, startPos[1] + j, word);
                ++index;
            }
            if (direction.down) i++;
            else j++;
        }
        return word;
    }

    private static Turn addLetterToWord(String myLetter, int x, int y, Turn word){
        LetterOnBoard letter = new LetterOnBoard();
        letter.letter = myLetter;
        letter.position[0] = x;
        letter.position[1] = y;
        word.letterInWord.add(letter);
        return word;
    }

    public static int getLetterBonus(int[] position) {
        String placeInBaseBoard = baseBoard[position[0]][position[1]];
        if (placeInBaseBoard.length() > 1 && placeInBaseBoard.charAt(1) == "l".charAt(0)) {
            return bonusPlace.get(placeInBaseBoard);
        }
        return 1;
    }

    public static int getWordBonus(int[] position, int wordBonus) {
        String placeInBaseBoard = baseBoard[position[0]][position[1]];
        if (placeInBaseBoard.length() > 1 && placeInBaseBoard.charAt(1) == "w".charAt(0)) {
            return Math.max(wordBonus, bonusPlace.get(placeInBaseBoard));
        }
        return Math.max(wordBonus, 1);
    }

    public static String getFullWord(int[] position, boolean down) {
        //position = {y, x} letterBoard[y][x]
        //to find start position
        int x = getBeginAcross(position);
        int y = getBeginDown(position);
        String word = "";
        if (down) {
            word = fullWordDown(position[1], y);
        } else {
            word = fullWordAcross(x, position[0]);
        }
        return word;
    }

    private static String fullWordAcross(int x, int y) {
        String word = "";
        while (x < 15 && letterBoard[y][x] != "*") {
            word += letterBoard[y][x];
            ++x;
        }
        return word;
    }

    private static String fullWordDown(int x, int y) {
        String word = "";
        while (y < 15 && letterBoard[y][x] != "*") {
            word += letterBoard[y][x];
            ++y;
        }
        return word;

    }

    private static int getBeginAcross(int[] position) {
        int x = 0;
        for (int i = position[1]; i >= 0; i--) {
            x = i;
            if (letterBoard[position[0]][i] == "*") {
                ++x;
                break;
            }
        }
        return x;
    }

    private static int getBeginDown(int[] position) {
        int y = 0;
        for (int i = position[0]; i >= 0; i--) {
            y = i;
            if (letterBoard[i][position[1]] == "*") {
                ++y;
                break;
            }
        }
        return y;
    }
}
