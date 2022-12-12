package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Service {

    private final List<Command> commandList = new ArrayList<>();
    private final List<Position> snake = new ArrayList<>();
    private final Set<Position> tailPositions = new TreeSet<>(Comparator.comparing(Position::toString));
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

    public int processMoves(int num) {
        snake.clear();
        for (int i = 0; i < num; i++) {
            snake.add(new Position(0,0));
        }
        tailPositions.clear();

        for (Command command: commandList) {
            move(command);
        }
        return tailPositions.size();
    }

    private void move(Command command) {
        for (int i = 0; i < command.getDistance(); i++) {
            Position defaultHeadPosition = snake.get(0);
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
            for (int j = 0; j < snake.size()-1; j++) {
                Position tailPos = moveTail(snake.get(j).getxPos(), snake.get(j).getyPos(), snake.get(j+1).getxPos(), snake.get(j+1).getyPos());
                snake.get(j+1).setxPos(tailPos.getxPos());
                snake.get(j+1).setyPos(tailPos.getyPos());
                if (j+1 == snake.size()-1) {
                    tailPositions.add(new Position(tailPos.getxPos(), tailPos.getyPos()));
                }
            }
        }
    }

    private Position moveTail(int headX, int headY, int tailX, int tailY) {
        int differentX = headX - tailX;
        int differentY = headY - tailY;

        if (Math.abs(differentX) < 2 && Math.abs(differentY) < 2) {
            return new Position(tailX, tailY);
        }

        if (differentX == 0) {
            // same col
            if (Math.abs(differentY) > 1) {
                tailY += (differentY/2);
            }
        } else if (differentY == 0) {
            // same row  ...T. H...
            if (Math.abs(differentX) > 1) {
                tailX += (differentX/2);
            }
        }
        else {
            // different col and row
            if (Math.abs(differentY) == 1) {
                tailY = headY;
                tailX += (differentX/2);

            }
            if (Math.abs(differentX) == 1) {
                tailX = headX;
                tailY += (differentY/2);
            }
        }
        return new Position(tailX, tailY);
    }
}
