package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Service {

    private final List<List<Character>> table = new ArrayList<>();
    private String command;
    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            boolean table = true;
            while ((line = br.readLine()) != null) {
                if (table) {
                    table = readTableFromLine(line);
                }
                else {
                    command = line.trim();
                }
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path);
        }
        reCalcLength();
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
}
