package Aoc2020;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution20 {

    Map<Long, List<char[][]>> rotationMap;
    Map<ElementRotationAndSide, Set<ElementRotationAndSide>> matches;
    List<Long> keys = new ArrayList<>();
    Integer lineLength = null;

    public boolean lookForMatches(int x, int y, ElementAndRotation[][] matrix,  Set<Long> usedElements) {
        if (y>=matrix.length) {
            return true;
        } else if (x>=matrix.length) {
            return lookForMatches(0, y+1, matrix, usedElements);
        }
        for(Long key : keys) {
            if (!usedElements.contains(key)) {
                for(int rotation=0;rotation<8;rotation++) {
                    if (x==0 && y==0){
                        usedElements.add(key);
                        matrix[x][y] = new ElementAndRotation(key, rotation);
                        if (lookForMatches(x+1,y,matrix,usedElements)) {
                            return true;
                        } else {
                            usedElements.remove(key);
                        }
                    } else {
                        if (x > 0) {
                            ElementAndRotation elementToTheLeft = matrix[x - 1][y];
                            Set<ElementRotationAndSide> validToTheLeft = matches.get(new ElementRotationAndSide(key, rotation, Side.LEFT));
                            if (validToTheLeft == null) {
                                continue;
                            }
                            boolean found = false;
                            for (ElementRotationAndSide element : validToTheLeft) {
                                if (element.element.equals(elementToTheLeft.element) && element.rotation.equals(elementToTheLeft.rotation)) {
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                continue;
                            }
                        }
                        if (y > 0) {
                            ElementAndRotation elementAbove = matrix[x][y-1];
                            Set<ElementRotationAndSide> validAbove = matches.get(new ElementRotationAndSide(key, rotation, Side.UP));
                            if (validAbove == null) {
                                continue;
                            }
                            boolean found = false;
                            for (ElementRotationAndSide element : validAbove) {
                                if (element.element.equals(elementAbove.element) && element.rotation.equals(elementAbove.rotation)) {
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                continue;
                            }
                        }
                        usedElements.add(key);
                        matrix[x][y] = new ElementAndRotation(key, rotation);
                        if (lookForMatches(x+1,y,matrix,usedElements)) {
                            return true;
                        } else {
                            usedElements.remove(key);
                        }
                    }

                }
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        Solution20 solution = new Solution20();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        Map<Long, char[][]> input = getInput();
        rotationMap = new HashMap<>();
        for(Long key : input.keySet()){
            rotationMap.put(key, getRotations(input.get(key)));
        }

        matches = getAllMatches();
        int inputSize = input.keySet().size();

        ElementAndRotation[][] initialMatrix = constructAllMatch(inputSize);
        char[][] finalMatrix = getFinalMatrix(initialMatrix);
        List<char[][]> finalRotations = getRotations(finalMatrix);
        int monsters = 0;
        for(char[][] rotation : finalRotations) {
            monsters = findMonsters(rotation);
            if (monsters != 0) {
                System.out.println("Found monsters: " + monsters);
                break;
            }
        }
        int elements = countElements(finalMatrix);
        int monsterElements = monsters * 15;
        int result = elements - monsterElements;
        System.out.println("Result:" + result);

    }

    private int countElements(char[][] matrix) {
        int count = 0;
        for(int i=0;i<matrix.length;i++) {
            for(int j=0;j<matrix.length;j++) {
                if (matrix[i][j] == '#') {
                    count++;
                }
            }
        }
        return count;
    }

    private int findMonsters(char[][] input) {
        int count = 0;
        for(int i=0;i< input.length;i++) {
            for(int j=0;j<input.length;j++) {
                if (isMonsterTail(input,i,j)) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isSet(char[][] input, int x, int y) {
        boolean xValid = (x>=0 && x < input.length);
        boolean yValid = (y >= 0 && y < input.length);
        return xValid && yValid && input[x][y] == '#';
    }

    private boolean isMonsterTail(char[][] input, int x, int y) {
        if (isSet(input, x,y)
                && isSet(input, x+1,y+1)
                && isSet(input, x+4,y+1)
                && isSet(input, x+5,y)
                && isSet(input, x+6,y)
                && isSet(input, x+7,y+1)
                && isSet(input, x+10,y+1)
                && isSet(input, x+11,y)
                && isSet(input, x+12,y)
                && isSet(input, x+13,y+1)
                && isSet(input, x+16,y+1)
                && isSet(input, x+17,y)
                && isSet(input, x+18,y)
                && isSet(input, x+18,y-1)
                && isSet(input, x+19,y)
        ) {
            System.out.println("Found monster in point " + x + " " + y);
            return true;
        } else {
            return false;
        }
    }

    private char[][] getFinalMatrix(ElementAndRotation[][] initialMatrix) {
        char[][] finalMatrix = new char[(lineLength-2)*initialMatrix.length][];
        for(int i=0;i<finalMatrix.length;i++) {
            finalMatrix[i] = new char[(lineLength-2)*initialMatrix.length];
        }
        int currentY=0;
        for(int i=0;i<initialMatrix.length;i++) {
            int currentX=0;
            for(int j=0;j<initialMatrix[i].length;j++) {
                ElementAndRotation element = initialMatrix[i][j];
                char[][] list = rotationMap.get(element.element).get(element.rotation);
                for(int k=1;k<list.length-1;k++) {
                    for(int l=1;l<list.length-1;l++) {
                        finalMatrix[currentX+k-1][currentY+l-1] = list[k][l];
                    }
                }
                currentX+=lineLength-2;
            }
            currentY+=lineLength-2;
        }
        return finalMatrix;
    }

    public ElementAndRotation[][] constructAllMatch( int inputSize) {
        int sideSize = (int) Math.sqrt(inputSize);
        ElementAndRotation[][] matrix = new ElementAndRotation[sideSize][];
        for(int i=0;i<matrix.length;i++) {
            matrix[i] = new ElementAndRotation[sideSize];
        }
        Set<Long> usedElements = new HashSet<>();
        if (lookForMatches(0,0,matrix, usedElements)) {
            long result = matrix[0][0].element*matrix[0][sideSize-1].element*matrix[sideSize-1][0].element*matrix[sideSize-1][sideSize-1].element;
        }
        return matrix;
    }


    private Map<ElementRotationAndSide, Set<ElementRotationAndSide>> getAllMatches() {
        Map<ElementRotationAndSide, Set<ElementRotationAndSide>> output = new HashMap<>();
        for(Long key : rotationMap.keySet()) {
            List<char[][]> currentList = rotationMap.get(key);
            for(int i=0; i<currentList.size();i++) {
                for(Side side : Side.values()) {
                    ElementRotationAndSide element = new ElementRotationAndSide(key, i, side);
                    Set<ElementRotationAndSide> matches = getSideMatches(element);
                    if (matches.size() >0) {
                        output.put(element, matches);
                    }
                }
            }
        }
        return output;
    }

    private Set<ElementRotationAndSide> getSideMatches(ElementRotationAndSide element) {
        Set<ElementRotationAndSide> result = new HashSet<>();
        Set<Long> keys = rotationMap.keySet();
        for(Long key : keys) {
            if (!key.equals(element.element)) {
                List<char[][]> otherElementList = rotationMap.get(key);
                for (int i = 0; i < otherElementList.size(); i++) {
                    if (checkIfMatch(element, otherElementList.get(i), element.element, key, i)) {
                        result.add(new ElementRotationAndSide(key, i, element.side.reverse()));
                    }
                }
            }
        }
        return result;
    }

    private boolean checkIfMatch(ElementRotationAndSide element, char[][] target, Long leftId, Long rightId, int rotation) {
        char[][] source = rotationMap.get(element.element).get(element.rotation);
        switch(element.side) {
            case UP:
                for(int i=0; i<source.length;i++) {
                    if (source[0][i] != target[source.length-1][i]) {
                        return false;
                    }
                }
                break;
            case DOWN:
                for(int i=0; i<source.length;i++) {
                    if (source[source.length-1][i] != target[0][i]) {
                        return false;
                    }
                }
                break;
            case LEFT:
                for(int i=0; i<source.length;i++) {
                    if (source[i][0] != target[i][source.length-1]) {
                        return false;
                    }
                }
                break;
            case RIGHT:
                for(int i=0; i<source.length;i++) {
                    if (source[i][source.length-1] != target[i][0]) {
                        return false;
                    }
                }
                break;

        }
        return true;
    }

    public enum Side {
        UP, DOWN, LEFT, RIGHT;

        public Side reverse() {
            switch (this) {
                case UP: return DOWN;
                case DOWN: return UP;
                case LEFT: return RIGHT;
                default: return LEFT;
            }

        }

    };


    private static Pattern idRegex = Pattern.compile("Tile ([0-9]+):");

    private Map<Long, char[][]> getInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("/Users/marc/Desktop/input20.txt"));
        Map<Long, char[][]> input = new HashMap<>();
        Long lastId = null;
        List<String> currentMap = new LinkedList<>();
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains("Tile")) {
                Matcher idMatcher = idRegex.matcher(line);
                idMatcher.find();
                lastId = Long.parseLong(idMatcher.group(1));
                keys.add(lastId);
            } else if (line.isEmpty()) {
                input.put(lastId, convertToCharArray(currentMap));
                currentMap = new LinkedList<>();
            } else {
                currentMap.add(line);
                lineLength = line.length();
            }
        }
        input.put(lastId, convertToCharArray(currentMap));
        scanner.close();
        return input;
    }

    private char[][] convertToCharArray(List<String> currentMap) {
        char[][] output = new char[currentMap.size()][];
        for(int i=0; i< currentMap.size();i++) {
            output[i] = currentMap.get(i).toCharArray();
        }
        return output;
    }

    private List<char[][]> getRotations(char[][] input) {
        List<char[][]> outputs = new ArrayList<>();
        outputs.add(input);
        for(int i=0;i<3;i++) {
            char[][] rotation = rotateLeft(input);
            outputs.add(rotation);
            input = rotation;
        }
        input = rotateLeft(input);
        input = invert(input);
        outputs.add(input);
        for(int i=0;i<3;i++) {
            char[][] rotation = rotateLeft(input);
            outputs.add(rotation);
            input = rotation;
        }

        return outputs;
    }

    private char[][] invert(char[][] input) {
        final int length = input.length;
        char[][] output = new char[length][];
        for(int i=0;i<length;i++) {
            output[i] = new char[length];
        }
        for(int i=0;i<length;i++) {
            for(int j=0;j<length;j++) {
                output[i][j] = input[i][length-1-j];
            }
        }
        return output;
    }

    private char[][] rotateLeft(char[][] input) {
        final int length = input.length;
        char[][] output = new char[length][];
        for(int i=0;i<length;i++) {
            output[i] = new char[length];
        }
        for(int i=0;i<length;i++) {
            for(int j=0;j<length;j++) {
                output[length-1 -j][i] = input[i][j];
            }
        }
        return output;
    }

    public class ElementAndRotation{
        Long element;
        Integer rotation;

        public ElementAndRotation(Long element, Integer rotation) {
            this.element = element;
            this.rotation = rotation;
        }

        @Override
        public String toString() {
            return "ElementAndRotation{" +
                    "element=" + element +
                    ", rotation=" + rotation +
                    '}';
        }
    }


    public class ElementRotationAndSide {
        public final Long element;
        public final Integer rotation;
        public final Side side;

        public ElementRotationAndSide(Long element, Integer rotation, Side side) {
            this.element=element;
            this.rotation=rotation;
            this.side=side;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ElementRotationAndSide that = (ElementRotationAndSide) o;
            return Objects.equals(element, that.element) &&
                    Objects.equals(rotation, that.rotation) &&
                    side == that.side;
        }

        @Override
        public int hashCode() {
            return Objects.hash(element, rotation, side);
        }

        @Override
        public String toString() {
            return "ElementRotationAndSide{" +
                    "element=" + element +
                    ", rotation=" + rotation +
                    ", side=" + side +
                    '}';
        }
    }

    private void draw(char[][] inputs) {
        for(int i=0;i<inputs.length;i++) {
            System.out.println(new String(inputs[i]));
        }
    }

}