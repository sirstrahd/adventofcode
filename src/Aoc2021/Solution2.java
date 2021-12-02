package Aoc2021;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution2 {
    List<String> inputs = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Solution2 solution = new Solution2();
        solution.execute2();
    }

    public void execute() throws FileNotFoundException {
        getInput();
        int depth = 0, pos = 0;
        for(int i=0;i<inputs.size();i++) {
            String[] command = inputs.get(i).split(" ");
            Long value = Long.parseLong(command[1]);
            switch (command[0]) {
                    case "forward": pos+=value; break;
                    case "down": depth+=value; break;
                    case "up": depth-=value; break;
            }

        }
        System.out.println("Result : " + depth + " " + pos +" total: " + depth*pos);
    }

    public void execute2() throws FileNotFoundException {
        getInput();
        int aim = 0, pos = 0, depth=0;
        for(int i=0;i<inputs.size();i++) {
            String[] command = inputs.get(i).split(" ");
            Long value = Long.parseLong(command[1]);
            switch (command[0]) {
                case "forward": pos+=value; depth+=(aim*value); break;
                case "down": aim+=value; break;
                case "up": aim-=value; break;
            }

        }
        System.out.println("Result : " + depth + " " + pos +" total: " + depth*pos);
    }



    private void getInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("/Users/marc/Desktop/input2.txt"));
        List<String> output = new ArrayList<>();
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line != "") {
                inputs.add(line);
            }
        }
        scanner.close();
    }

}
