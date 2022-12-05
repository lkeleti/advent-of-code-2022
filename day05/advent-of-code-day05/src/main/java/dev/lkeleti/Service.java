package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Service {
    private List<Command> commandList;
    private Map<Integer, List<String>> crates;
    private List<String> firstLines = new ArrayList<>();

    private void init() {
        commandList = new ArrayList<>();
        crates =  new TreeMap<>();
        firstLines = new ArrayList<>();
    }
    public void readInput(Path path) {
        init();
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            boolean isCommands = false;
            while ((line = br.readLine()) != null) {
                if (isCommands) {
                    commandFromLine(line);
                }
                else if (line.isEmpty()) {
                    isCommands = true;
                    createCrates();
                }
                else {
                    firstLines.add(line);
                }
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path);
        }
    }

    private void createCrates() {
        for (int i =firstLines.size()-2; i>=0;i--) {
            int lengthDefaultRow = (firstLines.get(i).length()-2)/4;
            for (int j = 0; j <= lengthDefaultRow;j++){
                crates.computeIfAbsent(j+1,k-> new ArrayList<>());
                int startIndex = (j * 4) + 1;
                String defaultLetter = firstLines.get(i).substring(startIndex,startIndex+1);
                if (!defaultLetter.isBlank()) {
                    crates.get(j+1).add(defaultLetter);
                }
            }
        }
    }

    private void commandFromLine(String line) {
        String[] datas = line.trim().split(" ");
        commandList.add(
                new Command(
                        Integer.parseInt(datas[1]),
                        Integer.parseInt(datas[3]),
                        Integer.parseInt(datas[5])
                )
        );
    }

    private void moveCrates() {
        for (Command command: commandList) {
            for (int i = 0; i < command.getNumberOfMoves(); i++) {
                List<String> fromList = crates.get(command.getFromColumn());
                List<String> toList = crates.get(command.getToColumn());
                String element = fromList.get(fromList.size()-1);
                fromList.remove(fromList.size()-1);
                toList.add(element);
            }
        }
    }

    private void moveCrates9001() {
        for (Command command: commandList) {
            List<String> elements = new ArrayList<>();
            List<String> toList = crates.get(command.getToColumn());
            for (int i = 0; i < command.getNumberOfMoves(); i++) {
                List<String> fromList = crates.get(command.getFromColumn());
                elements.add(fromList.get(fromList.size()-1));
                fromList.remove(fromList.size()-1);
            }

            for (int i = elements.size()-1; i >= 0; i--) {
                toList.add(elements.get(i));
            }
        }
    }

    public String listLastLetters(String part) {
        if (part.equals("p1")) {
            moveCrates();
        }
        else {
            moveCrates9001();
        }
        StringBuilder result = new StringBuilder();
        for (Map.Entry<Integer,List<String>> letters:crates.entrySet()) {
            result.append(letters.getValue().get(letters.getValue().size() - 1));
        }
        return result.toString();
    }
}
