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
        if (checkLeft(column, row)) {
            return true;
        }

        if (checkRight(column, row)) {
            return true;
        }

        if (checkTop(column, row)) {
            return true;
        }

        if (checkBottom(column, row)) {
            return true;
        }

        return false;
    }

    private boolean checkLeft(int column, int row) {
        int height = treeTable.get(column).get(row);
        for (int i = row-1; i >=0; i-- ) {
            if (treeTable.get(column).get(i) >= height) {
                return false;
            }
        }
        return true;
    }

    private boolean checkRight(int column, int row) {
        int height = treeTable.get(column).get(row);
        for (int i = row+1; i <treeTable.get(column).size(); i++ ) {
            if (treeTable.get(column).get(i) >= height) {
                return false;
            }
        }
        return true;
    }

    private boolean checkTop(int column, int row) {
        int height = treeTable.get(column).get(row);
        for (int i = column-1; i >=0; i-- ) {
            if (treeTable.get(i).get(row) >= height) {
                return false;
            }
        }
        return true;
    }

    private boolean checkBottom(int column, int row) {
        int height = treeTable.get(column).get(row);
        for (int i = column+1; i <treeTable.size(); i++ ) {
            if (treeTable.get(i).get(row) >= height) {
                return false;
            }
        }
        return true;
    }

}
