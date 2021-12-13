package Aoc2021;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Solution10 {

    public static void main(String[] args) throws IOException {
        Solution10 solution = new Solution10();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        List<String> input = getInput();
        Long totalScore = 0L;
        for(String line : input) {
            LinkedList<Character> lifo = new LinkedList<Character>();
            int currentIndex=0;
            while(currentIndex < line.length()) {
                Character c = line.charAt(currentIndex);
                if (isOpeningCharacter(c)) {
                    lifo.addFirst(c);
                } else {
                    Character lastOpened = lifo.removeFirst();
                    if (!corresponds(lastOpened, c)) {
                        totalScore+=getScore(c);
                        break;
                    }
                }
                currentIndex++;
            }
        }
        System.out.println("Total score: " + totalScore);
    }

    private long getScore(Character c) {
        if (c==')') {
            return 3L;
        } else if (c==']') {
            return 57;
        } else if (c=='}') {
            return 1197;
        } else if (c=='>') {
            return 25137;
        } else {
            throw new RuntimeException("oops");
        }
    }
    private boolean corresponds(Character opened, Character closed) {
        return ((opened == '[' && closed == ']') || (opened=='(' && closed==')') || (opened=='{' && closed=='}')
        || (opened=='<' && closed=='>'));
    }

    private boolean isOpeningCharacter(Character c) {
        return c == '(' || c=='[' || c=='{' || c =='<';
    }


    private List<String> getInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("/Users/marc/workspace/adventofcode/resources/input10.txt"));
        List<String> inputs = new ArrayList<>();
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            inputs.add(line);
        }

        return inputs;
    }
}

