package Aoc2022;

import Common.InputParser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Solution1 {
    public static void main(String[] args) throws Exception {
        Solution1 solution = new Solution1();
        solution.execute();
    }

    public void execute() throws Exception {
        String[] input = InputParser.getLinesAsArrayOfStrings("resources/2022/input1.txt");
        ArrayList<ArrayList<String>> clusters = clusterify(input);
        long result = getMostCalories(clusters);
        System.out.println("The elf with the most calories has: " + result);
        List<Long> elves = getListOfCalories(clusters);
        System.out.println("First elf has: " + elves.get(0));
        System.out.println("Second elf has: " + elves.get(1));
        System.out.println("Third elf has: " + elves.get(2));
        System.out.println("Sum of three: " + (elves.get(0) + elves.get(1) + elves.get(2)));
    }


    private long getMostCalories(ArrayList<ArrayList<String>> clusters) {
        long currentMax = 0L;
        for (ArrayList<String> elf : clusters) {
            currentMax = Math.max(getCalories(elf), currentMax);
        }
        return currentMax;
    }

    private List<Long> getListOfCalories(ArrayList<ArrayList<String>> clusters) {
        return clusters.stream().map(this::getCalories).sorted((o1, o2) -> o2.intValue() - o1.intValue()).collect(Collectors.toList());
    }

    private long getCalories(ArrayList<String> elf) {
        long total = 0L;
        for (String line : elf) {
            total += Long.parseLong(line);
        }
        return total;
    }

    private ArrayList<ArrayList<String>> clusterify(String[] input) {
        ArrayList<ArrayList<String>> result = new ArrayList();
        ArrayList<String> currentElf = new ArrayList<>();
        for (String line : input) {
            if (line.equals("")) {
                result.add(currentElf);
                currentElf = new ArrayList<>();
            } else {
                currentElf.add(line);
            }
        }
        result.add(currentElf);
        return result;
    }
}
