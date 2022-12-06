package Aoc2022;

import Common.InputParser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Solution5 {
    public static void main(String[] args) throws Exception {
        Solution5 solution = new Solution5();
        solution.execute();
    }
    public void execute() throws Exception {
        List<String> input =  InputParser.getLinesAsListOfStrings("resources/2022/input5.txt");
        List<List<String>> inputs = InputParser.splitByEmptyLines(input);
        if (inputs.size()>2) {
            throw new RuntimeException("too many input blocks...?");
        }
        Containers containers = new Containers(inputs.get(0));
        List<String> movements = inputs.get(1);
        containers.processMovementsPart1(movements);
        containers.printTopCrates();
        containers = new Containers(inputs.get(0));
        containers.processMovementsPart2(movements);
        containers.printTopCrates();

    }


    public class Containers {
        final Deque<Character> piles[];

        public Containers(List<String> initialState) {
            String[] splitLastLine = initialState.get(initialState.size() - 1).split(" ");
            int numberOfContainers = Integer.parseInt(splitLastLine[splitLastLine.length-1]);
            piles = new Deque[numberOfContainers];
            for(int i=0;i<numberOfContainers;i++) {
                piles[i] = new LinkedList<>();
            }
            for(int i=0; i < initialState.size()-1; i++) {
                String currentLine = initialState.get(i);
                int currentPos = 0;
                while(4*currentPos + 1 < currentLine.length()) {
                    Character c = currentLine.charAt(4*currentPos+1);
                    if (c != ' ') {
                        piles[currentPos].addLast(c);
                    }
                    currentPos++;
                }
            }
        }

        public void processMovementsPart1(List<String> movements) {
            for(String movement : movements) {
                movePart1(movement);
            }
        }

        public void processMovementsPart2(List<String> movements) {
            for(String movement : movements) {
                movePart2(movement);
            }
        }

        Pattern p = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");
        public void movePart1(String movement) {
            Matcher m = p.matcher(movement);
            m.find();
            int count = Integer.parseInt(m.group(1));
            int from = Integer.parseInt(m.group(2));
            int to = Integer.parseInt(m.group(3));
            System.out.println(movement);
            IntStream.range(0, count).forEach(i -> move1(to,from));
        }

        public void move1(int to, int from) {
            piles[to-1].addFirst(piles[from-1].removeFirst());
        }
        public void movePart2(String movement) {
            Matcher m = p.matcher(movement);
            m.find();
            int count = Integer.parseInt(m.group(1));
            int from = Integer.parseInt(m.group(2));
            int to = Integer.parseInt(m.group(3));
            System.out.println(movement);
            moveN(to,from, count);
        }

        private void moveN(int to, int from, int count) {
            for(int i=count-1; i>=0;i--) {
                piles[to-1].addFirst(((LinkedList<Character>)piles[from-1]).get(i));
            }
            IntStream.range(0, count).forEach(i -> piles[from-1].removeFirst());
        }

        public void printTopCrates() {
            for(Deque<Character> pile : piles) {
                System.out.print(pile.getFirst());
            }
            System.out.println("");
        }
    }
}
