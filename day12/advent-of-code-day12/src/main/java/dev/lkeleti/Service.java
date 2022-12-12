package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {

    private List<List<Character>> hill = new ArrayList<>();
    List<List<Position>> goodWays = new ArrayList<>();
    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                hill.add(readRowFromLine(line.trim()));
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path);
        }
    }

    private List<Character> readRowFromLine(String line) {
        List<Character> row = new ArrayList<>();
        for (char c: line.toCharArray()) {
            row.add(c);
        }
        return row;
    }

    public void findShortestWay() {
        Position start = findStart();

    }
    private Position findStart() {
        for (int x = 0 ; x < hill.get(0).size(); x++) {
            for (int y = 0; y < hill.size(); y++) {
                if (hill.get(y).get(x) == 'S') {
                    return new Position(x,y);
                }
            }
        }
        throw new IllegalArgumentException("Can't find start position.");
    }
}
