package Aoc2020;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Solution19 {

    Map<Long, Rule> rules = new HashMap<>();
    List<String> inputs = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        Solution19 solution = new Solution19();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        getInput();
        int counter = 0;
        for(String entry : inputs) {
            char[] characters = entry.toCharArray();
            boolean result = process(characters);
            if (result) {
                counter++;
            }
        }
        System.out.println("Result : " + counter);
    }

    public boolean process(char[] input) {
       List<Integer> result = applyRule(input, 0, rules.get(0L));
       if (result.contains(input.length)) {
           System.out.println("input:" + new String(input) + " matches");
           return true;
       } else {
           System.out.println("input:" + new String(input) + " does not match");
           return false;
       }
    }

    public List<Integer> applyRule(final char[] input, final int inputIndex, final Rule currentRule) {
        if (inputIndex >= input.length) {
            return new ArrayList<>();
        }
        if (currentRule.matchedChar != null) {
            List<Integer> result = new ArrayList<>();
            if (input[inputIndex] == currentRule.matchedChar) {
                result.add(inputIndex+1);
            }
            return result;
        } else {
            List<Integer> totalOutputs = new ArrayList<>();
            for (List<Long> orRule : currentRule.componentRules) {
                List<Integer> inputIndexes = new ArrayList<>();
                inputIndexes.add(inputIndex);
                List<Integer> outputIndexes = new ArrayList<>();
                for(Long component : orRule) {
                    outputIndexes = new ArrayList<>();
                    for(Integer idx : inputIndexes) {
                        outputIndexes.addAll(applyRule(input, idx, rules.get(component)));
                    }
                    inputIndexes = outputIndexes;
                }
                totalOutputs.addAll(outputIndexes);
            }
            return totalOutputs;
        }
    }

    public class Rule {
        public Character matchedChar;
        public List<List<Long>> componentRules = new LinkedList<>();

        public void addSubRule(String subRule) {
            List<Long> component = new LinkedList<Long>();
            String[] otherRules = subRule.split(" ");
            for(String otherRule : otherRules) {
                component.add(Long.parseLong(otherRule));
            }
            componentRules.add(component);
        }
    }


    private void getInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("/Users/marc/Desktop/input19recursive.txt"));
        List<String> output = new ArrayList<>();
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(":")) {
                String[] bigParts = line.split(":");
                Long ruleId = Long.parseLong(bigParts[0]);
                Rule rule = getRule(bigParts[1]);
                rules.put(ruleId, rule);
            } else if (line.isEmpty()) {

            } else {
                inputs.add(line);
            }
        }
        scanner.close();
    }

    public Rule getRule(String input) {
        Rule r = new Rule();
        input = input.trim();
        if (input.contains("\"")) {
            r.matchedChar = input.charAt(1);
            return r;
        } else {
            String[] subRules = input.split("[|]");
            for(String subRule : subRules) {
                r.addSubRule(subRule.trim());
            }
        }
        return r;
    }

}