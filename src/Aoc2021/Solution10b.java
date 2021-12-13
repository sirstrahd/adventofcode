package Aoc2021;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Solution10b {

    public static void main(String[] args) throws IOException {
        Solution10b solution = new Solution10b();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        List<String> input = getInput();
        List<Long> scores = new LinkedList<>();
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
                        break;
                    }
                }
                currentIndex++;
            }
            if (currentIndex == line.length()) {
                // incomplete line
                long score = 0;
                while(!lifo.isEmpty()) {
                    Character c = lifo.removeFirst();
                    long charScore = getScore(c);
                    score = score*5 + charScore;
                }
                scores.add(score);
            }
        }
        scores.sort(new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                if (o1 > o2) {
                    return -1;
                } else {
                    return +1;
                }
            }
        });
        // 29677473446 too high
        System.out.println("Total score: " + scores.get(scores.size()/2));
    }

    private long getScore(Character c) {
        if (c=='(') {
            return 1L;
        } else if (c=='[') {
            return 2;
        } else if (c=='{') {
            return 3;
        } else if (c=='<') {
            return 4;
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

