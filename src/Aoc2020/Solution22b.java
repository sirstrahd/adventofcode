package Aoc2020;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution22b {

    public class GameState{
        public LinkedList<Integer> player1;
        public LinkedList<Integer> player2;
        public GameState() {
            player1= new LinkedList<>();
            player2=new LinkedList<>();
        }

        public GameState createCopy(int size1, int size2) {
            GameState gameState = new GameState();
            gameState.player1 = (LinkedList)player1.clone();
            gameState.player2 = (LinkedList)player2.clone();
            while(gameState.player1.size() > size1) {
                gameState.player1.removeLast();
            }
            while(gameState.player2.size() > size2) {
                gameState.player2.removeLast();
            }
            return gameState;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GameState gameState = (GameState) o;
            return Objects.equals(player1, gameState.player1) &&
                    Objects.equals(player2, gameState.player2);
        }
        public Set<Integer> stateHashes = new HashSet<>();

        @Override
        public int hashCode() {
            return Objects.hash(player1, player2);
        }
    }

    public static void main(String[] args) throws IOException {
        Solution22b solution = new Solution22b();
        solution.execute();
    }

    public int play(GameState game) {
        while(!game.player1.isEmpty() && !game.player2.isEmpty()) {
            int winner = 0;
            if (game.stateHashes.contains(game.hashCode())) {
                //System.out.println("Infinite recursion detected! player1 wins.");
                return 1;
            }
            game.stateHashes.add(game.hashCode());
            int player1card = game.player1.removeFirst();
            int player2card = game.player2.removeFirst();
            if (player1card <= game.player1.size() && player2card <= game.player2.size()) {
                GameState subGame = game.createCopy(player1card, player2card);
                winner=play(subGame);
            } else {
                if (player1card > player2card) {
                    winner = 1;
                } else {
                    winner = 2;
                }
            }
            if (winner==1) {
                //System.out.println("1 wins round " + player1card + " vs " + player2card);
                game.player1.add(player1card);
                game.player1.add(player2card);
            } else {
                //System.out.println("2 wins round " + player1card + " vs " + player2card);
                game.player2.add(player2card);
                game.player2.add(player1card);
            }
        }
        if (game.player1.isEmpty()) {
            return 2;
        } else {
            return 1;
        }
    }

    public void execute() throws FileNotFoundException {
        GameState game = getInput();
        int result = play(game);
        LinkedList<Integer> list;
        if (result == 2) {
            System.out.println("2 wins");
            list = game.player2;
        } else {
            System.out.println("1 wins");
            list = game.player1;
        }

        int multiplier=1;
        long score=0;
        while(!list.isEmpty()) {
            int current = list.removeLast();
            score+=multiplier*current;
            multiplier++;
        }
        System.out.println("score: " + score);
    }

    private static Pattern playerRegex = Pattern.compile("^Player ([0-9]+):$");

    private GameState getInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("/Users/marc/Desktop/input22.txt"));
        GameState game = new GameState();
        boolean player2found = false;

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                continue;
            }

            Matcher playerMatcher = playerRegex.matcher(line);
            if (playerMatcher.find()) {
                if (Integer.parseInt(playerMatcher.group(1)) == 2) {
                    player2found=true;
                }
            } else {
                int value = Integer.parseInt(line);
                if (player2found) {
                    game.player2.add(value);
                } else {
                    game.player1.add(value);
                }
            }
        }
        scanner.close();
        return game;
    }



}