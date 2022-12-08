package Aoc2022;

import Common.InputParser;

import java.util.*;

public class Solution8 {

    private final Tree[][] grid;

    public static void main(String[] args) throws Exception {
        List<String> input = InputParser.getLinesAsListOfStrings("resources/2022/input8.txt");
        Solution8 solution = new Solution8(input);
        solution.execute();
    }

    public Solution8(List<String> input) {
        grid = new Tree[input.size()][input.get(0).length()];
        for(int i=0;i<grid.length;i++) {
            String line = input.get(i);
            for(int j=0; j<line.length();j++) {
                grid[i][j] = new Tree(Integer.parseInt(Character.toString(line.charAt(j))));
            }
        }
    }

    public void execute() throws Exception {
        processVisibilities();
        printCount();
    }

    private void printCount() {
        int count=0;
        int max=0;
        for (Tree[] trees : grid) {
            for (Tree tree : trees) {
                if (tree.isVisible()) {
                    count++;
                }
                if (tree.scenicScore() > max) {
                    max = tree.scenicScore();
                }
            }
        }
        System.out.println("count is: " + count);
        System.out.println("max is: " + max);
    }

    private void processVisibilities() {
        for(int i=0;i<grid.length;i++) {
            for(int j=0; j<grid[i].length;j++) {
                Tree thisTree = grid[i][j];
                topVisibilities(i, j, thisTree);
                leftVisibilities(i, j, thisTree);
                bottomVisibilities(i, j, thisTree);
                rightVisibilities(i, j, thisTree);
            }
        }
    }

    private void leftVisibilities(int i, int j, Tree thisTree) {
        boolean blocked=false;
        for(int z = j-1; z>=0; z--) {
            Tree otherTree = grid[i][z];
            if (!blocked) {
                thisTree.visibilityLeft++;
            }
            if (otherTree.height >= thisTree.height) {
                thisTree.visibleFromLeft=false;
                blocked=true;
            }
        }
    }

    private void topVisibilities(int i, int j, Tree thisTree) {
        boolean blocked=false;
        for(int z = i-1; z>=0; z--) {
            Tree otherTree = grid[z][j];
            if (!blocked) {
                thisTree.visibilityTop++;
            }
            if (otherTree.height >= thisTree.height) {
                thisTree.visibleFromTop=false;
                blocked=true;
            }
        }
    }

    private void bottomVisibilities(int i, int j, Tree thisTree) {
        boolean blocked=false;
        for(int z = i+1; z< grid.length; z++) {
            Tree otherTree = grid[z][j];
            if (!blocked) {
                thisTree.visibilityBottom++;
            }
            if (otherTree.height >= thisTree.height) {
                thisTree.visibleFromBottom=false;
                blocked=true;
            }
        }
    }

    private void rightVisibilities(int i, int j, Tree thisTree) {
        boolean blocked=false;
        for(int z = j+1; z< grid[i].length; z++) {
            Tree otherTree = grid[i][z];
            if (!blocked) {
                thisTree.visibilityRight++;
            }
            if (otherTree.height >= thisTree.height) {
                thisTree.visibleFromRight=false;
                blocked=true;
            }
        }
    }

    public static class Tree {
        int height;
        boolean visibleFromLeft = true;
        boolean visibleFromRight = true;
        boolean visibleFromBottom = true;
        boolean visibleFromTop = true;
        int visibilityLeft = 0;
        int visibilityRight = 0;
        int visibilityTop = 0;
        int visibilityBottom = 0;

        public Tree(int height) {
            this.height=height;
        }

        public boolean isVisible() {
            return visibleFromLeft || visibleFromRight || visibleFromBottom || visibleFromTop;
        }

        public int scenicScore() {
            return visibilityLeft*visibilityRight*visibilityBottom*visibilityTop;
        }
    }
}
