package Aoc2021;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution3 {

    public static void main(String[] args) throws IOException {
        Solution3 solution = new Solution3();
        solution.execute2();
    }

    public void execute2() throws FileNotFoundException {
        List<String> input = getInput();
        Long power = getPower(input);
    }

    public Long getPower(List<String> input) {
        Character[] commons = new Character[input.get(0).length()];
        for(int i=0;i<input.get(0).length();i++) {
            int countOf1s = 0;
            for(int j=0;j<input.size();j++) {
                Character current = input.get(j).charAt(i);
                if (current == '1') {
                    countOf1s++;
                }
            }
            if (countOf1s >= (input.size()/2)) {
                commons[i] = '1';
            } else {
                commons[i] = '0';
            }
        }
        long power = convertToNumber(commons);
        System.out.println("power: " + power);
        return power;
    }

    private long convertToNumber(Character[] commons) {
        long gamma = 0L;
        long epsilon =0L;
        for(Character c : commons) {
            gamma = (gamma << 1) + (c == '1' ? 1 : 0);
            epsilon = (epsilon << 1) + (c == '1' ? 0 : 1);
        }
        return (gamma * epsilon);
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
