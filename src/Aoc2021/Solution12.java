package Aoc2021;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution12 {

    Set<String> nodes = new HashSet<>();
    Map<String, List<String>> edges = new HashMap<>();

    public static void main(String[] args) throws IOException {
        Solution12 solution = new Solution12();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        getInput();

        int count = getAllPaths();

        System.out.println("Total score: " + count);
    }

    private int getAllPaths() {
        LinkedList<String> currentRoute = new LinkedList<>();
        Set<String> visitedNodes = new HashSet<>();
        currentRoute.addLast("start");
        visitedNodes.add("start");
        return visit("start", currentRoute, visitedNodes);
    }

    private int visit(String current, LinkedList<String> currentRoute, Set<String> visitedNodes) {
        int routes = 0;
        if (current.equals("end")) {
            return 1;
        }
        for(String target : edges.get(current)) {
            if (target.equals(target.toUpperCase())) {
                currentRoute.addLast(target);
                routes+=visit(target, currentRoute, visitedNodes);
                currentRoute.removeLast();
            } else if (!visitedNodes.contains(target)) {
                visitedNodes.add(target);
                currentRoute.addLast(target);
                routes += visit(target, currentRoute, visitedNodes);
                visitedNodes.remove(target);
                currentRoute.removeLast();
            }
        }

        return routes;
    }

    private void getInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("/Users/marc/workspace/adventofcode/resources/input12.txt"));
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equals("")) {
                break;
            }
            Matcher lineMatcher = lineRegex.matcher(line);
            if (lineMatcher.matches()) {
                String start = lineMatcher.group(1);
                String end = lineMatcher.group(2);
                List<String> value = edges.get(start);
                if (value == null) {
                    value = new LinkedList<>();

                }
                value.add(end);
                edges.put(start, value);
                value = edges.get(end);
                if (value == null) {
                    value = new LinkedList<>();

                }
                value.add(start);
                edges.put(end, value);
                nodes.add(start);
                nodes.add(end);
            } else {
                throw new RuntimeException("line doesnt match");
            }
        }

        scanner.close();
    }

    private static Pattern lineRegex = Pattern.compile("([a-zA-Z]+)\\-([a-zA-Z]+)");
}

