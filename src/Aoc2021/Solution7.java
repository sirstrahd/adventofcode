package Aoc2021;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Solution7 {

    public static void main(String[] args) throws IOException {
        Solution7 solution = new Solution7();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        Integer[] input = getInput();
        Integer position;
        Long mean = 0L;
        for(Integer  crab : input) {
            mean+= crab;
        }
        mean = mean / input.length;

        Long result = 0L;
        for(Integer crab : input) {
            Long guess = Math.abs(crab-mean);

            result+= (guess * (guess+1)) / 2;
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

