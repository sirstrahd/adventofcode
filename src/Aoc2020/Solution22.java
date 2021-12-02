package Aoc2020;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution22 {

    public LinkedList<Integer> player1 = new LinkedList<>();
    public LinkedList<Integer> player2 = new LinkedList<>();


    public static void main(String[] args) throws IOException {
        Solution22 solution = new Solution22();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        getInput();
        while(!player1.isEmpty() && !player2.isEmpty()) {
            int player1card = player1.removeFirst();
            int player2card = player2.removeFirst();
            if (player1card > player2card) {
                System.out.println("1 wins round " + player1card + " vs " + player2card);
                player1.add(player1card);
                player1.add(player2card);
            } else {
                System.out.println("2 wins round " + player2card + " vs " + player1card);
                player2.add(player2card);
                player2.add(player1card);
            }
        }
        LinkedList<Integer> list;
        if (player1.isEmpty()) {
            System.out.println("2 wins");
            list = player2;
        } else {
            System.out.println("1 wins");
            list = player1;
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

    private void getInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("/Users/marc/Desktop/input22.txt"));
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
                    player2.add(value);
                } else {
                    player1.add(value);
                }
            }
        }
        scanner.close();
    }



}