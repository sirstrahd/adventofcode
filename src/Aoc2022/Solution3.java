package Aoc2022;

import Common.InputParser;

import java.util.Arrays;

public class Solution3 {
    public static void main(String[] args) throws Exception {
        Solution3 solution = new Solution3();
        solution.execute();
    }

    public void execute() throws Exception {
        String[] sacks = InputParser.getLinesAsArrayOfStrings("resources/2022/input3.txt");
        firstPart(sacks);
        secondPart(sacks);
    }

    private void secondPart(String[] sacks) {
        int totalPriorities = 0;
        for(int i=0; i<sacks.length;i+=3) {
            totalPriorities += score(getCommonChar(sacks, i));
        }
        System.out.println("part 2 " + totalPriorities);
    }

    private Character getCommonChar(String[] sacks, int idx) {
        String sack1 = sacks[idx];
        String sack2 = sacks[idx+1];
        String sack3 = sacks[idx+2];
        for(int i=0;i<sack1.length();i++) {
            for(int j=0;j<sack2.length();j++) {
                if (sack1.charAt(i) != sack2.charAt(j)) {
                    continue;
                }
                for(int k=0;k<sack3.length();k++) {
                    if (sack1.charAt(i) == sack3.charAt(k)) {
                        return sacks[idx].charAt(i);
                    }
                }
            }
        }
        throw new RuntimeException("no common char found in group at idx" + idx);
    }

    private void firstPart(String[] sacks) {
        int result = Arrays.stream(sacks).map(this::getRepeatedChar).map(this::score).reduce(Integer::sum).orElseThrow();
        System.out.println("First part: " + result);
    }

    private int score(Character element) {
        int value;
        if (Character.isUpperCase(element)) { // uppercase
            value = element - 38;
        } else {
            value = element - 96;
        }
        return value;
    }

    private char getRepeatedChar(String sack) {
        int half = sack.length()/2;
        for(int i=0; i< half;i++) {
            for(int j=half;j<sack.length();j++) {
                if (sack.charAt(i) == sack.charAt(j)) {
                    return sack.charAt(i);
                }
            }
        }
        throw new RuntimeException("no repeated items found for sack: " + sack);
    }


}
