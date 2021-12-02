package Aoc2020;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Solution24 {

    public static void main(String[] args) throws IOException {
        Solution24 solution = new Solution24();
        solution.execute();
    }

    public static class XY{
        int x;
        int y;

        public XY(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            XY xy = (XY) o;
            return x == xy.x &&
                    y == xy.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
    private static final int TOTAL_DAYS = 100;

    public void execute() throws FileNotFoundException {
        List<List<Instruction>> input = getInput();
        Set<XY> flips = new HashSet<>();
        for(List<Instruction> instructions:input) {
            XY totalMove = new XY(0,0);
            for(Instruction inst: instructions) {
                XY movement = inst.getMovement();
                totalMove.x+=movement.x;
                totalMove.y+=movement.y;
            }
            if (flips.contains(totalMove)) {
                flips.remove(totalMove);
            } else {
                flips.add(totalMove);
            }
        }
        System.out.println(flips.size());
        int currentDay = 0;
        while(currentDay++ < TOTAL_DAYS) {
            Set<XY> dayResult = performArt(flips);
            System.out.println("day: " + currentDay + " number of black tiles: " + dayResult.size());
            flips=dayResult;
        }
    }

    public Set<XY> performArt(Set<XY> input) {
        Set<XY> result = new HashSet<>();
        List<XY> neighboursToCheck = new LinkedList<>();
        for(XY blackTile : input) {
            int blackNeighbours = countBlacks(blackTile, input, neighboursToCheck);
            if (blackNeighbours == 1 || blackNeighbours == 2) {
                result.add(blackTile);
            }
        }
        for(XY whiteTile : neighboursToCheck) {
            int blackNeighbours = countBlacks(whiteTile, input, null);
            if (blackNeighbours == 2) {
                result.add(whiteTile);
            }
        }
        return result;
    }

    int countBlacks(XY tile, Set<XY> input, List<XY> neighboursToCheck) {
        int count = 0;
        for(Instruction instruction : Instruction.values()) {
            XY movement = instruction.getMovement();
            XY neighbour = new XY(tile.x+movement.x,tile.y+movement.y);
            if (input.contains(neighbour)) {
                count++;
            } else {
                if (neighboursToCheck != null) {
                    neighboursToCheck.add(neighbour);
                }
            }
        }
        return count;
    }

    public enum Instruction {
        E,SE,SW,W,NW,NE;

        public XY getMovement() {
            switch(this) {
                case E:
                    return (new XY(2, 0));
                case SE:
                    return (new XY(1, -1));
                case SW:
                    return (new XY(-1, -1));
                case W:
                    return (new XY(-2, 0));
                case NW:
                    return (new XY(-1, 1));
                case NE:
                    return (new XY(1, 1));
                default:
                    throw new RuntimeException("unexpected value" + this);
            }
        }
    }

    private List<List<Instruction>> getInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("/Users/marc/Desktop/input24.txt"));
        List<List<Instruction>> combinations = new ArrayList<>();

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            List<Instruction> instructions = convertToInstructions(line.toCharArray());
            combinations.add(instructions);
        }
        scanner.close();
        return combinations;
    }

    private List<Instruction> convertToInstructions(char[] chars) {
        List<Instruction> instructions = new ArrayList<>();
        int i=0;
        while(i<chars.length) {
            Instruction instruction;
            switch (chars[i]) {
                case 'n':
                case 's': instruction = Instruction.valueOf((chars[i]+""+chars[i+1]).toUpperCase());
                    i++;
                    break;
                default:  instruction = Instruction.valueOf((chars[i]+"").toUpperCase());
            }
            instructions.add(instruction);
            i++;
        }

        return instructions;
    }


}