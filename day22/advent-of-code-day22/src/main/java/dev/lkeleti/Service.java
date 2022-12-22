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
            boolean tableB = true;
            while ((line = br.readLine()) != null) {
                if (tableB) {
                    tableB = readTableFromLine(line);
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
        commands.add(command);
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
                defPos = new Position(i, 0, 'R');
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

    public long simulate() {
        for (String command: commands) {
            if (command.equals("L") || command.equals("R")) {
                char defDirection = defPos.getDirection();
                if (command.equals("L")) {
                    turnLeft(defDirection);
                } else if (command.equals("R")) {
                    turnRight(defDirection);
                }
            } else {
                int steps = Integer.parseInt(command);
                for (int i = 0; i < steps; i++) {
                    char defDir = defPos.getDirection();
                    switch (defDir) {
                        case 'L':
                            moveLeft();
                            break;
                        case 'R':
                            moveRight();
                            break;
                        case 'U':
                            moveUp();
                            break;
                        default:
                            moveDown();
                            break;
                    }
                }
            }
        }
        int facing;
        switch (defPos.getDirection()) {
            case 'R':
                facing = 0;
                break;
            case 'D':
                facing = 1;
                break;

            case 'L':
                facing = 2;
                break;
            default:
                facing = 3;
                break;


        }
        return (defPos.getPosY()+1) * 1000 + (defPos.getPosX()+1) * 4 + facing;
    }

    private void moveUp() {
        Position nextPos = new Position(defPos);
        nextPos.decY();
        if (nextPos.getPosY() < 0 || table.get(nextPos.getPosY()).get(nextPos.getPosX()) == ' ') {
            for (int i = table.size()-1; i >= 0; i--) {
                if (table.get(i).get(defPos.getPosX()) == '#') {
                    break;
                } else if (table.get(i).get(defPos.getPosX()) == '.') {
                    defPos = new Position(defPos.getPosX(), i, defPos.getDirection());
                    break;
                }
            }
        }
        else if (table.get(nextPos.getPosY()).get(nextPos.getPosX()) == '.') {
            defPos = nextPos;
        }
    }

    private void moveDown() {
        Position nextPos = new Position(defPos);
        nextPos.incY();
        if (nextPos.getPosY() >= table.size() || table.get(nextPos.getPosY()).get(nextPos.getPosX()) == ' ') {
            for (int i = 0; i <  table.size(); i++) {
                if (table.get(i).get(defPos.getPosX()) == '#') {
                    break;
                } else if (table.get(i).get(defPos.getPosX()) == '.') {
                    defPos = new Position(defPos.getPosX(), i, defPos.getDirection());
                    break;
                }
            }
        }
        else if (table.get(nextPos.getPosY()).get(nextPos.getPosX()) == '.') {
            defPos = nextPos;
        }
    }

    private void moveRight() {
        Position nextPos = new Position(defPos);
        nextPos.incX();
        if (nextPos.getPosX() >= table.get(0).size() || table.get(nextPos.getPosY()).get(nextPos.getPosX()) == ' ') {
            for (int i = 0; i < table.get(0).size(); i++) {
                if (table.get(defPos.getPosY()).get(i) == '#') {
                    break;
                } else if (table.get(defPos.getPosY()).get(i) == '.') {
                    defPos = new Position(i, defPos.getPosY(), defPos.getDirection());
                    break;
                }
            }
        }
        else if (table.get(nextPos.getPosY()).get(nextPos.getPosX()) == '.') {
            defPos = nextPos;
        }
    }

    private void moveLeft() {
        Position nextPos = new Position(defPos);
        nextPos.decX();
        if (nextPos.getPosX() < 0 || table.get(nextPos.getPosY()).get(nextPos.getPosX()) == ' ') {
            for (int i = table.get(0).size()-1; i >= 0; i--) {
                if (table.get(defPos.getPosY()).get(i) == '#') {
                    break;
                } else if (table.get(defPos.getPosY()).get(i) == '.') {
                    defPos = new Position(i, defPos.getPosY(), defPos.getDirection());
                    break;
                }
            }
        }
        else if (table.get(nextPos.getPosY()).get(nextPos.getPosX()) == '.') {
            defPos = nextPos;
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
