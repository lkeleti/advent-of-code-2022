package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private final List<List<Integer>> treeTable = new ArrayList<>();

    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                treeTable.add(getRowsFromLine(line.trim()));
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path);
        }
    }

    private List<Integer> getRowsFromLine(String line) {
        List<Integer> row = new ArrayList<>();
        for (Character c: line.toCharArray()) {
            row.add(Integer.parseInt(c.toString()));
        }
        return row;
    }

    public long countVisible() {
        long total = 2L * treeTable.size();
        total += (treeTable.get(0).size() - 2) * 2L;

        for (int i = 1; i < treeTable.size()-1; i++) {
            for (int j = 1; j < treeTable.get(0).size()-1; j++) {
                if (isVisible(i,j)) {
                    total += 1;
                }
            }
        }

        return total;
    }

    private boolean isVisible(int column, int row) {
        int height = treeTable.get(column).get(row);
        for (int i = 0; i < treeTable.size(); i++) {
            if (i != column && treeTable.get(i).get(row) < height) {
                return true;
            }
        }

        for (int j = 0; j < treeTable.get(0).size(); j++) {
            if (j != row && treeTable.get(column).get(j) < height) {
                return true;
            }
        }
        return false;
    }

}
