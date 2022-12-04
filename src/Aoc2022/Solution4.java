package Aoc2022;

import Common.InputParser;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Solution4 {
    public static void main(String[] args) throws Exception {
        Solution4 solution = new Solution4();
        solution.execute();
    }

    public record Range(int start, int end) {

        public boolean contains(Range elf2) {
                return (this.start <= elf2.start && this.end >= elf2.end);
            }

            public boolean overlaps(Range elf2) {
                return ((this.start >= elf2.start && this.start <= elf2.end) || (this.end >= elf2.start && this.end <= elf2.end));
            }

            @Override
            public String toString() {
                return "Range{" +
                        "start=" + start +
                        ", end=" + end +
                        '}';
            }
        }

    public record Pair(Range elf1, Range elf2) {

        public boolean oneContainsTheOther() {
                return (elf1.contains(elf2) || elf2.contains(elf1));
            }

            public boolean hasOverlap() {
                return (elf1.overlaps(elf2) || elf2.overlaps(elf1));
            }

            @Override
            public String toString() {
                return "Pair{" +
                        "elf1=" + elf1 +
                        ", elf2=" + elf2 +
                        '}';
            }
        }

    public void execute() throws Exception {
        List<Pair> pairs = Arrays.stream(InputParser.getLinesAsArrayListOfStrings("resources/2022/input4.txt")).map(this::getPairFromString).collect(Collectors.toList());
        part1(pairs);
        part2(pairs);
    }

    private void part1(List<Pair> pairs) {
        long count = pairs.stream().mapToLong(p -> p.oneContainsTheOther() ? 1 : 0).sum();
        System.out.println("Fully contained: " + count);
    }

    private void part2(List<Pair> pairs) {
        long count = pairs.stream().mapToLong(p -> p.hasOverlap() ? 1 : 0).sum();
        System.out.println("Overlaps: " + count);
    }

    public Pair getPairFromString(String line) {
        String[] rangeStrings = line.split( ",");
        String[] elf1 = rangeStrings[0].split("-");
        String[] elf2 = rangeStrings[1].split("-");
        return new Pair(new Range(Integer.parseInt(elf1[0]), Integer.parseInt(elf1[1])), new Range(Integer.parseInt(elf2[0]), Integer.parseInt(elf2[1])));
    }

}
