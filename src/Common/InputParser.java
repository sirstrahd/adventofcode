package Common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputParser {
    public static String[] getLinesAsArrayOfStrings(String inputFilename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(inputFilename));
        List<String> result = new ArrayList<>();
        while(scanner.hasNextLine()) {
            result.add(scanner.nextLine().trim());
        }
        scanner.close();
        return result.toArray(new String[0]);
    }

    public static List<String> getLinesAsListOfStrings(String inputFilename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(inputFilename));
        List<String> result = new ArrayList<>();
        while(scanner.hasNextLine()) {
            result.add(scanner.nextLine());
        }
        scanner.close();
        return result;
    }

    public static List<List<String>> splitByEmptyLines(List<String> inputLines) {
        List<List<String>> result = new ArrayList<>();
        List<String> currentResult = new ArrayList<>();
        result.add(currentResult);
        for(String line : inputLines) {
            if (!line.isBlank()) {
                currentResult.add(line);
            } else {
                currentResult = new ArrayList<>();
                result.add(currentResult);
            }
        }
        return result;
    }
}
