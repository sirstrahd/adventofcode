package Aoc2021;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution3b {

    public static void main(String[] args) throws IOException {
        Solution3b solution = new Solution3b();
        solution.execute2();
    }


    public void execute2() throws FileNotFoundException {
        List<String> input = getInput();
        String o2 = getO2(input);
        String co2 = getCO2(input);
        Long result = convertToNumber(o2.toCharArray()) * convertToNumber(co2.toCharArray());
        System.out.println("Result: " + result);
    }

    public String getO2(List<String> input) {
        int currentIndex=0;
        while(input.size() > 1) {
            int countOf1s = 0;
            int countOf0s = 0;
            for (int j = 0; j < input.size(); j++) {
                Character current = input.get(j).charAt(currentIndex);
                if (current == '1') {
                    countOf1s++;
                } else{
                    countOf0s++;
                }
            }
                 if (countOf1s >= countOf0s) {
                input = removeWithBitInPosition(input, currentIndex, '0');
            } else  {
                input = removeWithBitInPosition(input, currentIndex, '1');
            }
            currentIndex++;
        }
        System.out.println("o2: " + input.get(0));
        return input.get(0);
    }

    public String getCO2(List<String> input) {
        int currentIndex=0;
        while(input.size() > 1) {
            int countOf1s = 0;
            int countOf0s = 0;
            for (int j = 0; j < input.size(); j++) {
                Character current = input.get(j).charAt(currentIndex);
                if (current == '1') {
                    countOf1s++;
                } else{
                    countOf0s++;
                }
            }
               if (countOf1s >= countOf0s) {
                input = removeWithBitInPosition(input, currentIndex, '1');
            } else  {
                input = removeWithBitInPosition(input, currentIndex, '0');
            }
            currentIndex++;
        }
        System.out.println("co2: " + input.get(0));
        return input.get(0);
    }

    private List<String> removeWithBitInPosition(List<String> input, int currentIndex, char c) {
        List<String> output = new ArrayList<>();
        for(String line : input) {
            if (line.charAt(currentIndex) != c) {
                output.add(line);
            }
        }
        return output;
    }

    private long convertToNumber(char[] commons) {
        long gamma = 0L;
        for(char c : commons) {
            gamma = (gamma << 1) + (c == '1' ? 1 : 0);
        }
        return gamma;
    }


    private List<String> getInput() throws FileNotFoundException {
        List<String> inputs = new ArrayList<>();
        Scanner scanner = new Scanner(new FileInputStream("/Users/marc/workspace/adventofcode/resources/input3.txt"));
        List<String> output = new ArrayList<>();
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line != "") {
                inputs.add(line);
            }
        }
        scanner.close();
        return inputs;
    }

}
