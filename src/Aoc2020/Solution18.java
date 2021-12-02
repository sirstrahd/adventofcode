package Aoc2020;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Solution18 {

    public static void main(String[] args) throws IOException {
        Solution18 solution = new Solution18();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        List<String> input = getInput();
        long totalSum = 0L;
        for(String line : input) {
            Stack<Operation> stack = new Stack<>();
            List<Character> elements = getChars(line);
            int current = 0;
            while(current<elements.size()) {
                Character element = elements.get(current);
                if (Character.isDigit(element)) {
                    if (stack.empty() || stack.peek().operator=='(') {
                        Operation o = new Operation();
                        o.value = (long)Character.getNumericValue(element);
                        stack.push(o);
                    } else {
                        Operation oTop = stack.peek();
                        if (oTop.operator == '+') {
                            oTop.value += (long)Character.getNumericValue(element);
                            oTop.operator = null;
                        } else if (oTop.operator == '*') {
                            oTop.value *= (long)Character.getNumericValue(element);
                            oTop.operator = null;
                        }
                    }
                } else {
                    switch(element) {
                    case '+':
                        Operation oTop = stack.peek();
                        oTop.operator = '+';
                        break;
                    case '*':
                        oTop = stack.peek();
                        oTop.operator = '*';
                        break;
                    case '(':
                        Operation o = new Operation();
                        o.operator = '(';
                        stack.push(o);
                        break;
                    case ')':
                        oTop = stack.pop();
                        Long insideParenthesisValue = oTop.value;
                        Operation oParenthesis = stack.pop();
                        if (oParenthesis.operator != '(') {throw new RuntimeException("unexpected operator");}
                        while(!stack.empty() && stack.peek().operator != '(') {
                            oTop = stack.pop();
                            if (oTop.operator == '*') {
                                insideParenthesisValue*=oTop.value;
                            } else if (oTop.operator == '+') {
                                insideParenthesisValue+=oTop.value;
                            }
                        }
                        oTop.value =insideParenthesisValue;
                        oTop.operator = null;
                        stack.push(oTop);
                        break;
                    }
                }
                current++;
            }
            if (stack.size() != 1) {
                throw new RuntimeException("Unexpected stack size: " + stack.size());
            }
            totalSum+=stack.peek().value;

            System.out.println("stack: " + stack.peek().value);
        }
        System.out.println("totalSum: " + totalSum);
    }

    public class Operation {
        Long value;
        Character operator;
    }


    private List<Character> getChars(String line) {
        String[] splitLine = line.split(" ");
        List<Character> elements = new ArrayList<>();
        for(String element : splitLine) {
            for (char character : element.toCharArray()) {
                elements.add(character);
            }
        }
        return elements;
    }

    private List<String> getInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("/Users/marc/Desktop/input18.txt"));
        List<String> output = new ArrayList<>();
        while(scanner.hasNextLine()) {
            output.add(scanner.nextLine());
        }
        scanner.close();
        return output;
    }

}