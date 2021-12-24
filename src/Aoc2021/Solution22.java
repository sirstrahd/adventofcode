package Aoc2021;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution22 {

    private static final String INPUTFILE = System.getenv("HOME") + "/workspace/adventofcode/resources/input22.txt";
    private Set<Position> cubes = new HashSet<>();
    private List<Order> orders = new LinkedList<>();
    public static void main(String[] args) throws IOException {
        Solution22 solution = new Solution22();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        getInput();
        for(Order order : orders) {
            System.out.println("processing order");
            processOrder(order);
        }
        System.out.println("result: " + cubes.size());
    }

    private void processOrder(Order order) {
        if (order.on) {
            for(int i=order.x1;i<=order.x2;i++) {
                if (Math.abs(i) >50) {
                    continue;
                }
                for(int j=order.y1;j<=order.y2;j++) {
                    if (Math.abs(j) >50) {
                        continue;
                    }
                    for(int k=order.z1;k<=order.z2;k++) {
                        if (Math.abs(k) <=50) {
                            cubes.add(new Position(i, j, k));
                        }
                    }
                }
            }
        } else {
            for(int i=order.x1;i<=order.x2;i++) {
                if (Math.abs(i) >50) {
                    continue;
                }
                for(int j=order.y1;j<=order.y2;j++) {
                    if (Math.abs(j) >50) {
                        continue;
                    }
                    for(int k=order.z1;k<=order.z2;k++) {
                        if (Math.abs(k) <=50) {
                            cubes.remove(new Position(i, j, k));
                        }
                    }
                }
            }
        }
    }

    private void getInput() throws FileNotFoundException {
        Scanner inputScanner = new Scanner(new FileInputStream(INPUTFILE));

        while (inputScanner.hasNextLine()) {
            String line = inputScanner.nextLine().trim();
            Matcher lineMatcher = lineRegex.matcher(line);
            if (lineMatcher.matches()) {
                String onOff = lineMatcher.group(1);
                int x1 = Integer.parseInt(lineMatcher.group(2));
                int x2 = Integer.parseInt(lineMatcher.group(3));
                int y1 = Integer.parseInt(lineMatcher.group(4));
                int y2 = Integer.parseInt(lineMatcher.group(5));
                int z1 = Integer.parseInt(lineMatcher.group(6));
                int z2 = Integer.parseInt(lineMatcher.group(7));
                orders.add(new Order(x1,x2,y1,y2,z1,z2,onOff.equals("on")));
            } else {
                throw new RuntimeException("oops");
            }
        }

        inputScanner.close();
    }

    public class Position {
        int x;
        int y;
        int z;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x &&
                    y == position.y &&
                    z == position.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }

        public Position(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z=z;
        }
    }
    public class Order {
        int x1;
        int x2;
        int y1;
        int y2;
        int z1;
        int z2;
        boolean on;

        @Override
        public String toString() {
            return "Order{" +
                    "x1=" + x1 +
                    ", x2=" + x2 +
                    ", y1=" + y1 +
                    ", y2=" + y2 +
                    ", z1=" + z1 +
                    ", z2=" + z2 +
                    ", on=" + on +
                    '}';
        }

        public Order(int x1, int x2, int y1, int y2, int z1, int z2, boolean on) {
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
            this.z1 = z1;
            this.z2 = z2;
            this.on = on;
        }
    }
    private static Pattern lineRegex = Pattern.compile("(on|off) x=(\\-*[0-9]+)\\.\\.(\\-*[0-9]+),y=(\\-*[0-9]+)\\.\\.(\\-*[0-9]+),z=(\\-*[0-9]+)\\.\\.(\\-*[0-9]+)");

}

