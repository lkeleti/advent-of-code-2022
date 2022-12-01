package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {
    public List<Long> sumCalories = new ArrayList<>();

    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            Long calories = Long.valueOf(0);
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) {
                    sumCalories.add(calories);
                    calories = Long.valueOf(0);
                }
                else {
                    calories += Long.parseLong(line);
                }
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path);
        }
    }
}
