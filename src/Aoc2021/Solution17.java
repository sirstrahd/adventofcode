package Aoc2021;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
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

    public void execute() throws FileNotFoundException {
        getInput();

        int absY = Math.abs(botY) - 1;
        int result = (absY*(absY+1))/2;

        System.out.println("Max Y:" + result);


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
    }

    private static Pattern lineRegex = Pattern.compile("target area: x=(\\-*[0-9]+)..(\\-*[0-9]+), y=(\\-*[0-9]+)..(\\-*[0-9]+)");


}

