package Aoc2022;

import Common.InputParser;

import java.util.HashMap;
import java.util.Map;

public class Solution2 {
    public static void main(String[] args) throws Exception {
        Solution2 solution = new Solution2();
        solution.execute();
    }
    public final Map<Character, Integer> yourMoveScores = new HashMap<>();

    public Solution2() {
        yourMoveScores.put('X',1);
        yourMoveScores.put('Y',2);
        yourMoveScores.put('Z',3);
    }
    public void execute() throws Exception {
        String[] moves = InputParser.getLinesAsArrayListOfStrings("resources/2022/input2.txt");
        long totalScoreFirstPart = 0;
        long totalScoreSecondPart = 0;
        for(String move : moves) {
            totalScoreFirstPart += getScoreFirstPart(move.charAt(0), move.charAt(2));
            totalScoreSecondPart += getScoreSecondPart(move.charAt(0), move.charAt(2));
        }
        System.out.println("total score first part: " + totalScoreFirstPart);
        System.out.println("total score second part: " + totalScoreSecondPart);

    }

    private long getScoreSecondPart(char oppMove, char yourResult) {
        final Character yourMove = yourMoveForSecondPart(oppMove, yourResult);
        return yourMoveScores.get(yourMove) + scoreFromGameFirstPart(oppMove, yourMove);
    }

    private long getScoreFirstPart(char oppMove, char yourMove) {
        return yourMoveScores.get(yourMove) + scoreFromGameFirstPart(oppMove, yourMove);
    }


    private Character yourMoveForSecondPart(char oppMove, char yourResult) {
        switch (oppMove) {
            case 'A': // THEIR ROCK
                switch (yourResult) {
                    case 'X': return 'Z'; // LOSE
                    case 'Y': return 'X'; // DRAW
                    case 'Z': return 'Y'; // WIN
                }
                break;
            case 'B': // THEIR PAPER
                switch (yourResult) {
                    case 'X': return 'X'; // LOSE
                    case 'Y': return 'Y'; // DRAW
                    case 'Z': return 'Z'; // WIN
                }
                break;
            case 'C': // THEIR SCISSORS
                switch (yourResult) {
                    case 'X': return 'Y'; // LOSE
                    case 'Y': return 'Z'; // DRAW
                    case 'Z': return 'X'; // WIN
                }
                break;
        }
        throw new RuntimeException("wut?");
    }

    private Integer scoreFromGameFirstPart(char oppMove, char yourMove) {
        switch (oppMove) {
            case 'A': // THEIR ROCK
                switch (yourMove) {
                    case 'X': return 3; // YOUR ROCK
                    case 'Y': return 6; // YOUR PAPER
                    case 'Z': return 0; // YOUR SCISSORS
                }
                break;
            case 'B': // THEIR PAPER
                switch (yourMove) {
                    case 'X': return 0; // YOUR ROCK
                    case 'Y': return 3; // YOUR PAPER
                    case 'Z': return 6; // YOUR SCISSORS
                }
                break;
            case 'C': // THEIR SCISSORS
                switch (yourMove) {
                    case 'X': return 6; // YOUR ROCK
                    case 'Y': return 0; // YOUR PAPER
                    case 'Z': return 3; // YOUR SCISSORS
                }
                break;
        }
        throw new RuntimeException("wut?");
    }


}
