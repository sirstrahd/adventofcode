package Aoc2021;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution17 {

    Integer leftX ;
    Integer rightX;
    Integer botY ;
    Integer topY;

    private static final String INPUTFILE = "/Users/marc/workspace/adventofcode/resources/input17.txt";

    public static void main(String[] args) throws IOException {
        Solution17 solution = new Solution17();
        solution.execute();
    }

    public Set<Dot> execute() throws FileNotFoundException {
        getInput();

        int absY = Math.abs(botY) - 1;
        int result = (absY*(absY+1))/2;

        System.out.println("Max Y:" + result);

        Set<Dot> results = new HashSet<>();
        for(int ySpeedTest = botY; ySpeedTest<=-botY;ySpeedTest++) {
            for(int xSpeedTest = 1;xSpeedTest<=rightX;xSpeedTest++) {
                int x=0;
                int y=0;
                int ySpeed = ySpeedTest;
                int xSpeed = xSpeedTest;
                while(x<=rightX && y>=botY) {
                    if (x>=leftX && y<=topY) {
                        results.add(new Dot(xSpeedTest,ySpeedTest));
                    }
                    x+=xSpeed;
                    y+=ySpeed;
                    ySpeed--;
                    if (xSpeed != 0) {
                        xSpeed--;
                    }
                }
            }
        }
        System.out.println("Amount of solutions: " + results.size());
        return results;
    }

    private void getInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(INPUTFILE));
        String line = scanner.nextLine().trim();
        scanner.close();
        Matcher lineMatcher = lineRegex.matcher(line);

        if (lineMatcher.matches()) {
             leftX = Integer.parseInt(lineMatcher.group(1));
             rightX = Integer.parseInt(lineMatcher.group(2));
             botY = Integer.parseInt(lineMatcher.group(3));
             topY = Integer.parseInt(lineMatcher.group(4));
        } else {
            throw new RuntimeException("oops:" + line);
        }
        scanner.close();
    }

    public class Dot {
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
            return x == dot.x &&
                    y == dot.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static Pattern lineRegex = Pattern.compile("target area: x=(\\-*[0-9]+)..(\\-*[0-9]+), y=(\\-*[0-9]+)..(\\-*[0-9]+)");


}

