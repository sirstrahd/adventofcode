package Aoc2021;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Solution8b {

    private static Set<Character> VALUES = new HashSet<Character>();
    static {
        VALUES.add('a');
        VALUES.add('b');
        VALUES.add('c');
        VALUES.add('d');
        VALUES.add('e');
        VALUES.add('f');
        VALUES.add('g');
    }

    private static Map<String, Integer> KEYS = new HashMap<>();
    static {
        KEYS.put("abcefg",0);
        KEYS.put("cf",1);
        KEYS.put("acdeg",2);
        KEYS.put("acdfg",3);
        KEYS.put("bcdf",4);
        KEYS.put("abdfg",5);
        KEYS.put("abdefg",6);
        KEYS.put("acf",7);
        KEYS.put("abcdefg",8);
        KEYS.put("abcdfg",9);
    }


    public static void main(String[] args) throws IOException {
        Solution8b solution = new Solution8b();
        solution.execute();
    }

    public void execute() throws FileNotFoundException {
        List<Sample> samples = getInput();
        int totalRelevantDigits = 0;
        for(Sample sample : samples) {
            totalRelevantDigits+=processSample(sample);
        }

        // 3600 too low
        System.out.println("Total result: " + totalRelevantDigits);
    }

    private long processSample(Sample sample) {
        Map<Character, Set<Character>> candidateValues = new HashMap<>();
        for(Character value : VALUES) {
            Set<Character> candidates = new HashSet<>();
            candidates.addAll(VALUES);
            candidateValues.put(value, candidates);
        }
        process1And4And7(sample.patterns, candidateValues);
        // I know 'a' and I have 2 candidates for both 'b' and 'd'.
        process5Length(sample.patterns, candidateValues);
        // we know a,d,g
        Character[] cf = candidateValues.get('c').toArray(new Character[0]);
        for(Character c : cf) {
            if (countAppearances(sample.patterns, c) == 9) {
                // it's f
                candidateValues.get('f').clear();
                candidateValues.get('f').add(c);
                candidateValues.get('b').remove(c);
                candidateValues.get('e').remove(c);
            } else {
                // it's c
                candidateValues.get('c').clear();
                candidateValues.get('c').add(c);
                candidateValues.get('b').remove(c);
                candidateValues.get('e').remove(c);
            }
        }
        // we know a,c,d,f,g
        for(Character c : VALUES) {
            Set<Character> cSet = candidateValues.get(c);
            if (cSet.size() == 1) {
                for(Set<Character> chars : candidateValues.values()) {
                    if (chars != cSet) {
                        chars.remove(cSet.iterator().next());
                    }
                }
            }
        }
        // we now know all
        Map<Character, Character> translationMap = new HashMap<>();
        for(Character c : candidateValues.keySet()) {
            translationMap.put(candidateValues.get(c).iterator().next(), c);
        }
        List<String> translatedValues = translate(sample.outputValues,translationMap);
        long count = 0;
        for(String value : translatedValues) {
            int valueInt = KEYS.get(value);
            System.out.println("Value: " + valueInt);
            count = count*10 + valueInt;
        }
        return count;
    }



    private int countAppearances(String[] patterns, Character c) {
        int count = 0;
        for (String pattern : patterns) {
            if (pattern.indexOf(c) != -1) {
                count++;
            }
        }
        return count;
    }

    private List<String> translate(String[] originals, Map<Character, Character> map) {
        List<String> result = new ArrayList<>();
        for(String original : originals) {
            result.add(translateWord(original, map));
        }
        return result;
    }

    private String translateWord(String string, Map<Character, Character> map) {
        char[] result = new char[string.length()];
        for(int i=0; i<string.length();i++) {
            result[i] = map.get(string.charAt(i));
        }
        Arrays.sort(result);
        return String.valueOf(result);
    }

    private void process5Length(String[] patterns, Map<Character, Set<Character>> candidateValues) {
        List<String> valuesFor5 = getWithLength(patterns, 5);
        List<Character> adg = getCharsInAll(valuesFor5);
        removeNotIn(candidateValues, 'a', adg);
        removeNotIn(candidateValues, 'd', adg);
        removeNotIn(candidateValues, 'g', adg);
    }

    private void removeNotIn(Map<Character, Set<Character>> candidateValues, char d, List<Character> adg) {
        Set<Character> set = candidateValues.get(d);
        for(char c : set.toArray(new Character[0])) {
            if (!adg.contains(c)) {
                set.remove(c);
            }
        }
    }

    private List<Character> getCharsInAll(List<String> valuesFor5) {
        List<Character> chars = new ArrayList<>();
        for(Character c : valuesFor5.get(0).toCharArray()) {
            chars.add(c);
            for(String phrase : valuesFor5) {
                if (phrase.indexOf(c) == -1) {
                    chars.remove(c);
                }
            }
        }
        return chars;
    }

    private void process1And4And7(String[] patterns, Map<Character, Set<Character>> candidateValues) {
        String valuesFor7 = getWithLength(patterns, 3).get(0);
        candidateValues.get('a').clear();
        candidateValues.get('c').clear();
        candidateValues.get('f').clear();
        for(Character c : valuesFor7.toCharArray()) {
            candidateValues.get('a').add(c);
            candidateValues.get('b').remove(c);
            candidateValues.get('c').add(c);
            candidateValues.get('d').remove(c);
            candidateValues.get('e').remove(c);
            candidateValues.get('f').add(c);
            candidateValues.get('g').remove(c);
        }

        String valuesFor1 = getWithLength(patterns, 2).get(0);
        for(Character c : valuesFor1.toCharArray()) {
            candidateValues.get('a').remove(c);
            candidateValues.get('b').remove(c);
            //candidateValues.get('c').add(c);
            candidateValues.get('d').remove(c);
            candidateValues.get('e').remove(c);
            //candidateValues.get('f').add(c);
            candidateValues.get('g').remove(c);
        }

        String valuesFor4 = getWithLength(patterns, 4).get(0);
        candidateValues.get('b').clear();
        candidateValues.get('d').clear();
        for(Character c : valuesFor4.toCharArray()) {
            candidateValues.get('a').remove(c);
            candidateValues.get('b').add(c);
            //candidateValues.get('c').add(c);
            candidateValues.get('d').add(c);
            candidateValues.get('e').remove(c);
            //candidateValues.get('f').add(c);
            candidateValues.get('g').remove(c);
        }
        for(Character c : valuesFor7.toCharArray()) {
            if (valuesFor1.indexOf(c) == -1) {
                candidateValues.get('a').clear();
                candidateValues.get('a').add(c);
                candidateValues.get('b').remove(c);
                candidateValues.get('c').remove(c);
                candidateValues.get('d').remove(c);
                candidateValues.get('e').remove(c);
                candidateValues.get('f').remove(c);
                candidateValues.get('g').remove(c);
                break;
            }
        }
        // We know 'a' and we have 2 candidates for 'c' and 'f'
    }

    private List<String> getWithLength(String[] patterns, int i) {
        List<String> output = new ArrayList<>();
        for(String pattern : patterns) {
            if (pattern.length() == i) {
                output.add(pattern);
            }
        }
        return output;
    }

    private List<Sample> getInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("/Users/marc/workspace/adventofcode/resources/input8.txt"));
        List<Sample> inputs = new ArrayList<>();
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            String[] patterns = line.split("\\|")[0].trim().split(" ");
            String[] outputValues = line.split("\\|")[1].trim().split(" ");
            inputs.add(new Sample(patterns, outputValues));
        }

        scanner.close();
        return inputs;
    }
    public class Sample {
        public Sample(String[] patterns, String[] outputValues) {
            this.patterns=patterns;
            this.outputValues=outputValues;
        }
        String[] patterns;
        String[] outputValues;
    }
}

