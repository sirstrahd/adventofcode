package Aoc2022;

import Common.InputParser;

import java.util.*;
import java.util.stream.Collectors;

public class Solution11b {


    public static void main(String[] args) throws Exception {
        Solution11b solution = new Solution11b();
        solution.execute();
    }

    public void execute() throws Exception {
        List<String> input = InputParser.getLinesAsListOfStrings("resources/2022/input11.txt");
        Map<Integer, Monkey> monkeys = getMonkeysFromInput(input);
        Long commonMultiplier = 1L;
        for (Monkey monkey: monkeys.values()) {
            commonMultiplier = commonMultiplier * monkey.divisible;
        }
        for(int i=0;i<10000;i++) {
            // System.out.println("Turn " + i);
            doTurn(monkeys,commonMultiplier);
        }
        long max=0L;
        long max2=0L;

        Collection<Monkey> monkeyValues = monkeys.values();
        for(Monkey monkey: monkeyValues) {
            if (monkey.inspectionCount >= max) {
                max2=max;
                max = monkey.inspectionCount;
            } else if (monkey.inspectionCount >= max2) {
                max2 = monkey.inspectionCount;
            }
        }
        System.out.println("Result: " + (max * max2));
    }

    private Map<Integer, Monkey> getMonkeysFromInput(List<String> input) {
        Map<Integer, Monkey> monkeys = new HashMap<>();
        for(int i = 0; i< input.size(); i+=7) {
            int monkeyNumber = Integer.parseInt(input.get(i+0).split(" ")[1].replace(":",""));
            List<Long> startingItems = Arrays.stream(input.get(i+1).split(":")[1].split(",")).map(String::trim).map(Long::parseLong).collect(Collectors.toList());
            String[] operation = input.get(i+2).split("Operation: new = old")[1].split(" ");
            long divisible = Long.parseLong(input.get(i+3).split("Test: divisible by ")[1]);
            int throwIfTrue = Integer.parseInt(input.get(i+4).split("If true: throw to monkey ")[1]);
            int throwIfFalse = Integer.parseInt(input.get(i+5).split("If false: throw to monkey ")[1]);
            monkeys.put(monkeyNumber,new Monkey(startingItems,operation,divisible,throwIfTrue, throwIfFalse));
        }
        return monkeys;
    }

    private void doTurn(Map<Integer, Monkey> monkeys, Long commonMultiplier) {
        for(int i=0; i<monkeys.size();i++) {
            Monkey monkey = monkeys.get(i);
            monkey.inspectionCount+=monkey.items.size();
//            System.out.println(" Monkey: " + i);
            for (Long item : monkey.items) {
//                System.out.println("  Monkey inspects item with level: " + item);
                long newWorry = applyWorry(monkey.operation, item, commonMultiplier);
//                System.out.println("  Monkey output worry: " + newWorry);
                if (newWorry % monkey.divisible == 0) {
//                    System.out.println("  Worry divisible: " + newWorry);
//                    System.out.println("  Item thrown to : " + monkey.throwIfTrue);
                    monkeys.get(monkey.throwIfTrue).items.add(newWorry);
                } else {
//                    System.out.println("  Worry not divisible: " + newWorry);
//                    System.out.println("  Item thrown to : " + monkey.throwIfFalse);
                    monkeys.get(monkey.throwIfFalse).items.add(newWorry);
                }
            }
            monkey.items.clear();
        }
    }

    private Long applyWorry(String[] operation, Long item, Long commonMultiplier) {
        long secondOperand = switch(operation[2]) {
            case "old" -> item;
            default -> Long.parseLong(operation[2].trim());
        };
        return switch(operation[1]) {
            case "+" -> (item + secondOperand) % commonMultiplier;
            case "*" -> (item * secondOperand) % commonMultiplier;
            default -> throw new RuntimeException("wtf?");
        };
    }


    public class Monkey {

        List<Long> items;

        String[] operation;

        long divisible;

        int throwIfTrue;

        int throwIfFalse;

        int inspectionCount = 0;

        public Monkey(List<Long> items, String[] operation, long divisible, int throwIfTrue, int throwIfFalse) {
            this.items = items;
            this.operation=operation;
            this.divisible=divisible;
            this.throwIfTrue=throwIfTrue;
            this.throwIfFalse=throwIfFalse;
        }

    }
}
