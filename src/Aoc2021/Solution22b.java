package Aoc2021;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution22b {

    private static final String INPUTFILE = System.getenv("HOME") + "/workspace/adventofcode/resources/input22.txt";

    public static void main(String[] args) throws IOException {
        Solution22b solution = new Solution22b();
        solution.execute();
    }


    public void execute() throws IOException {
        List<Order> orders = getInput();
        List<Order> cur = new ArrayList<>();
        cur.add(orders.get(0));
        for (int i = 1; i < orders.size(); i++) {
            cur = processOrder(cur, orders.get(i));
        }
        long res = 0;
        for (Order order : cur) {
            if (order.onOff) {
                res += order.size();
            }
        }
        System.out.println("switched-on cube count="+res);
    }

    private List<Order> processOrder(List<Order> cur, Order cube) {
        int size = cur.size();
        cur.add(cube);
        for (int j = 0; j < size; j++) {
            Order current = cur.get(j);
            if (overlap(cube, current)) {
                xLeft(cur, cube, current);
                xRight(cur, cube, current);
                yLeft(cur, cube, current);
                yRight(cur, cube, current);
                zLeft(cur, cube, current);
                zRight(cur, cube, current);
            } else {
                cur.add(current);
            }
        }
        return cur.subList(size, cur.size());
    }

    private void zRight(List<Order> cur, Order cube, Order current) {
        if (current.z2 >= cube.z1 && cube.z1 >= current.z1) {
            Order subCube = new Order( current.x1, current.x2, current.y1, current.y2, current.z1, cube.z1 - 1,current.onOff);
            if (subCube.isCorrect()) {
                cur.add(subCube);
                current.z1 = cube.z1;
            }
        }
    }

    private void zLeft(List<Order> cur, Order cube, Order current) {
        if (current.z1 <= cube.z2 && cube.z2 <= current.z2) {
            Order subCube = new Order(current.x1, current.x2, current.y1, current.y2, cube.z2 + 1, current.z2,current.onOff);
            if (subCube.isCorrect()) {
                cur.add(subCube);
                current.z2 = cube.z2;
            }
        }
    }

    private void yRight(List<Order> cur, Order cube, Order current) {
        if (current.y2 >= cube.y1 && cube.y1 >= current.y1) {
            Order subCube = new Order(current.x1, current.x2, current.y1, cube.y1 - 1, current.z1, current.z2,current.onOff);
            if (subCube.isCorrect()) {
                cur.add(subCube);
                current.y1 = cube.y1;
            }
        }
    }

    private void yLeft(List<Order> cur, Order cube, Order current) {
        if (current.y1 <= cube.y2 && cube.y2 <= current.y2) {
            Order subCube = new Order(current.x1, current.x2, cube.y2 + 1, current.y2, current.z1, current.z2,current.onOff);
            if (subCube.isCorrect()) {
                cur.add(subCube);
                current.y2 = cube.y2;
            }
        }
    }

    private void xRight(List<Order> cur, Order cube, Order current) {
        if (current.x1 <= cube.x2 && cube.x2 <= current.x2) {
            Order subCube = new Order( cube.x2 + 1, current.x2, current.y1, current.y2, current.z1, current.z2,current.onOff);
            if (subCube.isCorrect()) {
                cur.add(subCube);
                current.x2 = cube.x2;
            }
        }
    }

    private void xLeft(List<Order> cur, Order cube, Order current) {
        if (current.x2 >= cube.x1 && cube.x1 >= current.x1) {
            Order subCube = new Order(current.x1, cube.x1 - 1, current.y1, current.y2, current.z1, current.z2,current.onOff);
            if (subCube.isCorrect()) {
                cur.add(subCube);
                current.x1 = cube.x1;
            }
        }
    }


    private boolean overlap(Order cube, Order current) {
        return ((cube.x1 <= current.x1 && current.x1 <= cube.x2) || (current.x1 <= cube.x1 && cube.x1 <= current.x2)) &&
                ((cube.y1 <= current.y1 && current.y1 <= cube.y2) || (current.y1 <= cube.y1 && cube.y1 <= current.y2)) &&
                ((cube.z1 <= current.z1 && current.z1 <= cube.z2) || (current.z1 <= cube.z1 && cube.z1 <= current.z2));
    }

    private List<Order> getInput() throws FileNotFoundException {
        Scanner inputScanner = new Scanner(new FileInputStream(INPUTFILE));
        List<Order> orders = new LinkedList<>();
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
                orders.add(new Order( x1,x2,y1,y2,z1,z2,onOff.equals("on")));
            } else {
                throw new RuntimeException("oops");
            }
        }

        inputScanner.close();
        return orders;
    }

    private class Order {
        int x1;
        int x2;
        int y1;
        int y2;
        int z1;
        int z2;
        boolean onOff;

        @Override
        public String toString() {
            return "Order{" +
                    "x1=" + x1 +
                    ", x2=" + x2 +
                    ", y1=" + y1 +
                    ", y2=" + y2 +
                    ", z1=" + z1 +
                    ", z2=" + z2 +
                    ", onOff=" + onOff +
                    '}';
        }

        public Order(int x1, int x2, int y1, int y2, int z1, int z2, boolean onOff) {
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
            this.z1 = z1;
            this.z2 = z2;
            this.onOff = onOff;
        }

        private long size() {
            return (x2 - x1 + 1L) * (y2 - y1 + 1L) * (z2- z1 + 1L);
        }

        private boolean isCorrect() {
            return x1 <=x2 && y1 <= y2 && z1 <= z2;
        }

    }
    private static Pattern lineRegex = Pattern.compile("(on|off) x=(\\-*[0-9]+)\\.\\.(\\-*[0-9]+),y=(\\-*[0-9]+)\\.\\.(\\-*[0-9]+),z=(\\-*[0-9]+)\\.\\.(\\-*[0-9]+)");

}
