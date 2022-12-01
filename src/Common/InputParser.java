package Common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class InputParser {
    public static String[] getLinesAsArrayListOfStrings(String inputFilename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(inputFilename));
        ArrayList<String> result = new ArrayList<>();
        while(scanner.hasNextLine()) {
            result.add(scanner.nextLine().trim());
        }
        scanner.close();
        return result.toArray(new String[0]);
    }
}
