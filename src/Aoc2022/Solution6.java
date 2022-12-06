package Aoc2022;

import Common.InputParser;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Solution6 {
    public static void main(String[] args) throws Exception {
        Solution6 solution = new Solution6();
        solution.execute();
    }
    public void execute() throws Exception {
        List<Character> input =  InputParser.getAsListOfCharacters("resources/2022/input6.txt");
        part1(input);
        part2(input);
    }

    private void part2(List<Character> input) {
        for(int i = 13; i< input.size(); i++) {
            if (isStartOfMessage(input, i, 14)) {
                System.out.println("Part1 found at position " + (i+1));
                break;
            }
        }
    }

    private static void part1(List<Character> input) {
        for(int i = 3; i< input.size(); i++) {
            if (isStartOfMessage(input, i,4)) {
                System.out.println("Part2 found at position " + (i+1));
                break;
            }
        }
    }

    public static boolean isStartOfMessage(List<Character> input, int currentIndex, int size) {
        int startOfCandidate=currentIndex-(size-1);
        for(int i=startOfCandidate;i<currentIndex;i++) {
            for(int j=startOfCandidate+1; j<=currentIndex;j++) {
                if (i==j) {
                    continue;
                }
                if (input.get(i) == input.get(j)) {
                    return false;
                }
            }
        }
        return true;
    }
}
