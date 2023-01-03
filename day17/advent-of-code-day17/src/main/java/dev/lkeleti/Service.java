package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Service {

    private char[] jetPattern;
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

    private long simulate() {
        int counter = 2022;
        int pushCounter = 0;

        while (counter > 0 ) {
            // max height of board + 4 the start height
            boolean canMove = true;
            while (canMove) {
                // push
                if (pushCounter == jetPattern.length - 1) {
                    pushCounter = 0;
                } else {
                    pushCounter++;
                }
                //moveDown, if can"t move then
                canMove = false;
            }
            counter--;
        }
        return 0;
    }
}
