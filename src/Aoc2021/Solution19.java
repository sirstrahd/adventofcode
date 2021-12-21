package Aoc2021;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution19 {

    private static final String INPUTFILE = "/Users/marc/workspace/adventofcode/resources/input19.txt";
    List<Point> ROTATIONS = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        Solution19 solution = new Solution19();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        ROTATIONS.add(new Point(1,1,1));
        ROTATIONS.add(new Point(1,1,-1));
        ROTATIONS.add(new Point(1,-1,1));
        ROTATIONS.add(new Point(1,-1,-1));
        ROTATIONS.add(new Point(-1,1,1));
        ROTATIONS.add(new Point(-1,1,-1));
        ROTATIONS.add(new Point(-1,-1,1));
        ROTATIONS.add(new Point(-1,-1,-1));

        List<BeaconScanner> input = getInput();
        Set<Overlap> overlaps = new HashSet<>();
        LinkedList<Integer> stackToProcess = new LinkedList<>();
        LinkedList<Integer> processed = new LinkedList<>();
        stackToProcess.add(0);
        while(!stackToProcess.isEmpty()) {
            int i = stackToProcess.removeFirst();
            if (!processed.contains(i)) {
                for (int j = 0; j < input.size(); j++) {
                    if (i==j) {
                        continue;
                    }
                    if (input.get(i).rotations.size() == 1 && input.get(j).rotations.size() == 1) {
                        continue;
                    }
                    Overlap overlap = findOverlap(input.get(i), input.get(j));
                    if (overlap != null) {
                        System.out.println("Scanner " + overlap.p1 + " overlaps with scanner " + overlap.p2);
                        overlaps.add(overlap);
                        BeaconScanner b1 = input.get(overlap.p1);
                        List<Point> r1 = b1.rotations.get(overlap.i);
                        BeaconScanner b2 = input.get(overlap.p2);
                        List<Point> r2 = b2.rotations.get(overlap.j);
                        b1.rotations.clear();
                        b1.rotations.add(r1);
                        b2.rotations.clear();
                        b2.rotations.add(r2);
                        stackToProcess.addLast(b2.number);
                    }
                }
                processed.add(i);
            }
        }
        System.out.println("Calculating area...");
        Map<Integer, Point> relativeScannerPositions = new HashMap<>();
        relativeScannerPositions.put(0, new Point(0,0,0));
        while(true) {
            boolean informedAtLeastOne = false;
            for(Overlap overlap: overlaps) {
                Point p1 = relativeScannerPositions.get(overlap.p1);
                Point p2 = relativeScannerPositions.get(overlap.p2);
                if (p1 != null && p2 == null) {
                    informedAtLeastOne = true;
                    relativeScannerPositions.put(overlap.p2, new Point(p1.x+overlap.scanner2Pos.x,p1.y+overlap.scanner2Pos.y,p1.z+overlap.scanner2Pos.z));
                }
            }
            if (!informedAtLeastOne) {
                break;
            }
        }

        Set<Point> beacons = placeInSameMap( input, relativeScannerPositions);

        int maxManhattan = 0;
        for(Point pos1 : relativeScannerPositions.values()) {
            for(Point pos2 : relativeScannerPositions.values()) {
                int distance = Math.abs(pos1.x - pos2.x) + Math.abs(pos1.y - pos2.y) + Math.abs(pos1.z - pos2.z);
                maxManhattan = Math.max(maxManhattan, distance);
            }
        }
        System.out.println("Number of beacons: " + beacons.size());
        System.out.println("maxManhattan: " + maxManhattan);
        // 447
    }

    private Set<Point> placeInSameMap(List<BeaconScanner> input, Map<Integer, Point> relativeScannerPositions) {
        Set<Point> result = new HashSet<>();
        for(BeaconScanner beacon : input) {
            List<Point> points = beacon.rotations.get(0);
            Point scannerPosition = relativeScannerPositions.get(beacon.number);
            for(Point point : points) {
                result.add(new Point(point.x + scannerPosition.x, point.y + scannerPosition.y,point.z + scannerPosition.z));
            }
        }
        return result;
    }

    public class Overlap {
        int p1;
        int p2;
        int i;
        int j;
        Point scanner2Pos;
        public Overlap(int p1, int p2, int i, int j, Point scanner2Pos) {
            this.i=i;
            this.j=j;
            this.p1=p1;
            this.p2=p2;
            this.scanner2Pos=scanner2Pos;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Overlap overlap = (Overlap) o;
            return p1 == overlap.p1 &&
                    p2 == overlap.p2 &&
                    i == overlap.i &&
                    j == overlap.j;
        }

        @Override
        public int hashCode() {
            return Objects.hash(p1, p2, i, j);
        }
    }

    private Overlap findOverlap(BeaconScanner s1, BeaconScanner s2) {
        for(int i=0;i<s1.rotations.size();i++) {
            for(int j=0;j<s2.rotations.size();j++) {
                Point scanner2Pos = hasEnoughCommonPoints(s1.rotations.get(i), s2.rotations.get(j));
                if (scanner2Pos != null) {
                    return new Overlap(s1.number, s2.number, i,j, scanner2Pos);
                }
            }
        }
        return null;
    }

    private Point hasEnoughCommonPoints(List<Point> r1, List<Point> r2) {
        for(Point p1 : r1) {
            for(Point p2: r2) {
                Point scanner2Position = new Point(p1.x-p2.x, p1.y-p2.y,p1.z-p2.z);
                if (checkScanner2Position(scanner2Position, r1, r2)) {
                    return scanner2Position;
                }
            }
        }
        return null;
    }

    private boolean checkScanner2Position(Point s2Pos, List<Point> r1, List<Point> r2) {
        int counter = 0;
        for(Point p1 : r1) {
            for(Point p2 : r2) {
                Point pCandidate = new Point(s2Pos.x + p2.x, s2Pos.y + p2.y, s2Pos.z + p2.z);
                if (p1.equals(pCandidate)) {
                    counter++;
                }
            }
        }
        return counter >= 12;
    }

    private List<BeaconScanner> getInput() throws FileNotFoundException {
        List<BeaconScanner> result = new ArrayList<>();
        Scanner inputScanner = new Scanner(new FileInputStream(INPUTFILE));
        while(inputScanner.hasNextLine()) {
            String line = inputScanner.nextLine().trim();
            BeaconScanner scanner = new BeaconScanner();
            Matcher titleMatcher = titleRegex.matcher(line);
            if (titleMatcher.matches()) {
                int scannerNumber = Integer.parseInt(titleMatcher.group(1));
                scanner.number=scannerNumber;
            } else {
                throw new RuntimeException("oops");
            }
            line = inputScanner.nextLine().trim();
            while(!line.equals("")) {
                 Matcher lineMatcher = lineRegex.matcher(line);
                if (lineMatcher.matches()) {
                    int x = Integer.parseInt(lineMatcher.group(1));
                    int y = Integer.parseInt(lineMatcher.group(2));
                    int z = Integer.parseInt(lineMatcher.group(3));
                    Point p = new Point(x,y,z);
                    scanner.coordinates.add(p);
                } else {
                    throw new RuntimeException("oops");
                }
                if (inputScanner.hasNextLine()) {
                    line = inputScanner.nextLine().trim();
                } else {
                    break;
                }
            }
            scanner.calculateRotations();
            result.add(scanner);
        }
        inputScanner.close();
        return result;
    }

    public class BeaconScanner {
        int number;
        public List<Point> coordinates = new LinkedList<>();
        public List<List<Point>> rotations = new LinkedList<>();

        public void calculateRotations() {
            // xyz
            addRotationsForCoordinates();
            switchYZ();
            // xzy
            addRotationsForCoordinates();
            switchYZ();
            switchXZ();
            //zyx
            addRotationsForCoordinates();
            switchYZ();
            //zxy
            addRotationsForCoordinates();
            switchXZ();
            // yxz
            addRotationsForCoordinates();
            switchYZ();
            // yzx
            addRotationsForCoordinates();
            switchYZ();

        }

        void switchYZ() {
            List<Point> output = new LinkedList<>();
            for(Point p : coordinates) {
                output.add(new Point(p.x,p.z,p.y));
            }
            coordinates = output;
        }

        void switchXY() {
            List<Point> output = new LinkedList<>();
            for(Point p : coordinates) {
                output.add(new Point(p.y,p.x,p.z));
            }
            coordinates = output;
        }

        void switchXZ() {
            List<Point> output = new LinkedList<>();
            for(Point p : coordinates) {
                output.add(new Point(p.z,p.y,p.x));
            }
            coordinates = output;
        }

        void addRotationsForCoordinates() {
            for(Point rotation : ROTATIONS) {
                LinkedList currentRotation = new LinkedList<>();
                rotations.add(currentRotation);
                for(Point point : coordinates) {
                    currentRotation.add(point.multiply(rotation));
                }
            }
        }
    }



    public class Point {
        public int x;
        public int y;
        public int z;
        public Point(int x, int y, int z) {
            this.x=x;
            this.y=y;
            this.z=z;
        }

        public Point multiply(Point p) {
            return new Point(x*p.x, y*p.y,z*p.z);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y &&
                    z == point.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
    }


    private static Pattern titleRegex = Pattern.compile("--- scanner ([0-9]+) ---");

    private static Pattern lineRegex = Pattern.compile("(\\-*[0-9]+),(\\-*[0-9]+),(\\-*[0-9]+)");


}

