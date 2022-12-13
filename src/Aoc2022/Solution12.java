package Aoc2022;

import Common.InputParser;

import java.util.*;

public class Solution12 {
    public static void main(String[] args) throws Exception {
        Solution12 solution = new Solution12();
        solution.execute();
    }

    public void execute() throws Exception {
        List<String> input = InputParser.getLinesAsListOfStrings("resources/2022/input12.txt");
        Position[][] positions = transformInputIntoPositionMatrix(input);
        boolean anyDistanceHasChanged=true;
        while(anyDistanceHasChanged) {
            anyDistanceHasChanged=false;
            for(int i = 0; i< positions.length; i++) {
                for (int j = 0; j < positions[i].length; j++) {
                    Position currentPosition = positions[i][j];
                    for(Position newPosition : getPositionsToTry(positions,i,j)) {
                        if (!currentPosition.isValidMovement(newPosition) || currentPosition.isCloser(newPosition)) {
                            continue;
                        }
                        currentPosition.distance = newPosition.distance+1;
                        anyDistanceHasChanged=true;
                    }
                }
            }
        }
        System.out.println("Part 1 Distance: " + getMinimalPositionWithHeight(positions, List.of('S')).distance);
        System.out.println("Part 2 Distance: " + getMinimalPositionWithHeight(positions,List.of('S','a')).distance);
    }

    private List<Position> getPositionsToTry(Position[][] positions, int i, int j) {
        List<Position> result = new LinkedList<>();
        if (i>0) {
            result.add(positions[i-1][j]);
        }
        if (j>0) {
            result.add(positions[i][j-1]);
        }
        if (i<positions.length-1) {
            result.add(positions[i+1][j]);
        }
        if (j<positions[i].length-1) {
            result.add(positions[i][j+1]);
        }
        return result;
    }

    private Position getMinimalPositionWithHeight(Position[][] positions, List<Character> chars) {
        Position minPosition = null;
        for (Position[] positionLine : positions) {
            for (Position position : positionLine) {
                if (chars.contains(position.height)) {
                    if (minPosition == null || minPosition.distance > position.distance) {
                        minPosition = position;
                    }
                }
            }
        }
        return minPosition;
    }

    private static Position[][] transformInputIntoPositionMatrix(List<String> input) {
        Position[][] positions = new Position[input.size()][input.get(0).length()];
        for(int i = 0; i< input.size(); i++) {
            for(int j = 0; j< input.get(i).length(); j++) {
                Position position = new Position(input.get(i).charAt(j));
                if (position.height == 'E') {
                    position.distance=0;
                }
                positions[i][j] = position;
            }
        }
        return positions;
    }


    public static class Position {
        public Position(char height) {
            this.height = height;
        }

        int distance=Integer.MAX_VALUE-1;

        char height;

        private char getEffectiveHeight() {
            return switch(height) {
                case 'S' -> 'a';
                case 'E' -> 'z';
                default -> height;
            };
        }

        public boolean isValidMovement(Position newPosition) {
            return getEffectiveHeight() >= newPosition.getEffectiveHeight()-1;
        }

        public boolean isCloser(Position newPosition) {
            return newPosition.distance + 1 >= this.distance;
        }
    }
}
