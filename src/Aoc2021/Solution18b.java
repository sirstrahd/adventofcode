package Aoc2021;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Solution18b {

    private static final String INPUTFILE = "/Users/marc/workspace/adventofcode/resources/input18.txt";

    public static void main(String[] args) throws IOException {
        Solution18b solution = new Solution18b();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        List<String> input = getInput();
        long maxMagnitude = Long.MIN_VALUE;
        for(int i=0;i<input.size();i++) {
            for(int j=0;j<input.size();j++) {
                if (i==j) {
                    continue;
                } else {
                    Node node1 = convertToNode(input.get(i));
                    Node node2 = convertToNode(input.get(j));
                    Node result = add(node1,node2);
                    long magnitude = calculateMagnitude(result);
                    System.out.println("Result: " + result);
                    System.out.println("Magnitude: " + magnitude);
                    maxMagnitude = Math.max(maxMagnitude, magnitude);
                }
            }
        }
        System.out.println("Max magnitude: " + maxMagnitude);
    }

    private long calculateMagnitude(Node current) {
        if (current.value != null) {
            return current.value;
        } else {
            return 3*calculateMagnitude(current.children.get(0)) + 2*calculateMagnitude(current.children.get(1));
        }
    }


    private Node add(Node node1, Node node2) {
        Node result = new Node(null);
        node1.parent=result;
        node2.parent=result;
        result.children.add(node1);
        result.children.add(node2);
        while(true) {
            if (!explode(result)) {
                if (!split(result)) {
                    break;
                }
            }
        }
        return result;
    }

    private boolean split(Node current) {
        if (current.value != null && current.value >=10) {
            int leftValue = current.value / 2;
            int rightValue = current.value / 2 + current.value % 2;
            current.value = null;
            Node leftChild = new Node(current);
            Node rightChild = new Node(current);
            leftChild.value=leftValue;
            rightChild.value=rightValue;
            current.children.add(leftChild);
            current.children.add(rightChild);
            return true;
        } else {
            for(Node node : current.children) {
                if (split(node)) {
                    return true;
                }
            }
            return false;
        }
    }

    private boolean explode(Node current) {
        if (current.isPair() && current.nesting() >=4) {
            int leftValue = current.children.get(0).value;
            int rightValue = current.children.get(1).value;
            current.children = new LinkedList<>();
            current.value=0;
            addLeft(current, leftValue);
            addRight(current, rightValue);
            return true;
        } else {
            for(Node node : current.children) {
                if (explode(node)) {
                    return true;
                }
            }
            return false;
        }
    }

    private void addLeft(Node original, int value) {
        Node child = original;
        while(true) {
            if (child.isRoot()) {
                // no left sibling
                return;
            }
            Node current = child.parent;
            int index = current.children.indexOf(child);
            if (index==0) {
                // is leftmost keep going up
                child = current;
            } else {
                addDownRight(current.children.get(index-1), value);
                return;
            }
        }
    }

    private void addRight(Node original, int value) {
        Node child = original;
        while(true) {
            if (child.isRoot()) {
                // no right sibling
                return;
            }
            Node current = child.parent;
            int index = current.children.indexOf(child);
            if (index==current.children.size()-1) {
                // is rightMost node keep going up
                child = current;
            } else {
                addDownLeft(current.children.get(index+1), value);
                return;
            }
        }
    }

    private void addDownLeft(Node node, int value) {
        while(!node.children.isEmpty()) {
            node = node.children.getFirst();
        }
        node.value = node.value+value;
    }

    private void addDownRight(Node node, int value) {
        while(!node.children.isEmpty()) {
            node = node.children.getLast();
        }
        node.value = node.value+value;
    }

    private List<String> getInput() throws FileNotFoundException {
        List<String> result = new LinkedList<>();
        Scanner scanner = new Scanner(new FileInputStream(INPUTFILE));
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            result.add(line);
        }
        scanner.close();
        return result;
    }

    private Node convertToNode(String line) {
        int i=1;
        Node currentNode = new Node(null);
        while(i<line.length()-1) {
            switch(line.charAt(i)) {
                case '[': Node node = new Node(currentNode);
                    currentNode.children.add(node);
                    currentNode = node;
                    break;
                case ']': currentNode = currentNode.parent;
                        break;
                case ',':break;
                default: node = new Node(currentNode);
                    node.value=Character.getNumericValue(line.charAt(i));
                    currentNode.children.add(node);
            }
            i++;
        }
        assert currentNode.isRoot();
        return currentNode;
    }

    public class Node {
        public LinkedList<Node> children = new LinkedList<>();
        public Node parent;
        public Node(Node parent) {
            this.parent=parent;
        }
        Integer value = null;

        public boolean isRoot() {
            return (parent==null);
        }
        public boolean isPair() {
            return (this.value == null && this.children.size()==2&&this.children.get(0)!=null&&this.children.get(1)!=null);
        }

        public int nesting() {
            int i=0;
            Node current=this;
            while(!current.isRoot()) {
                i++;
                current=current.parent;
            }
            return i;
        }

        @Override
        public String toString() {
            if (value != null) {
                return "" + value;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                for(int i=0;i<children.size();i++) {
                    sb.append(children.get(i).toString());
                    if (i<children.size()-1) {
                        sb.append(",");
                    }
                }
                sb.append("]");
                return sb.toString();
            }
        }
    }


}

