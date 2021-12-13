package Aoc2021;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution11b {

    public static void main(String[] args) throws IOException {
        Solution11b solution = new Solution11b();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        int[][] input = getInput();
        int step=0;
        while(true) {
            step++;
            int flashes = performStep(input);
            if (flashes == 100) {
                break;
            }
        }

        System.out.println("Total score: " + step);
    }

    private int performStep(int[][] input) {
        int result = increaseAll(input);
        for(int i=0;i<10;i++) {
            for(int j=0;j<10;j++) {
                if (input[i][j] == 10) {
                    input[i][j] = 0;
                }
            }
        }
        return result;
    }

    private int increaseAll(int[][] input) {
        int flashes = 0;
        for(int i=0;i<10;i++) {
            for(int j=0;j<10;j++) {
                flashes+=increase(input, i, j);
            }
        }
        return flashes;
    }

    private int increase(int[][] input, int i, int j) {
        if (i<0 || i>=10 || j<0 || j>=10 || input[i][j] == 10) {
            return 0;
        }
        input[i][j]++;
        if (input[i][j] == 10) {
            return processFlash(input, i, j);
        }
        return 0;
    }

    private int processFlash(int[][] input, int i, int j) {
        int flashes=1;
        flashes+=increase(input, i-1,j-1);
        flashes+=increase(input, i,j-1);
        flashes+=increase(input, i+1,j-1);
        flashes+=increase(input,i-1, j);
        flashes+=increase(input,i+1, j);
        flashes+=increase(input, i-1,j+1);
        flashes+=increase(input, i,j+1);
        flashes+=increase(input, i+1,j+1);
        return flashes;
    }

    private int[][] getInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("/Users/marc/workspace/adventofcode/resources/input11.txt"));
        List<String> inputs = new ArrayList<>();
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            inputs.add(line);
        }
        scanner.close();
        int[][] map = new int[inputs.size()][inputs.get(0).length()];
        int i=0;
        for(String input : inputs) {
            int j=0;
            for(Character c : input.toCharArray()) {
                map[i][j] = Character.getNumericValue(c);
                j++;
            }
            i++;
        }
        return map;
    }
}

