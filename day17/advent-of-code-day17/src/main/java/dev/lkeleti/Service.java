package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {

    private char[] jetPattern;
    private List<Rock> rocks = new ArrayList<>();
    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                jetPattern = line.trim().toCharArray();
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path);
        }
    }

    public long simulate() {
        int counter = 2022;
        int pushCounter = 0;
        int maxHeight = 0;

        rocks.add(new Rock(0));
        while (counter > 0 ) {
            maxHeight = rocks.stream().mapToInt(r->r.getPosY()+r.getHeight()).max().orElse(0) + 3;
            Rock defaultRock = new Rock(maxHeight);
            boolean canMove = true;
            while (canMove) {
                if (defaultRock.canMoveSide(jetPattern[pushCounter]) && !checkCollideSide(defaultRock, jetPattern[pushCounter])) {
                    defaultRock.moveSide(jetPattern[pushCounter]);
                }

                if (pushCounter == jetPattern.length - 1) {
                    pushCounter = 0;
                } else {
                    pushCounter++;
                }

                if (!checkCollideDown(defaultRock)) {
                    defaultRock.setPosY(defaultRock.getPosY() - 1);
                } else {
                    canMove = false;
                }

                Rock testRock = new Rock(defaultRock);
                if (canMove && checkCollideDown(testRock)) {
                    canMove = false;
                    if (testRock.canMoveSide(jetPattern[pushCounter]) && !checkCollideSide(testRock, jetPattern[pushCounter])) {
                        testRock.moveSide(jetPattern[pushCounter]);
                        if (!checkCollideDown(testRock)) {
                            canMove = true;
                        }
                    }
                }
            }

            counter--;
            rocks.add(defaultRock);
        }
        return rocks.stream().mapToInt(r->r.getPosY()+r.getHeight()).max().getAsInt();
    }

    private boolean checkCollideSide(Rock defaultRock, char direction) {
        Rock testRock = new Rock(defaultRock);
        testRock.moveSide(direction);
        for(Rock otherRock : rocks) {
            if (testRock.isCollide(otherRock)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkCollideDown(Rock defaultRock) {
        Rock testRock = new Rock(defaultRock);
        testRock.setPosY(testRock.getPosY()-1);
        for(Rock otherRock : rocks) {
            if (testRock.isCollide(otherRock)) {
                return true;
            }
        }
        return false;
    }
}
