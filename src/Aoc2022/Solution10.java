package Aoc2022;

import Common.InputParser;

import java.util.LinkedList;
import java.util.List;

public class Solution10 {


    public static void main(String[] args) throws Exception {
        Solution10 solution = new Solution10();
        solution.execute();
    }
    private int xRegister=1;
    private int cycle=1;

    private final boolean[][] screen = new boolean[6][40];

    private final List<Long> strengths = new LinkedList<>();

    public void execute() throws Exception {
        List<String> input = InputParser.getLinesAsListOfStrings("resources/2022/input10.txt");
        processInput(input);
        System.out.println("Total strengths: " + strengths.stream().reduce(Long::sum));
        // should be 13140 for test input
        // Should be 14420 for real input

        drawScreen();
    }

    private void drawScreen() {
        for (boolean[] booleans : screen) {
            for (boolean aBoolean : booleans) {
                if (aBoolean) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

    private void drawIfNecessary() {
        int currentDrawingX = (cycle-1)%40;
        int currentDrawingY = (cycle-1)/40;
        if (isInRange(currentDrawingX, xRegister)) {
            screen[currentDrawingY][currentDrawingX]=true;
        }
    }

    private boolean isInRange(int currentDrawingX, int xRegister) {
        return (currentDrawingX == xRegister || currentDrawingX == xRegister-1|| currentDrawingX == xRegister +1);
    }

    private void processInput(List<String> input) {
        for(String commandString : input) {
            drawAndSaveCycleStrengthIfNecessary();
            if (commandString.equals("noop")) {
                cycle++;
            } else {
                cycle++;
                drawAndSaveCycleStrengthIfNecessary();
                int valueToIncrease = Integer.parseInt(commandString.split(" ")[1]);
                xRegister += valueToIncrease;
                cycle++;
            }
        }
    }

    private void drawAndSaveCycleStrengthIfNecessary() {
        drawIfNecessary();
        if ((cycle % 40) == 20) {
            strengths.add((long)cycle * xRegister);
        }
    }
}
