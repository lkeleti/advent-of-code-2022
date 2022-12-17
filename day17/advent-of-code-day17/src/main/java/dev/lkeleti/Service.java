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
}
