package Aoc2021;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Solution4 {

    public static void main(String[] args) throws IOException {
        Solution4 solution = new Solution4();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        Game game = getInput();
        Long result = process(game);
        System.out.println("result: " + result);
    }

    public Long process(Game game) {
        for(int calledNumber : game.calledNumbers) {
            for(Board board : game.boards) {
                for(int i = 0; i < board.positions.length;i++) {
                    Position[] line = board.positions[i];
                    for (int j=0; j < line.length;j++) {
                        if (line[j].value == calledNumber) {
                            line[j].called=true;
                        }
                    }
                }
            }
            for(int i=0; i<game.boards.size(); i++) {
                Board board = game.boards.get(i);
                if (winner(board)) {
                    System.out.println("winner board: " + i);
                    return calculatePoints(board, calledNumber);
                }
            }
        }
        return 0L;
    }

    private Long calculatePoints(Board board, int calledNumber) {
        Long sum = 0L;
        for(Position[] line : board.positions) {
            for(Position position : line) {
                if (!position.called) {
                    sum+=position.value;
                }
            }
        }
        return sum * calledNumber;
    }

    private boolean winner(Board board) {
        // Check horizontal
        for (Position[] line : board.positions) {
            boolean winner = true;
            for (Position position : line) {
                if (!position.called) {
                    winner = false;
                }
            }
            if (winner) {
                return true;
            }
        }
        for (int i = 0; i < board.positions[0].length; i++) {
            boolean winner = true;
            for (Position[] line : board.positions) {
                if (!line[i].called) {
                    winner = false;
                }
            }
            if (winner) {
                return true;
            }
        }
        return false;
    }

    private Game getInput() throws FileNotFoundException {
        List<Board> boards = new LinkedList<>();
        Scanner scanner = new Scanner(new FileInputStream("/Users/marc/workspace/adventofcode/resources/input4.txt"));
        String[] calledNumbersString = scanner.nextLine().trim().split(",");
        List<Integer> calledNumbers = new LinkedList<>();
        for (String number: calledNumbersString) {
            calledNumbers.add(Integer.parseInt(number));
        }
        scanner.nextLine();
        Position[][] positions;
        while(scanner.hasNextLine()) {
            String nextLine = scanner.nextLine().trim();
            List<Integer[]> lines = new LinkedList<Integer[]>();
            boolean somethingThere = false;
            while(!nextLine.equals("")) {
                somethingThere = true;
                String[] parts = nextLine.split("\\s+");
                Integer[] values = new Integer[parts.length];
                for(int i=0;i<parts.length;i++) {
                    values[i] = Integer.parseInt(parts[i]);
                }
                lines.add(values);
                if (scanner.hasNextLine()) {
                    nextLine = scanner.nextLine().trim();
                } else {
                    nextLine = "";
                }
            }
            if (somethingThere) {
                positions = new Position[lines.size()][lines.get(0).length];
                for (int i = 0; i < lines.size(); i++) {
                    Integer[] line = lines.get(i);
                    positions[i] = new Position[line.length];
                    for (int j = 0; j < line.length; j++) {
                        positions[i][j] = new Position(line[j]);
                    }
                }
                boards.add(new Board(positions));
            }
        }
        scanner.close();

        return new Game(boards, calledNumbers);
    }

    public class Position {
        public int value;
        public boolean called = false;
        public Position(int input) {
            this.value = input;
        }
    }

    public class Board {
        public Position[][] positions;
        public Board(Position[][] positions) {
            this.positions = positions;
        }
    }

    public class Game {
        public List<Board> boards;
        public List<Integer> calledNumbers;
        public Game(List<Board> boards, List<Integer> calledNumbers) {
            this.boards = boards;
            this.calledNumbers = calledNumbers;
        }
    }
}
