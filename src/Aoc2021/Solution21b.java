package Aoc2021;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution21b {

    private static final String INPUTFILE = System.getenv("HOME") + "/workspace/adventofcode/resources/input21.txt";

    public Map<GameState, Wins> cache = new HashMap<>();
    List<Player> players = new ArrayList<>(2);
    Map<Integer, Integer> dieRolls = new HashMap<>();

    public class Player {
        int score;
        int position;
        public Player(int score, int position) {
            this.score=score;
            this.position=position;
        }
    }

    public static void main(String[] args) throws IOException {
        Solution21b solution = new Solution21b();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        for(int i=1;i<4;i++) {
            for(int j=1;j<4;j++) {
                for(int k=1;k<4;k++) {
                    int result = i+j+k;
                    dieRolls.put(result, dieRolls.getOrDefault(result, 0)+1);
                }
            }
        }

        getInput();
        GameState state = new GameState(true,0,0,players.get(0).position, players.get(1).position);
        Wins wins = play(state);
        System.out.println("result: " + Math.max(wins.player1Wins, wins.player2Wins));
    }

    public Wins play(GameState state) {
        if (cache.get(state)!= null) {
            return cache.get(state);
        }
        Wins accumulatedWins;
        //System.out.println("Checking state: " + state);
        if (state.score1 >=21) {
            accumulatedWins = new Wins(1,0);
        } else if (state.score2 >=21) {
            accumulatedWins = new Wins(0,1);
        } else {
           accumulatedWins= new Wins(0, 0);
            if (state.player1Turn) {
                for (Integer dieRoll : dieRolls.keySet()) {
                    int amount = dieRolls.get(dieRoll);
                    int position = state.position1 + dieRoll;
                    if (position >= 11) {
                        position -= 10;
                    }
                    int score = state.score1 + position;
                    GameState newState = new GameState(false, score, state.score2, position, state.position2);
                    Wins playWins = play(newState);
                    accumulatedWins = new Wins(accumulatedWins.player1Wins + (amount * playWins.player1Wins), accumulatedWins.player2Wins + (amount * playWins.player2Wins));
                }
            } else {
                for (Integer dieRoll : dieRolls.keySet()) {
                    int amount = dieRolls.get(dieRoll);
                    int position = state.position2 + dieRoll;
                    if (position >= 11) {
                        position -= 10;
                    }
                    int score = state.score2 + position;
                    GameState newState = new GameState(true, state.score1, score, state.position1, position);
                    Wins playWins = play(newState);
                    accumulatedWins = new Wins(accumulatedWins.player1Wins + (amount * playWins.player1Wins), accumulatedWins.player2Wins + (amount * playWins.player2Wins));
                }
            }
        }
        cache.put(state, accumulatedWins);
        return accumulatedWins;
    }

    private void getInput() throws FileNotFoundException {
        Scanner inputScanner = new Scanner(new FileInputStream(INPUTFILE));

        for(int i=1;i<=2;i++) {
            String line = inputScanner.nextLine().trim();
            Matcher lineMatcher = lineRegex.matcher(line);
            if (lineMatcher.matches()) {
                int startingPosition = Integer.parseInt(lineMatcher.group(2));
                players.add(new Player(0, startingPosition));
            } else {
                throw new RuntimeException("oops");
            }
        }
        inputScanner.close();
    }

    public class Wins {
        final long player1Wins;
        final long player2Wins;
        public Wins(long player1Wins, long player2Wins) {
            this.player1Wins = player1Wins;
            this.player2Wins = player2Wins;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Wins wins = (Wins) o;
            return player1Wins == wins.player1Wins &&
                    player2Wins == wins.player2Wins;
        }

        @Override
        public int hashCode() {
            return Objects.hash(player1Wins, player2Wins);
        }
    }

    public class GameState {
        final boolean player1Turn;
        final int score1;
        final int score2;
        final int position1;
        final int position2;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GameState gameState = (GameState) o;
            return player1Turn == gameState.player1Turn && score1 == gameState.score1 && score2 == gameState.score2 && position1 == gameState.position1 && position2 == gameState.position2;
        }

        @Override
        public int hashCode() {
            return Objects.hash(player1Turn, score1, score2, position1, position2);
        }

        @Override
        public String toString() {
            return "GameState{" +
                    "player1Turn=" + player1Turn +
                    ", score1=" + score1 +
                    ", score2=" + score2 +
                    ", position1=" + position1 +
                    ", position2=" + position2 +
                    '}';
        }

        public GameState(boolean player1Turn, int score1, int score2, int position1, int position2) {
            this.player1Turn = player1Turn;
            this.score1 = score1;
            this.score2 = score2;
            this.position1 = position1;
            this.position2 = position2;
        }
    }
    private static Pattern lineRegex = Pattern.compile("Player ([0-9]+) starting position: ([0-9]+)");

}

