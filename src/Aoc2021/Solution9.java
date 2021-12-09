package Aoc2021;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Solution9 {

    public static void main(String[] args) throws IOException {
        Solution9 solution = new Solution9();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        int[][] map = getInput();
        long sumOfLows = 0L;
        for(int i=0; i< map.length;i++) {
            for(int j=0; j<map[0].length;j++) {
                if (isLow(map, i, j)) {
                    sumOfLows+=(1+map[i][j]);
                }
            }
        }
        System.out.println("map: " + sumOfLows);

    }

    private boolean isLow(int[][] map, int x, int y){
        return (map[x][y] < leftNeighbour(map, x, y) && map[x][y] < rightNeighbour(map, x, y)
                && map[x][y] < topNeighbour(map, x, y)&& map[x][y] < bottomNeighbour(map, x, y));

    }

    private int leftNeighbour(int[][] map, int x, int y){
        if (y == 0) {
            return 10;
        } else {
            return map[x][y-1];
        }
    }

    private int rightNeighbour(int[][] map, int x, int y){
        if (y == map[0].length - 1) {
            return 10;
        } else {
            return map[x][y+1];
        }
    }

    private int topNeighbour(int[][] map, int x, int y){
        if (x == 0) {
            return 10;
        } else {
            return map[x-1][y];
        }
    }

    private int bottomNeighbour(int[][] map, int x, int y){
        if (x == map.length - 1) {
            return 10;
        } else {
            return map[x+1][y];
        }
    }

    private int[][] getInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("/Users/marc/workspace/adventofcode/resources/input9.txt"));
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

