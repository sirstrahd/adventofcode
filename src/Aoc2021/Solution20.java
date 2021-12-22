package Aoc2021;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution20 {

    private static final String HOME = System.getenv("HOME");
    private static final String INPUTFILE = HOME + "/workspace/adventofcode/resources/input20.txt";
    Map<Integer, Boolean> algorithm = new HashMap<>();
    Map<Pair, Boolean> map = new HashMap<>();
    Boolean currentDefault = false;
    int minX=0;
    int minY=0;
    int maxX=0;
    int maxY=0;

    List<String> lines = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        Solution20 solution = new Solution20();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        int result = 0;
        getInput();
        executeSteps(2);
        for(Boolean value : map.values()) {
            if (value) {
                result++;
            }
        }
        // 35
        System.out.println("count: " + result);
    }

    private void executeSteps(int n) {
        for(int i=0;i<n;i++) {
            executeStep();
        }
    }

    private void executeStep() {
        Map<Pair, Boolean> output = new HashMap<>();
        Set<Pair> pairs = getNewPairs();
        pairs.addAll(map.keySet());
        for(Pair pair: pairs) {
            output.put(pair, calculateOutputValue(pair));
        }
        map = output;
        currentDefault = calculateNewDefault();
    }

    private Boolean calculateOutputValue(Pair pair) {
        int positionInAlgo = 0b00;
        for(int i=-1;i<2;i++) {
            for(int j=-1;j<2;j++) {
                if (getCurrentValue(new Pair(pair.x+i, pair.y+j))) {
                    positionInAlgo = positionInAlgo*2 + 0b01;
                } else {
                    positionInAlgo = positionInAlgo*2 + 0b00;
                }
            }
        }
        return algorithm.get(positionInAlgo);
    }

    private Boolean calculateNewDefault() {
        int positionInAlgo = 0b00;
        for(int i=-1;i<2;i++) {
            for(int j=-1;j<2;j++) {
                if (currentDefault) {
                    positionInAlgo = positionInAlgo*2 + 0b01;
                } else {
                    positionInAlgo = positionInAlgo*2 + 0b00;
                }
            }
        }
        return algorithm.get(positionInAlgo);
    }

    private Boolean getCurrentValue(Pair pair) {
        Boolean result = map.get(pair);
        if (result == null) {
            return currentDefault;
        } else {
            return result;
        }
    }

    private Set<Pair> getNewPairs() {
        Set<Pair> newPairs = new HashSet<>();
        Set<Pair> keys = map.keySet();
        for(Pair key: keys) {
            List<Pair> neighbours = getNeighbours(key);
            for(Pair pair : neighbours) {
                if (!map.containsKey(pair)) {
                    newPairs.add(pair);
                }
            }
        }
        return newPairs;
    }

    private List<Pair> getNeighbours(Pair key) {
        List<Pair> neighbours = new LinkedList<>();
        neighbours.add(new Pair(key.x-1,key.y-1));
        neighbours.add(new Pair(key.x,key.y-1));
        neighbours.add(new Pair(key.x+1,key.y-1));

        neighbours.add(new Pair(key.x-1,key.y));
        neighbours.add(new Pair(key.x+1,key.y));

        neighbours.add(new Pair(key.x-1,key.y+1));
        neighbours.add(new Pair(key.x,key.y+1));
        neighbours.add(new Pair(key.x+1,key.y+1));
        return neighbours;
    }

    private void getInput() throws FileNotFoundException {
        Scanner inputScanner = new Scanner(new FileInputStream(INPUTFILE));

        String line = inputScanner.nextLine().trim();
        for(int i=0;i<line.length();i++) {
            algorithm.put(i, line.charAt(i) == '#');
        }
        inputScanner.nextLine();
        while(inputScanner.hasNextLine()) {
            line = inputScanner.nextLine().trim();
            lines.add(line);
        }
        inputScanner.close();
        maxY = lines.size();
        maxX = lines.get(0).length();

        for(int i=0;i<maxY;i++) {
            for(int j=0;j<maxX;j++) {
                map.put(new Pair(i,j), lines.get(i).charAt(j) == '#');
            }
        }
    }

    public class Pair {
        private int x;
        private int y;

        public Pair(int x, int y) {
            this.x=x;
            this.y=y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return x == pair.x &&
                    y == pair.y;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }


}

