package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {

    private final List<String> lines = new ArrayList<>();

    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line.trim());
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path);
        }
    }

    public long calculateSums() {
        long total = 0;

        for (String line: lines) {
            total += calculatePriority(line);
        }
        return total;
    }

    public long calculateSumsOfThree() {
        long total = 0;

        for (int i=0; i < lines.size();i+=3) {
            total += calculatePriorityOfThree(lines.get(i), lines.get(i+1), lines.get(i+2));
        }
        return total;
    }

    private long calculatePriority(String line) {
        String firstHalf = line.substring(0,line.length()/2);
        String secondHalf = line.substring(line.length()/2);

        for (char c: firstHalf.toCharArray()) {
            if (secondHalf.contains(String.valueOf(c)))  {
                return c >96?c-96:c-38;
            }
        }
        return 0;
    }

    private long calculatePriorityOfThree(String first, String second, String third) {
        List<Character> same = new ArrayList<>();
        for (char c: first.toCharArray()) {
            if (second.contains(String.valueOf(c)))  {
                same.add(c);
            }
        }

        for (char c: same) {
            if (third.contains(String.valueOf(c)))  {
                return c >96?c-96:c-38;
            }
        }
        return 0;
    }
}
