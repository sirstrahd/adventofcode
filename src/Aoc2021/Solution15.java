package Aoc2021;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution15 {
    Node[][] map = null;
    int sizeX, sizeY;

    public static void main(String[] args) throws IOException {
        Solution15 solution = new Solution15();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        map = getInput();
        draw();
        boolean somethingChanged = true;
        map[sizeX-1][sizeY-1].minRemainingCost = map[sizeX-1][sizeY-1].stepInCost;
        while(somethingChanged) {
            somethingChanged=false;
            for (int i = sizeX - 1; i >= 0; i--) {
                for (int j = sizeY - 1; j >= 0; j--) {
                    int minNeighbourCost = getMinCostFromNeighbours(i, j);
                    if (minNeighbourCost != map[i][j].minRemainingCost) {
                        map[i][j].minRemainingCost = minNeighbourCost;
                        somethingChanged = true;
                    }
                }
            }
        }
        System.out.println("Total score: " + (map[0][0].minRemainingCost - map[0][0].stepInCost));
    }

    private void draw() {
        for(int i=0;i<sizeX;i++) {
            for(int j=0;j<sizeY;j++) {
                System.out.print(map[i][j].stepInCost);
            }
            System.out.println("");
        }
    }

    private int getMinCostFromNeighbours(int i, int j) {
        int minNeighbourCost = map[i][j].minRemainingCost;
        if (i>0) {
            minNeighbourCost = Math.min(minNeighbourCost, map[i-1][j].minRemainingCost+map[i][j].stepInCost);
        }
        if (j>0) {
            minNeighbourCost = Math.min(minNeighbourCost, map[i][j-1].minRemainingCost+map[i][j].stepInCost);
        }
        if (i<sizeX-1) {
            minNeighbourCost = Math.min(minNeighbourCost, map[i+1][j].minRemainingCost+map[i][j].stepInCost);
        }
        if (j<sizeY-1) {
            minNeighbourCost = Math.min(minNeighbourCost, map[i][j+1].minRemainingCost+map[i][j].stepInCost);
        }
        return minNeighbourCost;
    }

    private Node[][] getInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("/Users/marc/workspace/adventofcode/resources/input15.txt"));
        List<String> inputs = new ArrayList<>();
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            inputs.add(line);
        }
        scanner.close();
        sizeY=inputs.size();
        sizeX=inputs.get(0).length();
        Node[][] map = new Node[5*sizeY][5*sizeX];
        int i=0;
        for(String input : inputs) {
            int j=0;
            for(Character c : input.toCharArray()) {
                map[i][j] = new Node(Character.getNumericValue(c));
                j++;
            }
            i++;
        }
        for(i=0;i<5;i++) {
            for(int j=0;j<5;j++) {
                if (i==0 && j==0) {
                    continue;
                }
                for(int a=0;a<sizeX;a++) {
                    for(int b=0;b<sizeY;b++) {
                        int increase = i+j;
                        int cost = map[a][b].stepInCost + increase;
                        if (cost >= 10) {
                            cost-=9;
                        }
                        map[i*sizeY+a][j*sizeX+b] = new Node(cost);
                    }
                }
            }
        }
        sizeX = 5*sizeX;
        sizeY = 5*sizeY;
        return map;
    }

    public class Node {
        int stepInCost;
        int minRemainingCost = Integer.MAX_VALUE / 2;

        public Node(int stepInCost) {
            this.stepInCost = stepInCost;
        }
    }

}

