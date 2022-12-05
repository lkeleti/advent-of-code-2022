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
        commandList.clear();
        crates.clear();
        firstLines.clear();
    }
    public void readInput(Path path) {
        init();
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            Boolean isCommands = false;
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
//        for (int i =firstLines.size()-2; i>=0;i--) {
//        }
        crates.put(1,new ArrayList<>());
        crates.get(1).add("W");
        crates.get(1).add("R");
        crates.get(1).add("F");
        crates.put(2,new ArrayList<>());
        crates.get(2).add("T");
        crates.get(2).add("H");
        crates.get(2).add("M");
        crates.get(2).add("C");
        crates.get(2).add("D");
        crates.get(2).add("V");
        crates.get(2).add("W");
        crates.get(2).add("P");
        crates.put(3,new ArrayList<>());
        crates.get(3).add("P");
        crates.get(3).add("M");
        crates.get(3).add("Z");
        crates.get(3).add("N");
        crates.get(3).add("L");
        crates.put(4,new ArrayList<>());
        crates.get(4).add("J");
        crates.get(4).add("C");
        crates.get(4).add("H");
        crates.get(4).add("R");
        crates.put(5,new ArrayList<>());
        crates.get(5).add("C");
        crates.get(5).add("P");
        crates.get(5).add("G");
        crates.get(5).add("H");
        crates.get(5).add("Q");
        crates.get(5).add("T");
        crates.get(5).add("B");
        crates.put(6,new ArrayList<>());
        crates.get(6).add("G");
        crates.get(6).add("C");
        crates.get(6).add("W");
        crates.get(6).add("L");
        crates.get(6).add("F");
        crates.get(6).add("Z");
        crates.put(7,new ArrayList<>());
        crates.get(7).add("W");
        crates.get(7).add("V");
        crates.get(7).add("L");
        crates.get(7).add("Q");
        crates.get(7).add("Z");
        crates.get(7).add("J");
        crates.get(7).add("G");
        crates.get(7).add("C");
        crates.put(8,new ArrayList<>());
        crates.get(8).add("P");
        crates.get(8).add("N");
        crates.get(8).add("R");
        crates.get(8).add("F");
        crates.get(8).add("W");
        crates.get(8).add("T");
        crates.get(8).add("V");
        crates.get(8).add("C");
        crates.put(9,new ArrayList<>());
        crates.get(9).add("J");
        crates.get(9).add("W");
        crates.get(9).add("H");
        crates.get(9).add("G");
        crates.get(9).add("R");
        crates.get(9).add("S");
        crates.get(9).add("V");
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
        String result = "";
        for (int key: crates.keySet()) {
            List<String> letters = crates.get(key);
            result += letters.get(letters.size()-1);
        }
        return result;
    }
}
