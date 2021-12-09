package Aoc2021;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Solution6b {

    public static void main(String[] args) throws IOException {
        Solution6b solution = new Solution6b();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        Map<Integer, Long> map = getInput();
        int numberOfDaysLeft = 256;
        while(numberOfDaysLeft > 0) {
            Map<Integer, Long> newMap = new HashMap<>();
            for(int i=0;i<9;i++) {
                long currentValue = map.getOrDefault(i,0L);
                if (i==0) {
                    newMap.put(6, currentValue + map.getOrDefault(7,0L));
                    newMap.put(8, currentValue);
                } else if (i==7) {
                    continue;
                } else {
                    newMap.put(i-1, currentValue);
                }
            }
            numberOfDaysLeft--;
            map = newMap;
        }
        Long sum =0L;
        for(Long value : map.values()) {
            sum+=value;
        }
        System.out.println("Total sum: " + sum);
    }

    private Map<Integer, Long> getInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("/Users/marc/workspace/adventofcode/resources/input6.txt"));
        String line = scanner.nextLine().trim();
        scanner.close();
        String[] numbers = line.split(",");
        Map<Integer, Long> fishes = new HashMap<>();
        for(String numberString : numbers) {
            Integer number = Integer.parseInt(numberString);
            fishes.put(number, fishes.getOrDefault(number, 0L)+1);
        }
        return fishes;
    }
}

