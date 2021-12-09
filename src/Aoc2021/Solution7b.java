package Aoc2021;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Solution7b {

    public static void main(String[] args) throws IOException {
        Solution7b solution = new Solution7b();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        Integer[] input = getInput();
        Long result = 0L;
        for(Integer crab : input) {
            result+= crab;
        }
        Long position = result / input.length;
        System.out.println("Determined position: " + position);
        result = 0L;
        for(Integer crab : input) {
            result+= (Math.abs(crab-position));
        }
        // 363196 too low
        System.out.println("Total result: " + result);
    }

    private Integer[] getInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("/Users/marc/workspace/adventofcode/resources/input7.txt"));
        String line = scanner.nextLine().trim();
        scanner.close();
        String[] numbers = line.split(",");
        List<Integer> crabs = new ArrayList<>();
        for(String numberString : numbers) {
            Integer number = Integer.parseInt(numberString);
            crabs.add(number);
        }
        Integer[] array = new Integer[0];
        array = crabs.toArray(array);
        Arrays.sort(array);
        return array;
    }
}

