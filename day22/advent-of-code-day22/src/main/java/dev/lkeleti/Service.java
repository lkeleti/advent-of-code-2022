package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Service {

    private Position defPos;
    private final List<List<Character>> table = new ArrayList<>();
    private List<String> commands = new ArrayList<>();

    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            boolean table = true;
            while ((line = br.readLine()) != null) {
                if (table) {
                    table = readTableFromLine(line);
                }
                else {
                    getCommands(line.trim());
                }
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path);
        }
        reCalcLength();
    }

    private void getCommands(String line) {
        String command = "";
        for (Character c: line.toCharArray()) {
            if (c >= 47 && c<= 57) {
                command = command + c;
            }
            else {
                commands.add(command);
                commands.add(c.toString());
                command = "";
            }
        }
    }

    private void reCalcLength() {
        int maxCol = 0;
        for (List<Character> row: table) {
            if (row.size() > maxCol) {
                maxCol = row.size();
            }
        }

        for (List<Character> row: table) {
            int rowSize = row.size();
            if (rowSize < maxCol) {
                for (int i = 0; i < maxCol - rowSize; i++) {
                    row.add(' ');
                }
            }
        }
        findStart();
    }

    private void findStart() {
        for (int i = 0; i < table.get(0).size(); i++) {
            if (table.get(0).get(i) != ' ') {
                defPos = new Position(i, 0, 'L');
                break;
            }
        }
    }

    private boolean readTableFromLine(String line) {
        if (!line.isBlank()) {
            List<Character> chars = new ArrayList<>();
            for (char c : line.toCharArray()) {
                chars.add(c);
            }
            table.add(chars);
            return true;
        }
        else {
            return false;
        }
    }

    private void simulate() {
        for (String command: commands) {
            if (command.equals("L") || command.equals("R")) {
                char defDirection = defPos.getDirection();
                if (command.equals("L")) {
                    turnLeft(defDirection);
                } else if (command.equals("R")) {
                    turnRight(defDirection);
                } else {
                    //step ahead
                }
            }
        }
    }

    private void turnRight(char defDirection) {
        if (defDirection == 'L') {
            defPos.setDirection('U');
        } else if (defDirection == 'D') {
            defPos.setDirection('L');
        } else if (defDirection == 'R') {
            defPos.setDirection('D');
        }
        else {
            defPos.setDirection('R');
        }
    }

    private void turnLeft(char defDirection) {
        if (defDirection == 'L') {
            defPos.setDirection('D');
        } else if (defDirection == 'D') {
            defPos.setDirection('R');
        } else if (defDirection == 'R') {
            defPos.setDirection('U');
        }
        else {
            defPos.setDirection('L');
        }
    }
}
