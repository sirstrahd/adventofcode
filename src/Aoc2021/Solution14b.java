package Aoc2021;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution14b {

    private Map<String, Character> rules = new HashMap<>();
    private Map<String, Long> polymer = new HashMap<>();
    private Character firstChar = null;


    public static void main(String[] args) throws IOException {
        Solution14b solution = new Solution14b();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        getInput();
        for(int step=0;step<40;step++) {
            executeStep();
        }
        long maxCount = 0L;
        long minCount = Long.MAX_VALUE;
        Map<Character, Long> counts = new HashMap<>();
        for(String c : polymer.keySet()) {
            long count = polymer.getOrDefault(c, 0L);
            counts.put(c.charAt(1), count + counts.getOrDefault(c.charAt(1),0L));
        }
        counts.put(firstChar, counts.getOrDefault(firstChar, 0L) + 1);
        for(Character c : counts.keySet()) {
            long count = counts.get(c);
            if (count < minCount) {
                minCount = count;
            }
            if (count > maxCount) {
                maxCount = count;
            }
        }
        // guessed 2188189693529
        System.out.println("Total score: " + (maxCount - minCount));
    }

    private void executeStep() {
        Map<String, Long> output = new HashMap<>();
        for(String key : polymer.keySet()) {
            Character insertion = rules.get(key);
            if (insertion != null) {
                String outputKey1 = new StringBuilder().append(key.charAt(0)).append(insertion).toString();
                String outputKey2 = new StringBuilder().append(insertion).append(key.charAt(1)).toString();
                output.put(outputKey1, polymer.get(key)+output.getOrDefault(outputKey1,0L));
                output.put(outputKey2, polymer.get(key)+output.getOrDefault(outputKey2,0L));
            } else {
                // leave it as it is
                output.put(key, polymer.get(key)+output.getOrDefault(key,0L));
            }
        }
        polymer = output;
    }

    private void getInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("/Users/marc/workspace/adventofcode/resources/input14.txt"));
        String polymerTemplate = scanner.nextLine().trim();
        firstChar = polymerTemplate.charAt(0);
        for(int i=0;i<polymerTemplate.length()-1;i++) {
            String key = new StringBuilder().append(polymerTemplate.charAt(i)).append(polymerTemplate.charAt(i+1)).toString();
            polymer.put(key, polymer.getOrDefault(key,0L)+1);
        }
        scanner.nextLine();
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            Matcher lineMatcher = lineRegex.matcher(line);
            if (lineMatcher.matches()) {
                Character leftChar = lineMatcher.group(1).charAt(0);
                Character rightChar = lineMatcher.group(2).charAt(0);
                String key = new StringBuilder().append(leftChar).append(rightChar).toString();
                Character insertion = lineMatcher.group(3).charAt(0);
                rules.put(key, insertion);
             } else {
                throw new RuntimeException("oops:" + line);
            }
        }
        scanner.close();
    }
    private static Pattern lineRegex = Pattern.compile("([A-Z])([A-Z]) \\-\\> ([A-Z])");

}

