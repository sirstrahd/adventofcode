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

public class Solution5 {

    public static void main(String[] args) throws IOException {
        Solution5 solution = new Solution5();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        Map map = getInput();
        System.out.println("result: " + map.countOverlaps());
        // 20252 incorrect
    }



    private static Pattern lineRegex = Pattern.compile("([0-9]+)\\,([0-9]+) \\-\\> ([0-9]+)\\,([0-9]+)");

    private Map getInput() throws FileNotFoundException {
        List<Line> lines = new ArrayList<>();
        Scanner scanner = new Scanner(new FileInputStream("/Users/marc/workspace/adventofcode/resources/input5.txt"));
        int maxX=0,maxY=0;

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equals("")) {
                break;
            }
            Matcher lineMatcher = lineRegex.matcher(line);
            if (lineMatcher.matches()) {
                int x1 = Integer.parseInt(lineMatcher.group(1));
                int y1 = Integer.parseInt(lineMatcher.group(2));
                int x2 = Integer.parseInt(lineMatcher.group(3));
                int y2 = Integer.parseInt(lineMatcher.group(4));
                if (x1 > maxX) {
                    maxX = x1;
                }
                if (x2 > maxX) {
                    maxX = x2;
                }
                if (y1 > maxY) {
                    maxY = y1;
                }
                if (x2 > maxX) {
                    maxY = y2;
                }
                lines.add(new Line(x1,x2,y1,y2));
            } else {
                throw new RuntimeException("line doesnt match");
            }

        }

        scanner.close();
        Map map = new Map(maxX, maxY);
        fillPositions(map, lines);
        return map;
    }

    private void fillPositions(Map map, List<Line> lines) {
        for(Line line : lines) {
            map.addLine(line);
        }
    }

    public class Map {
        int[][] map;
        public Map(int sizeX, int sizeY) {
            this.map = new int[sizeX+1][sizeY+1];
        }
        public int countOverlaps() {
            int counter = 0;
            for(int i=0;i<map.length;i++) {
                for(int j=0;j<map[0].length;j++) {
                    if (map[i][j] > 1) {
                        System.out.println("overlap at " + i + " " + j + ":" + map[i][j]);
                        counter++;
                    }
                }
            }
            return counter;
        }
        public void addLine(Line line) {
            if (line.x1 == line.x2) {
                int start = Math.min(line.y1, line.y2);
                int end = Math.max(line.y1, line.y2);
                for(int i=start; i<=end;i++) {
                    map[line.x1][i]++;
                }

            } else if (line.y1 == line.y2){
                int start = Math.min(line.x1, line.x2);
                int end = Math.max(line.x1, line.x2);
                for(int i=start; i<=end;i++) {
                    map[i][line.y1]++;
                }
            } else {
                boolean xDirectionRight = line.x2 > line.x1;
                boolean yDirectionDown = line.y2 > line.y1;
                int currentX=line.x1;
                int currentY=line.y1;
                map[currentX][currentY]++;
                do {
                    if (xDirectionRight) {
                        currentX++;
                    } else {
                        currentX--;
                    }
                    if (yDirectionDown) {
                        currentY++;
                    } else {
                        currentY--;
                    }
                    map[currentX][currentY]++;
                } while(currentX!=line.x2 && currentY != line.y2);
            }
        }
    }

    public class Line {
        public int x1,x2,y1,y2;
        public Line(int x1,int x2,int y1,int y2) {
            this.x1=x1;
            this.x2=x2;
            this.y1=y1;
            this.y2=y2;
        }
    }
}

