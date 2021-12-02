package Aoc2020;

import java.io.IOException;

public class Solution23 {

    public static final int MOVEMENTS = 10000000;

    public static final int CUP_NUMBER = 1000000;

    public static void main(String[] args) throws IOException {
        Solution23 solution = new Solution23();
        solution.execute();
    }

    public class Cup {
        Cup previous;
        Cup next;
        Integer value;
        public Cup(Integer value) {
            this.value=value;
        }
        public void setAsPrevious(Cup cup) {
            this.previous = cup;
            this.previous.next = this;
        }
    }

    public void execute() {
        char[] input = "614752839".toCharArray();
        int min = 1;
        int max = Integer.MIN_VALUE;
        Cup firstCup = null;
        Cup previousCup = null;
        for(char inc : input) {
            int value = Character.digit(inc, 10);
            if (value > max) { max = value;}
            Cup cup = new Cup(value);
            if (firstCup == null) {
                firstCup = cup;
            } else {
                cup.setAsPrevious(previousCup);
            }
            previousCup = cup;
        }

        for(int i=max+1;i<=CUP_NUMBER;i++){
            Cup cup = new Cup(i);
            cup.setAsPrevious(previousCup);
            previousCup = cup;
        }
        firstCup.setAsPrevious(previousCup);
        max=CUP_NUMBER;

        Cup[] cupLinks = new Cup[CUP_NUMBER+1];
        Cup currentCup = firstCup;
        do {
            cupLinks[currentCup.value] = currentCup;
            currentCup = currentCup.next;
        } while (currentCup != firstCup);

        currentCup = firstCup;
        int numberOfMoves = 0;
        while(numberOfMoves++ < MOVEMENTS) {
//            if (numberOfMoves % 10000 == 0) {
//                System.out.println("Done movements: " + numberOfMoves + " " + (numberOfMoves / MOVEMENTS) + "%");
//            }
            //System.out.println("Move : " + (numberOfMoves ));
            //printState(gameOfCups);
            if (numberOfMoves == 1124997) {
                System.out.println("Let's see what goes wrong");
            }
            Cup removedThree = removeAndGetNextThree(currentCup);

            //System.out.println("Removed elements: ");
            //printState(removedElements);
            int nextValue = currentCup.value;
            do {
                nextValue--;
                if (nextValue < min) {
                    nextValue=max;
                }
            } while (contains(removedThree, nextValue));

            Cup cupForPlacement = cupLinks[nextValue];
            addAfter(cupForPlacement, removedThree);

//            while(current != gameOfCups.get(currentIndex)) {
//                gameOfCups.add(gameOfCups.removeFirst());
//            }
            //System.out.println("Current iteration state");
            //printCups(currentCup);
            if (currentCup == currentCup.next) {
                System.out.println("Something went very wrong!");
            }
            currentCup = currentCup.next;
            if (numberOfMoves==1124997 && count(currentCup) < max) {
                System.out.println("We lost a cup!");
            }
            //System.out.println("------");
        }
        System.out.println("Final state");
        //printCups(cupLinks[1]);
        Cup cup = cupLinks[1];
        System.out.println("Element 1: " + cup.next.value);
        System.out.println("Element 2: " + cup.next.next.value);
        System.out.println("Solution:" + (Integer.toUnsignedLong(cup.next.value) * Integer.toUnsignedLong(cup.next.next.value)));
    }
    void printCups(Cup firstCup) {
        System.out.print(firstCup.value+" ");
        Cup current = firstCup.next;
        while(current != firstCup) {
            System.out.print(current.value+" ");
            current = current.next;
        }
    }
    boolean contains(Cup mainCup, Integer value) {
        if (mainCup.value.equals( value) || mainCup.next.value.equals(value) || mainCup.next.next.value.equals(value)) {
            return true;
        } else {
            return false;
        }
    }

    public Cup removeAndGetNextThree(Cup mainCup) {
        Cup result = mainCup.next;
        Cup mainNext = result.next.next.next;
        mainNext.setAsPrevious(mainCup);
        result.setAsPrevious(result.next.next);
        return result;
    }

    public void addAfter(Cup before, Cup removedThree) {
        Cup after = before.next;
        removedThree.setAsPrevious(before);
        after.setAsPrevious(removedThree.next.next);
    }

    public int count(Cup initialCup) {
        int counter=0;
        Cup cup = initialCup;
        do {
            counter++;
            cup=cup.next;
        } while(cup != initialCup);
        return counter;
    }


}