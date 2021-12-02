package Aoc2021;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Solution1 {

    List<Long> inputs = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Solution1 solution = new Solution1();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        getInput();
        int result = 0;
        for(int i=3;i<inputs.size();i++) {
            if (getWindow(i-1) < getWindow(i)) {
                result++;
            }
        }
        System.out.println("Result : " + result);
    }

    private long getWindow(int endIndex) {
        return inputs.get(endIndex) + inputs.get(endIndex-1) + inputs.get(endIndex -2);
    }



    private void getInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("/Users/marc/Desktop/input1.txt"));
        List<String> output = new ArrayList<>();
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line != "") {
                inputs.add(Long.parseLong(line));
            }
        }
        scanner.close();
    }

}