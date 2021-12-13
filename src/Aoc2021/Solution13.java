package Aoc2021;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution13 {
    Set<Dot> dots = new HashSet<>();
    List<Fold> folds = new LinkedList<>();
    public static void main(String[] args) throws IOException {
        Solution13 solution = new Solution13();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        getInput();
        int iteration=0;
        for(Fold fold : folds) {
            applyFold(fold);
            System.out.println("Iteration: " + ++iteration + " length: " + dots.size());
        }
        int maxX=0;
        int maxY=0;
        for(Dot dot : dots) {
            maxX=Integer.max(maxX, dot.x);
            maxY=Integer.max(maxY, dot.y);
        }
        for(int i=0;i<=maxY;i++) {
            for(int j=0;j<=maxX;j++) {
                if (dots.contains(new Dot(j,i))) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println("");
        }
        System.out.println("Total score: " + 0);
    }

    private void applyFold(Fold fold) {
        Set<Dot> output = new HashSet<>();
        for(Dot dot : dots) {
            if (fold.axis.equals("x") && dot.x > fold.value) {
                int outX = (2*fold.value) - dot.x;
                output.add(new Dot(outX, dot.y));
            } else if (fold.axis.equals("y") && dot.y > fold.value) {
                int outY = (2*fold.value) - dot.y;
                output.add(new Dot(dot.x, outY));
            } else {
                output.add(dot);
            }
        }
        dots = output;
    }

    private void getInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("/Users/marc/workspace/adventofcode/resources/input13.txt"));
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            Matcher lineMatcher = lineRegex.matcher(line);
            Matcher lineMatcher2 = lineRegex2.matcher(line);
            if (lineMatcher.matches()) {
                int x = Integer.parseInt(lineMatcher.group(1));
                int y = Integer.parseInt(lineMatcher.group(2));
                dots.add(new Dot(x,y));
            } else if (lineMatcher2.matches()) {
                String axis = lineMatcher2.group(1);
                int value = Integer.parseInt(lineMatcher2.group(2));
                folds.add(new Fold(axis, value));
            } else if (!line.equals("")) {
                throw new RuntimeException("oops:" + line);
            }
        }
        scanner.close();
    }

    public class Fold{
        int value;
        String axis;
        public Fold(String axis, int value) {
            this.axis=axis;
            this.value=value;
        }
    }

    public class Dot{
        int x;
        int y;
        public Dot(int x, int y) {
            this.x=x;
            this.y=y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Dot dot = (Dot) o;
            return x == dot.x && y == dot.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static Pattern lineRegex = Pattern.compile("([0-9]+)\\,([0-9]+)");
    private static Pattern lineRegex2 = Pattern.compile("fold along ([a-z]+)=([0-9]+)");

}

