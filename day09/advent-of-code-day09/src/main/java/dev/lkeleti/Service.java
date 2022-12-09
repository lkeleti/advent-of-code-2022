package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Service {

    private List<Command> commandList = new ArrayList<>();
    private Position defaultPosition = new Position(0,0);
    private Set<Position> tailPositions = new HashSet<>();
    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                readCommandFromLine(line.trim());
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path);
        }
    }

    private void readCommandFromLine(String line) {
        String[] datas = line.split(" ");
        commandList.add(
                new Command(datas[0].charAt(0), Integer.parseInt(datas[1]))
        );
    }

    public void processMoves() {
        for (Command command: commandList) {
            move(command);
        }
        System.out.println(defaultPosition.getxPos());
        System.out.println(defaultPosition.getyPos());
    }

    private void move(Command command) {
        switch (command.getDirection()) {
            case 'U':
                defaultPosition.moveY(command.getDistance());
                break;
            case 'D':
                defaultPosition.moveY(-command.getDistance());
                break;
            case 'L':
                defaultPosition.moveX(-command.getDistance());
                break;
            case 'R':
                defaultPosition.moveX(command.getDistance());
                break;
            default:
                break;
        }
    }
}
