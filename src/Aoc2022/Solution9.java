package Aoc2022;

import Common.InputParser;

import java.util.*;
import java.util.stream.Collectors;

public class Solution9 {
    public Set<Position> visitedPositions = new HashSet<>();
    public ArrayList<Position> tailPositions;

    public void execute() throws Exception {
        List<Movement> input = InputParser.getLinesAsListOfStrings("resources/2022/input9.txt").stream().map(Movement::new).collect(Collectors.toList());
        generateTailOfLength(1);
        processMovements(input);
        generateTailOfLength(9);
        processMovements(input);
        // 2683 too high
    }

    private void generateTailOfLength(int length) {
        visitedPositions = new HashSet<>();
        tailPositions = new ArrayList<>();
        for (int i = 0; i <= length; i++) {
            tailPositions.add(new Position(0, 0));
        }
    }

    private void processMovements(List<Movement> input) {
        visitedPositions.add(tailPositions.get(tailPositions.size() - 1));
        for (Movement movement : input) {
            processMovement(movement);
        }
        System.out.println("Visited positions: " + visitedPositions.size());
    }

    private void processMovement(Movement movement) {
        Position newHeadPosition;
        for (int i = 0; i < movement.amount; i++) {
            newHeadPosition = switch (movement.direction) {
                case U -> new Position(tailPositions.get(0).x, tailPositions.get(0).y + 1);
                case D -> new Position(tailPositions.get(0).x, tailPositions.get(0).y - 1);
                case L -> new Position(tailPositions.get(0).x - 1, tailPositions.get(0).y);
                case R -> new Position(tailPositions.get(0).x + 1, tailPositions.get(0).y);
            };
            tailPositions.set(0, newHeadPosition);
            for (int j = 1; j < tailPositions.size(); j++) {
                processTailMovement(j);
            }
            visitedPositions.add(tailPositions.get(tailPositions.size() - 1));
            System.out.println("Adding visited position: " + tailPositions.get(tailPositions.size() - 1));
        }
    }

    private void processTailMovement(int element) {
        final Position headPosition = tailPositions.get(element - 1);
        final Position tailPosition = tailPositions.get(element);
        int xDistance = headPosition.x - tailPosition.x;
        int yDistance = headPosition.y - tailPosition.y;
        final Position newTailPosition;
        if (xDistance > 1 && yDistance >= 1 || xDistance >= 1 && yDistance > 1) {
            newTailPosition = new Position(tailPosition.x + 1, tailPosition.y + 1);
        } else if (xDistance > 1 && yDistance <= -1 || xDistance >= 1 && yDistance < -1) {
            newTailPosition = new Position(tailPosition.x + 1, tailPosition.y - 1);
        } else if (xDistance < -1 && yDistance >= 1 || xDistance <= -1 && yDistance > 1) {
            newTailPosition = new Position(tailPosition.x - 1, tailPosition.y + 1);
        } else if (xDistance < -1 && yDistance <= -1 || xDistance <= -1 && yDistance < -1) {
            newTailPosition = new Position(tailPosition.x - 1, tailPosition.y - 1);
        } else if (yDistance > 1) {
            newTailPosition = new Position(tailPosition.x, tailPosition.y + 1);
        } else if (yDistance < -1) {
            newTailPosition = new Position(tailPosition.x, tailPosition.y - 1);
        } else if (xDistance < -1) {
            newTailPosition = new Position(tailPosition.x - 1, tailPosition.y);
        } else if (xDistance > 1) {
            newTailPosition = new Position(tailPosition.x + 1, tailPosition.y);
        } else {
            newTailPosition = tailPosition;
        }
        tailPositions.set(element, newTailPosition);
    }


    public static void main(String[] args) throws Exception {
        Solution9 solution = new Solution9();
        solution.execute();
    }

    public enum MoveEnum {U, D, L, R}

    public static class Movement {

        final MoveEnum direction;
        final int amount;

        public Movement(String line) {
            String[] parts = line.split(" ");
            direction = MoveEnum.valueOf(parts[0]);
            amount = Integer.parseInt(parts[1]);
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
