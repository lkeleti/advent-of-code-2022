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

    private final List<Command> commandList = new ArrayList<>();
    private final Position defaultHeadPosition = new Position(0,0);
    private final Position defaultTailPosition = new Position(0,0);
    private final Set<Position> tailPositions = new HashSet<>();
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
        System.out.println(defaultHeadPosition.getxPos());
        System.out.println(defaultHeadPosition.getyPos());
        System.out.println(tailPositions.size());
    }

    private void move(Command command) {
        for (int i = 0; i < command.getDistance(); i++) {
            switch (command.getDirection()) {
                case 'U':
                    defaultHeadPosition.moveY(1);
                    break;
                case 'D':
                    defaultHeadPosition.moveY(-1);
                    break;
                case 'L':
                    defaultHeadPosition.moveX(-1);
                    break;
                case 'R':
                    defaultHeadPosition.moveX(1);
                    break;
                default:
                    break;
            }
            moveTail();
        }
    }

    private void moveTail() {
        int differentX = defaultHeadPosition.getxPos() - defaultTailPosition.getxPos();
        int differentY = defaultHeadPosition.getyPos() - defaultTailPosition.getyPos();

        if (differentX == 0 && differentY == 0) {
            tailPositions.add(defaultTailPosition);
            return;
        }
        if (differentX == 0) {
            // same col
            if (Math.abs(differentY) > 1) {
                defaultTailPosition.moveX(differentX);
            }
        } else if (differentY == 0) {
            // same row  ...T. H...
            if (Math.abs(differentX) > 1) {
                defaultTailPosition.moveY(differentY);
            }
        }
        else {
            // different col and row
            if (Math.abs(differentY) == 1) {
                defaultTailPosition.setyPos(defaultHeadPosition.getyPos());
                defaultTailPosition.moveX(differentX/2);
            }
            if (Math.abs(differentX) == 1) {
                defaultTailPosition.setxPos(defaultHeadPosition.getxPos());
                defaultTailPosition.moveY(differentY/2);
            }
        }
        tailPositions.add(defaultTailPosition);
    }
}
