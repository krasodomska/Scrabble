package com.mygdx.game.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

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

    public static int addWord(String word, int[] startPos, Direction direction) {
        int score = 0;
        int wordBonus = 1;
        String placeInBaseBoard = "";
        for (int i = 0, index = 0, j = 0; index < word.length() && startPos[0] + i < 15 && startPos[1] + j < 15; ) {
            if (letterBoard[startPos[0] + i][startPos[1] + j].equals("*")) {
                letterBoard[startPos[0] + i][startPos[1] + j] = String.valueOf(word.charAt(index));

                placeInBaseBoard = baseBoard[startPos[0] + i][startPos[1] + j];
                wordBonus = getWordBonus(placeInBaseBoard, wordBonus);

                score += LetterBag.pointTable.get(String.valueOf(word.charAt(index))) * getLetterBonus(placeInBaseBoard);
                ++index;
            }
            if (direction.down) i++;
            else j++;
        }
        score += (direction.down) ? Word.getWordScore(getFullWordDown(startPos)) : Word.getWordScore(getFullWordAcross(startPos)) - Word.getWordScore(word);
        score *= wordBonus;

        return score;
    }

    private static int getLetterBonus(String placeInBaseBoard) {
        if (placeInBaseBoard.length() > 1 && placeInBaseBoard.charAt(1) == "l".charAt(0)) {
            return bonusPlace.get(placeInBaseBoard);
        }
        return 1;
    }

    private static int getWordBonus(String placeInBaseBoard, int wordBonus) {
        if (placeInBaseBoard.length() > 1 && placeInBaseBoard.charAt(1) == "w".charAt(0)) {
            return Math.max(wordBonus, bonusPlace.get(placeInBaseBoard));
        }
        return Math.max(wordBonus, 1);
    }

    public static String getFullWordAcross(int[] position) {
        //position = {y, x} letterBoard[y][x]
        //to find start position
        int x = 0;
        String word = "";
        for (int i = position[1]; i >= 0; i--) {
            x = i;
            if (letterBoard[position[0]][i] == "*") {
                ++x;
                break;
            }
        }
        while (x < 15 && letterBoard[position[0]][x] != "*") {
            word += letterBoard[position[0]][x];
            ++x;
        }
        return word;
    }

    public static String getFullWordDown(int[] position) {
        //position = {y, x} letterBoard[y][x]
        //to find start position
        int y = 0;
        String word = "";
        for (int i = position[0]; i >= 0; i--) {
            y = i;
            if (letterBoard[i][position[1]] == "*") {
                ++y;
                break;
            }
        }
        while (y < 15 && letterBoard[y][position[1]] != "*") {
            word += letterBoard[y][position[1]];
            ++y;
        }
        return word;
    }

}
