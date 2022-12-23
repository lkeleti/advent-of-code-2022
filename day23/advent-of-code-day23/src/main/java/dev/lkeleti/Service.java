package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {
private List<Elf> elves = new ArrayList<>();

    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                readElvesFromLine(line.trim(), row);
                row ++;
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path);
        }
    }

    private void readElvesFromLine(String line,int row) {
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '#') {
                elves.add(new Elf(new Position(i,row)));
            }
        }
    }
}
