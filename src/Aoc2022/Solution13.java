package Aoc2022;

import Common.InputParser;

import java.util.*;
import java.util.stream.Collectors;

public class Solution13 {
    public static void main(String[] args) throws Exception {
        Solution13 solution = new Solution13();
        solution.execute();
    }

    public static class Element {
    }

    public static class NodeElement extends Element {
        List<Element> list = new LinkedList<>();

        @Override
        public String toString() {
            String result = list.stream().map(Object::toString).collect(Collectors.joining(","));
            return "["  +result+ "]";
        }
    }

    public static class LeafElement extends Element {
        int value;

        @Override
        public String toString() {
            return Integer.toString(value);
        }

        public LeafElement(int value) {
            this.value=value;
        }
    }

    public void execute() throws Exception {
        List<String> input = InputParser.getLinesAsListOfStrings("resources/2022/input13.txt");
        part1(input);
        part2(input);
    }

    private static void part2(List<String> input) {
        String firstSeparator = "[[2]]";
        String secondSeparator = "[[6]]";
        List<String> filteredInput = input.stream().filter(x -> !x.isBlank()).collect(Collectors.toList());
        filteredInput.add(firstSeparator);
        filteredInput.add(secondSeparator);
        List<Element> sortedInput = filteredInput.stream().map(Solution13::getElementFrom).sorted(new ElementComparator()).toList();
        int result = 1;
        for(int i=0;i<sortedInput.size();i++) {
            if (sortedInput.get(i).toString().equals(firstSeparator) || sortedInput.get(i).toString().equals(secondSeparator)) {
                result = result * (i+1);
            }
        }
        System.out.println("Part 2 result is: " + result);
    }

    private static void part1(List<String> input) {
        int response = 0;
        int i=0;
        while(i < input.size()) {
            Element firstElement = getElementFrom(input.get(i));
            Element secondElement = getElementFrom(input.get(i +1));
            int result = compare(firstElement, secondElement);
            boolean correct = result != 1;
            if (correct) {
                response += ((i /3)+1);
            }
            System.out.println("Pair " + i + " result: " + result + "order is " + correct);
            i = i +3;
        }
        System.out.println("Part 1 result is " + response);
    }

    public static class ElementComparator implements Comparator<Element> {
        @Override
        public int compare(Element o1, Element o2) {
            return Solution13.compare(o1, o2);
        }
    }
    /*
    Returns -1 if the first one is smaller, 0 if equal, +1 if the second one is smaller (wrong order)
     */
    public static int compare(Element firstElement, Element secondElement) {
        if (firstElement instanceof LeafElement && secondElement instanceof LeafElement) {
            return Integer.compare(((LeafElement)firstElement).value, ((LeafElement)secondElement).value);
        } else if (firstElement instanceof LeafElement) {
            NodeElement convertedFirst = new NodeElement();
            convertedFirst.list.add(firstElement);
            return compare(convertedFirst, secondElement);
        } else if (secondElement instanceof LeafElement) {
            NodeElement convertedSecond = new NodeElement();
            convertedSecond.list.add(secondElement);
            return compare(firstElement, convertedSecond);
        } else {
            List<Element> firstElements = ((NodeElement) firstElement).list;
            List<Element> secondElements =( (NodeElement) secondElement).list;
            int i=0;
            while(i<firstElements.size() && i<secondElements.size()) {
                int result = compare(firstElements.get(i), secondElements.get(i));
                if (result != 0) {
                    return result;
                }
                i++;
            }
            if (i >= firstElements.size() && i >= secondElements.size()) {
                return 0;
            } else if (i < firstElements.size()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    public static Element getElementFrom(String characters) {
        Stack<NodeElement> stack = new Stack<>();
        int i=0;
        while(i<characters.length()) {
            char c = characters.charAt(i);
            if (c == '[') {
                stack.push(new NodeElement());
            } else if (c == ']') {
                NodeElement e = stack.pop();
                if (stack.isEmpty()) {
                    return e;
                } else {
                    stack.peek().list.add(e);
                }
            } else if (Character.isDigit(characters.charAt(i))) {
                int end = i;
                while(Character.isDigit(characters.charAt(end))) {
                    end++;
                }
                int value = Integer.parseInt(characters.substring(i,end));
                stack.peek().list.add(new LeafElement(value));
                i=end-1;
            }
            i++;
        }
        throw new RuntimeException("should have returned by now");
    }
}
