package Aoc2021;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution21 {

    private static final String INPUTFILE = System.getenv("HOME") + "/workspace/adventofcode/resources/input21.txt";

    int currentDice=0;
    int totalDiceRolls = 0;

    List<Player> players = new ArrayList<>(2);

    public class Player {
        int score;
        int position;
        public Player(int score, int position) {
            this.score=score;
            this.position=position;
        }
    }

    public static void main(String[] args) throws IOException {
        Solution21 solution = new Solution21();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        getInput();
        play();
        int result = totalDiceRolls*Math.min(players.get(0).score,players.get(1).score);
        System.out.println("result: " + result);
    }

    public void play() {
        label: while (true) {
            for(Player player: players) {
                add3DiceRolls(player);
                if (player.score >=1000) {
                    break label;
                }
            }
        }
    }

    private void add3DiceRolls(Player player) {
        player.position+=(1+currentDice);
        currentDice = (currentDice+1)%100;
        player.position+=(1+currentDice);
        currentDice = (currentDice+1)%100;
        player.position+=(1+currentDice);
        currentDice = (currentDice+1)%100;
        player.position = player.position % 10;
        player.score+=(1+player.position);
        totalDiceRolls+=3;
    }

    private void getInput() throws FileNotFoundException {
        Scanner inputScanner = new Scanner(new FileInputStream(INPUTFILE));

        for(int i=1;i<=2;i++) {
            String line = inputScanner.nextLine().trim();
            Matcher lineMatcher = lineRegex.matcher(line);
            if (lineMatcher.matches()) {
                int startingPosition = Integer.parseInt(lineMatcher.group(2));
                players.add(new Player(0, startingPosition-1));
            } else {
                throw new RuntimeException("oops");
            }
        }
        inputScanner.close();
    }

    private static Pattern lineRegex = Pattern.compile("Player ([0-9]+) starting position: ([0-9]+)");

}

