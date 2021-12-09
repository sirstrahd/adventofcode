package Aoc2021;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Solution9b {

    public static void main(String[] args) throws IOException {
        Solution9b solution = new Solution9b();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        int[][] map = getInput();
        List<Position> lowPoints = new ArrayList<>();
        for(int i=0; i< map.length;i++) {
            for(int j=0; j<map[0].length;j++) {
                if (isLow(map, new Position(i, j))) {
                    lowPoints.add(new Position(i,j));
                }
            }
        }
        List<Set<Position>> basins = new ArrayList<>();
        for(Position lp : lowPoints) {
            Set<Position> basin = getBasinForLowpoint(lp, map);
            basins.add(basin);

        }
        basins.sort(new Comparator<Set<Position>>() {
            @Override
            public int compare(Set<Position> o1, Set<Position> o2) {
                return o2.size() - o1.size();
            }
        });
        System.out.println("map: " + basins);
        System.out.println("map: " + basins.get(0).size()*basins.get(1).size()*basins.get(2).size());

    }

    private Set<Position> getBasinForLowpoint(Position lp, int[][] map) {
        List<Position> candidates = new LinkedList<>();
        Set<Position> basin = new HashSet<>();
        Set<Position> explored = new HashSet<>();
        candidates.add(lp);
        explore(candidates, basin, explored, map);
        return basin;
    }

    private void explore(List<Position> candidates, Set<Position> basin,Set<Position> explored , int[][] map ) {
        System.out.println("Exploring lp");
        while (!candidates.isEmpty()) {
            Position position = candidates.remove(0);
            basin.add(position);
            explored.add(position);
            System.out.println("Checking position " + position);
            Position left = new Position(position.x, position.y - 1);
            Position right = new Position(position.x, position.y + 1);
            Position top = new Position(position.x - 1, position.y);
            Position below = new Position(position.x + 1, position.y);
            if (!explored.contains(left) && leftNeighbour(map, position) < 9) {
                candidates.add(left);
            }
            explored.add(left);
            if (!explored.contains(right) && rightNeighbour(map, position) < 9) {
                candidates.add(right);
            }
            explored.add(right);
            if (!explored.contains(top) && topNeighbour(map, position) < 9) {
                candidates.add(top);
            }
            explored.add(top);
            if (!explored.contains(below) && bottomNeighbour(map, position) < 9) {
                candidates.add(below);
            }
            explored.add(below);
        }
    }

    private boolean isLow(int[][] map, Position pos){
        return (map[pos.x][pos.y] < leftNeighbour(map, pos) && map[pos.x][pos.y] < rightNeighbour(map, pos)
                && map[pos.x][pos.y] < topNeighbour(map, pos)&& map[pos.x][pos.y] < bottomNeighbour(map, pos));

    }

    private int leftNeighbour(int[][] map, Position pos){
        if (pos.y == 0) {
            return 10;
        } else {
            return map[pos.x][pos.y-1];
        }
    }

    private int rightNeighbour(int[][] map, Position pos){
        if (pos.y == map[0].length - 1) {
            return 10;
        } else {
            return map[pos.x][pos.y+1];
        }
    }

    private int topNeighbour(int[][] map, Position pos){
        if (pos.x == 0) {
            return 10;
        } else {
            return map[pos.x-1][pos.y];
        }
    }

    private int bottomNeighbour(int[][] map, Position pos){
        if (pos.x == map.length - 1) {
            return 10;
        } else {
            return map[pos.x+1][pos.y];
        }
    }

    private int[][] getInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("/Users/marc/workspace/adventofcode/resources/input9.txt"));
        List<String> inputs = new ArrayList<>();
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            inputs.add(line);
        }
        scanner.close();
        int[][] map = new int[inputs.size()][inputs.get(0).length()];
        int i=0;
        for(String input : inputs) {
            int j=0;
            for(Character c : input.toCharArray()) {
                map[i][j] = Character.getNumericValue(c);
                j++;
            }
            i++;
        }
        return map;
    }

    public class Position {
        int x;
        int y;
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x &&
                    y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}

