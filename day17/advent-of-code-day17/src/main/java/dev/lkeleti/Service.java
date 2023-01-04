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

        while (counter > 0 ) {
            maxHeight = rocks.stream().mapToInt(r->r.getPosY()+r.getHeight()).max().orElse(0) + 4;
            Rock defaultRock = new Rock(maxHeight);
            boolean canMove = true;
            while (canMove) {
                if (!defaultRock.canMoveSide(jetPattern[pushCounter])) {
                    defaultRock.moveSide(jetPattern[pushCounter]);
                }
                boolean isCollide = false;
                for(Rock otherRock : rocks) {
                    if (defaultRock.isCollide(otherRock)) {
                        if ( jetPattern[pushCounter] == '>') {
                            defaultRock.moveSide('<');
                        }
                        else {
                            defaultRock.moveSide('>');
                        }
                    }
                    break;
                }

                if (pushCounter == jetPattern.length - 1) {
                    pushCounter = 0;
                } else {
                    pushCounter++;
                }

                defaultRock.setPosY(defaultRock.getPosY() - 1);
                if (!rocks.isEmpty()) {
                    for (Rock otherRock : rocks) {
                        if (defaultRock.isCollide(otherRock)) {
                            defaultRock.setPosY(defaultRock.getPosY() + 1);
                            canMove = false;
                            break;
                        }
                    }
                }
                else {
                    if (defaultRock.getPosY() == 0) {
                        canMove = false;
                    }
                }
            }
            counter--;
            rocks.add(defaultRock);
        }
        return rocks.stream().mapToInt(r->r.getPosY()+r.getHeight()).max().getAsInt();
    }
}
