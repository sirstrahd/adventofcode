package Aoc2021;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution14 {

    private Map<Rule, Character> rules = new HashMap<>();
    private List<Character> polymer = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Solution14 solution = new Solution14();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        getInput();
        for(int step=0;step<10;step++) {
            executeStep();
        }
        int maxCount = 0;
        int minCount = Integer.MAX_VALUE;
        Character max = null;
        Character min = null;
        Map<Character,Integer> counts = countElements();
        for(Character c : counts.keySet()) {
            int count = counts.get(c);
            if (count > maxCount) {
                maxCount = count;
            }
            if (count < minCount) {
                minCount = count;
            }
        }
        System.out.println("Total score: " + (maxCount - minCount));
    }

    private Map<Character, Integer> countElements() {
        Map<Character, Integer> counts = new HashMap<>();
        for(Character c : polymer) {
            counts.put(c, counts.getOrDefault(c,0)+1);
        }
        return counts;
    }

    private void executeStep() {
        int i=0;
        List<Character> output = new ArrayList<>();
        while(i<polymer.size()) {
            Character char1 = polymer.get(i);
            if (i == polymer.size()-1) {
                output.add(char1);
                break;
            }
            Character char2 = polymer.get(i+1);
            Character insertion = rules.get(new Rule(char1,char2));
            if (insertion==null) {
                output.add(char1);
            } else {
                output.add(char1);
                output.add(insertion);
            }
            i++;
        }
        polymer = output;
    }

    private void getInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("/Users/marc/workspace/adventofcode/resources/input14test.txt"));
        String polymerTemplate = scanner.nextLine().trim();
        for(Character c : polymerTemplate.toCharArray()) {
            polymer.add(c);
        }
        scanner.nextLine();
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            Matcher lineMatcher = lineRegex.matcher(line);
            if (lineMatcher.matches()) {
                Character leftChar = lineMatcher.group(1).charAt(0);
                Character rightChar = lineMatcher.group(2).charAt(0);
                Character insertion = lineMatcher.group(3).charAt(0);
                rules.put(new Rule(leftChar, rightChar), insertion);
             } else {
                throw new RuntimeException("oops:" + line);
            }
        }
        scanner.close();
    }
    public class Rule {
        public final Character leftChar;
        public final Character rightChar;

        public Rule(Character leftChar, Character rightChar) {
            this.leftChar = leftChar;
            this.rightChar = rightChar;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Rule rule = (Rule) o;
            return Objects.equals(leftChar, rule.leftChar) && Objects.equals(rightChar, rule.rightChar);
        }

        @Override
        public int hashCode() {
            return Objects.hash(leftChar, rightChar);
        }
    }
    private static Pattern lineRegex = Pattern.compile("([A-Z])([A-Z]) \\-\\> ([A-Z])");

}

