package Aoc2022;

import Common.InputParser;

import java.util.*;

public class Solution14 {


    Position source = new Position(500,0);

    Set<Position> allPositions;

    int lowestRock;

    int floor;
    long sandCounter;

    public static void main(String[] args) throws Exception {
        Solution14 solution = new Solution14();
        solution.execute();
    }

    public void execute() throws Exception {
        List<String> input = InputParser.getLinesAsListOfStrings("resources/2022/input14.txt");
        populatePositions(input);
        int previousSize = allPositions.size()-1;
        while(previousSize != allPositions.size()) {
            previousSize=allPositions.size();
            dropSandPart1();
        }
        System.out.println("Part1: sand: " + sandCounter);
        populatePositions(input);
        while(!allPositions.contains(source)) {
            dropSandPart2();
        }
        System.out.println("Part2: sand: " + sandCounter);

    }

    private void populatePositions(List<String> input) {
        allPositions = new HashSet<>();
        sandCounter = 0;
        lowestRock = 0;
        for(String line: input) {
            List<Position> positions = Arrays.stream(line.split(" -> ")).map(x -> {
                List<Integer> values = Arrays.stream(x.split(",")).map(Integer::parseInt).toList();
                return new Position(values.get(0), values.get(1));
            }).toList();
            allPositions.add(positions.get(0));
            for(int i=0;i<positions.size()-1;i++) {
                Position pos1 = positions.get(i);
                Position pos2 = positions.get(i+1);
                int xDelta = pos2.x - pos1.x;
                int yDelta = pos2.y - pos1.y;
                lowestRock = Integer.max(lowestRock, Integer.max(pos1.y,pos2.y));
                while(xDelta>0) {
                    allPositions.add(new Position(pos1.x + xDelta, pos1.y));
                    xDelta--;
                }
                while(xDelta<0) {
                    allPositions.add(new Position(pos1.x + xDelta, pos1.y));
                    xDelta++;
                }
                while(yDelta>0) {
                    allPositions.add(new Position(pos1.x, pos1.y+yDelta));
                    yDelta--;
                }
                while(yDelta<0) {
                    allPositions.add(new Position(pos1.x, pos1.y+yDelta));
                    yDelta++;
                }
            }
            allPositions.addAll(positions);
        }
        floor = lowestRock + 2;
    }

    private void dropSandPart1() {
        Position currentPosition = new Position(500,0);
        while(currentPosition.y < lowestRock) {
            Position below = new Position(currentPosition.x, currentPosition.y+1);
            Position belowLeft = new Position(currentPosition.x-1, below.y);
            Position belowRight = new Position(currentPosition.x+1, below.y);
            if (!allPositions.contains(below)) {
                currentPosition = below;
            } else if (!allPositions.contains(belowLeft)) {
                currentPosition = belowLeft;
            } else if (!allPositions.contains(belowRight)) {
                currentPosition = belowRight;
            } else {
                allPositions.add(currentPosition);
                sandCounter++;
                break;
            }
        }
    }

    private void dropSandPart2() {
        Position currentPosition = new Position(500,0);
        while(true) {
            Position below = new Position(currentPosition.x, currentPosition.y+1);
            Position belowLeft = new Position(currentPosition.x-1, below.y);
            Position belowRight = new Position(currentPosition.x+1, below.y);
            if (below.y == floor) {
                allPositions.add(currentPosition);
                sandCounter++;
                break;
            } else if (!allPositions.contains(below)) {
                currentPosition = below;
            } else if (!allPositions.contains(belowLeft)) {
                currentPosition = belowLeft;
            } else if (!allPositions.contains(belowRight)) {
                currentPosition = belowRight;
            } else {
                allPositions.add(currentPosition);
                sandCounter++;
                break;
            }
        }
    }

    public static class Position {
        private final int x;
        private final int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Position{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
