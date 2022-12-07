package Aoc2022;

import Common.InputParser;

import java.util.*;

public class Solution7 {

    private final Folder rootFolder = new Folder(null);
    private Folder currentFolder = rootFolder;

    private final List<Long> smallSizes = new LinkedList<>();
    private final List<Long> allSizes = new LinkedList<>();

    public static void main(String[] args) throws Exception {
        Solution7 solution = new Solution7();
        solution.execute();
    }

    public void execute() throws Exception {
        List<String> input = InputParser.getLinesAsListOfStrings("resources/2022/input7.txt");
        part1(input);
        part2();
    }

    private void part2() {
        List<Long> sortedSizes = allSizes.stream().sorted().toList();
        Long totalUsed = sortedSizes.get(sortedSizes.size()-1);
        Long freeSpace = 70000000L - totalUsed;
        Long necessary = 30000000L;
        Long needToFree = necessary - freeSpace;
        System.out.println("need to free: " + needToFree);
        for (Long sortedSize : sortedSizes) {
            if (sortedSize > needToFree) {
                System.out.println("can delete folder with size: " + sortedSize);
                break;
            }
        }
    }

    private void part1(List<String> input) {
        LinkedList<Command> commands = new LinkedList<>();
        for(String commandString: input) {
            if (commandString.startsWith("$")) {
                Command cmd = transformToCommand(commandString);
                commands.add(cmd);
            } else {
                Command cmd = commands.getLast();
                cmd.outputLines.add(commandString);
            }
        }
        for(Command command : commands) {
            command.execute();
        }
        outputSizes("/", rootFolder);
        Long total = smallSizes.stream().reduce(Long::sum).get();
        System.out.println("Total for small folders: " + total);
    }

    private void outputSizes(String name, Folder folder) {
        Long size = outputSize(folder);
        System.out.println("Size for folder " + name + " : " + size);
        if (size <= 100000) {
            smallSizes.add(size);
        }
        allSizes.add(size);
        for(String subFolderName : folder.subdirectories.keySet()) {
            outputSizes(subFolderName, folder.subdirectories.get(subFolderName));
        }
    }

    private Long outputSize(Folder folder) {
        Long fileSize = folder.files.values().stream().map(x -> x.size).reduce(Long::sum).orElse(0L);
        Long childrenSize = folder.subdirectories.values().stream().map(this::outputSize).reduce(Long::sum).orElse(0L);
        return fileSize + childrenSize;
    }

    private Command transformToCommand(String input) {
        CommandType type;
        Optional<String> param = Optional.empty();
        switch (input) {
            case "$ ls" -> type = CommandType.LS;
            case "$ cd .." -> type = CommandType.CDPARENT;
            case "$ cd /" -> type = CommandType.CDROOT;
            default -> {
                type = CommandType.CD;
                param = Optional.of(input.split(" ")[2]);
            }
        }
        return new Command(type, param);
    }


    private void addChild(Folder folder, String outputLine) {
        String[] values = outputLine.split(" ");
        if (values[0].equals("dir")) {
            if (!folder.subdirectories.containsKey(values[1])) {
                folder.subdirectories.put(values[1], new Folder(folder));
            }
        } else {
            File file = new File(Long.parseLong(values[0]));
            folder.files.put(values[1], file);
        }
    }

    public enum CommandType {
        CD, CDPARENT, CDROOT, LS
    }

    public class Command {
        CommandType type;

        Optional<String> param;

        List<String> outputLines;

        public Command(CommandType type, Optional<String> param) {
            this.type = type;
            this.param = param;
            this.outputLines = new LinkedList<>();
        }

        public void execute() {
            switch(type) {
                case CD -> {
                    if (currentFolder.subdirectories.containsKey(param.get())) {
                        currentFolder = currentFolder.subdirectories.get(param.get());
                    }
                }
                case CDPARENT -> currentFolder = currentFolder.parent;
                case CDROOT -> currentFolder = rootFolder;
                case LS -> {
                    for(String outputLine : outputLines) {
                        addChild(currentFolder, outputLine);
                    }
                }
            }
        }
    }


    public static class File {
        Long size;
        public File(Long size) {
            this.size=size;
        }
    }
    public static class Folder {

        public Folder(Folder parent) {
            this.parent = parent;
        }
        private final Map<String, Folder> subdirectories = new HashMap<>();
        private final Map<String, File> files = new HashMap<>();

        private final Folder parent;
    }

}
