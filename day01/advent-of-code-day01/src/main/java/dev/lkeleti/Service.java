package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private List<Long> sumCalories = new ArrayList<>();

    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            long calories = 0;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) {
                    sumCalories.add(calories);
                    calories = 0;
                }
                else {
                    calories += Long.parseLong(line);
                }
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path);
        }
    }

    public List<Long> getSumCalories() {
        return sumCalories;
    }
}
