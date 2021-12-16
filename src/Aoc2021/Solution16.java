package Aoc2021;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Solution16 {

    private int versionNumbersSum = 0;

    private static final String INPUTFILE = "/Users/marc/workspace/adventofcode/resources/input16.txt";

    public static void main(String[] args) throws IOException {
        Solution16 solution = new Solution16();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        ArrayList<Character> bits = getInput();
        List<LiteralResult> results = processPackets(bits,0,bits.size(), null, true);
        System.out.println("Total score: " + results.get(0).result);
        System.out.println("Version numbers sum: " + versionNumbersSum);

    }

    public class LiteralResult {
        long result;
        int length;
        public LiteralResult(long result, int length) {
            this.result = result;
            this.length=length;
        }
    }

    private List<LiteralResult> processPackets(ArrayList<Character> bits, final int originalOffset, Integer endOfPacket, Integer packetNumber, boolean ignorePadding) {
        int offset = originalOffset;
        List<LiteralResult> results = new ArrayList<>();
        while((endOfPacket != null && offset<endOfPacket) || (packetNumber!=null && packetNumber-->0)) {
            char[] versionChars = {bits.get(offset++), bits.get(offset++), bits.get(offset++)};
            char[] typeIDChars = {bits.get(offset++), bits.get(offset++), bits.get(offset++)};
            int version = (int)binaryArrayToNumber(versionChars);
            int typeId = (int)binaryArrayToNumber(typeIDChars);
            versionNumbersSum+=version;
            System.out.println("Version: " + version + " typeId: " + typeId);
            switch (typeId) {
                case 4:
                    LiteralResult result = processLiteral(bits, offset);
                    offset += result.length;
                    result.length+=6;
                    results.add(result);
                    System.out.println("Get literal: " + result.result);
                    break;
                default:
                    int lengthType = bits.get(offset++);
                    List<LiteralResult> subResults = null;
                    if (lengthType == '0') {
                        int size = (int)getNumberFromList(bits, offset, 15);
                        System.out.println("Processing subpackets by size " + size);
                        subResults = processPackets(bits, offset + 15,
                                offset + 15 + size, null,false);
                        offset+=(15+size);
                    } else {
                        int subPacketNumber = (int)getNumberFromList(bits, offset, 11);
                        System.out.println("Processing subpackets by amount " + subPacketNumber);
                        subResults =processPackets(bits, offset + 11,null,
                                subPacketNumber, false);
                        offset+=11;
                        offset+= subResults.get(0).length;
//                        for(LiteralResult subResult : subResults) {
//                            offset+=subResult.length;
//                        }
                    }
                    LiteralResult literalResult;
                    switch(typeId) {
                        case 0: literalResult = sum(subResults);
                            break;
                        case 1: literalResult = product(subResults);
                            break;
                        case 2: literalResult = minimum(subResults);
                            break;
                        case 3: literalResult = maximum(subResults);
                            break;
                        case 5: literalResult = gt(subResults);
                            break;
                        case 6: literalResult = lt(subResults);
                            break;
                        case 7: literalResult = equals(subResults);
                            break;
                        default: throw new RuntimeException("unexpected package");

                    }
                    results.add(literalResult);

            }
            if (ignorePadding) {
                break;
            }
        }
        results.get(0).length=offset - originalOffset;
//        LiteralResult aggregatedResult = new LiteralResult(0, offset - originalOffset);
//        results = new ArrayList<>();
//        results.add(aggregatedResult);
        return results;
    }

    public LiteralResult sum(List<LiteralResult> input) {
        System.out.println("processing sum");
        long output = 0L;
        for(LiteralResult res : input) {
            output+=res.result;
        }
        return new LiteralResult(output,0);
    }

    public LiteralResult product(List<LiteralResult> input) {
        System.out.println("processing product");
        long output = 1L;
        for(LiteralResult res : input) {
            output*=res.result;
        }
        return new LiteralResult(output,0);
    }

    public LiteralResult minimum(List<LiteralResult> input) {
        System.out.println("processing min");
        long output = Long.MAX_VALUE;
        for(LiteralResult res : input) {
            output=Math.min(output, res.result);
        }
        return new LiteralResult(output,0);
    }

    public LiteralResult maximum(List<LiteralResult> input) {
        System.out.println("processing max");
        long output = Long.MIN_VALUE;
        for(LiteralResult res : input) {
            output=Math.max(output, res.result);
        }
        return new LiteralResult(output,0);
    }

    public LiteralResult gt(List<LiteralResult> input) {
        System.out.println("processing gt");
        long output = 0L;
        long firstValue = input.get(0).result;
        long secondValue = input.get(1).result;
        if (firstValue > secondValue) {
            output=1L;
        }
        return new LiteralResult(output,0);
    }

    public LiteralResult lt(List<LiteralResult> input) {
        System.out.println("processing lt");
        long output = 0L;
        long firstValue = input.get(0).result;
        long secondValue = input.get(1).result;
        if (firstValue < secondValue) {
            output=1L;
        }
        return new LiteralResult(output,0);
    }

    public LiteralResult equals(List<LiteralResult> input) {
        System.out.println("processing equals");
        long output = 0L;
        long firstValue = input.get(0).result;
        long secondValue = input.get(1).result;
        if (firstValue == secondValue) {
            output=1L;
        }
        return new LiteralResult(output,0);
    }

    private LiteralResult processLiteral(ArrayList<Character> bits, int offset) {
        int length=0;
        boolean isLast = bits.get(offset+length++) == '0';
        List<Character> value = new LinkedList<>();
        value.add(bits.get(offset+length++));
        value.add(bits.get(offset+length++));
        value.add(bits.get(offset+length++));
        value.add(bits.get(offset+length++));
        while(!isLast) {
            isLast = bits.get(offset+length++) == '0';
            value.add(bits.get(offset+length++));
            value.add(bits.get(offset+length++));
            value.add(bits.get(offset+length++));
            value.add(bits.get(offset+length++));
        }

        return new LiteralResult(binaryListToNumber(value), length);

    }

    private ArrayList<Character> getInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(INPUTFILE));
        String hexaLine = scanner.nextLine().trim();
        scanner.close();
        ArrayList<Character> bitLine = convertToBinary(hexaLine);
        return bitLine;
    }

    private long binaryArrayToNumber(char[] input) {
        long output=0;
        for(int i = 0;i<input.length;i++) {
            output = output*2 + Character.getNumericValue(input[i]);
        }
        return output;
    }

    private long binaryListToNumber(List<Character> input) {
        long output=0;
        for(int i = 0;i<input.size();i++) {
            output = output*2 + Character.getNumericValue(input.get(i));
        }
        return output;
    }

    private long getNumberFromList(List<Character> input, int offset, int length) {
        long output=0;
        for(int i = offset;i<offset+length;i++) {
            output = output*2 + Character.getNumericValue(input.get(i));
        }
        return output;
    }


    private ArrayList<Character> convertToBinary(String hexaLine) {
        ArrayList<Character> output = new ArrayList<>();
        for(Character c : hexaLine.toCharArray()) {
            List<Character> value = hexaToBin(c);
            output.addAll(value);
        }
        return output;
    }

    private List<Character> hexaToBin(Character hexa) {
        List<Character> output = new ArrayList<>();
        switch (hexa) {
            case '0': output.add('0');output.add('0');output.add('0');output.add('0');break;
            case '1': output.add('0');output.add('0');output.add('0');output.add('1');break;
            case '2': output.add('0');output.add('0');output.add('1');output.add('0');break;
            case '3': output.add('0');output.add('0');output.add('1');output.add('1');break;
            case '4': output.add('0');output.add('1');output.add('0');output.add('0');break;
            case '5': output.add('0');output.add('1');output.add('0');output.add('1');break;
            case '6': output.add('0');output.add('1');output.add('1');output.add('0');break;
            case '7': output.add('0');output.add('1');output.add('1');output.add('1');break;
            case '8': output.add('1');output.add('0');output.add('0');output.add('0');break;
            case '9': output.add('1');output.add('0');output.add('0');output.add('1');break;
            case 'A': output.add('1');output.add('0');output.add('1');output.add('0');break;
            case 'B': output.add('1');output.add('0');output.add('1');output.add('1');break;
            case 'C': output.add('1');output.add('1');output.add('0');output.add('0');break;
            case 'D': output.add('1');output.add('1');output.add('0');output.add('1');break;
            case 'E': output.add('1');output.add('1');output.add('1');output.add('0');break;
            case 'F': output.add('1');output.add('1');output.add('1');output.add('1');break;
        }
        return output;
    }

}

