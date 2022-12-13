package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Service {

    private final List<List<Character>> hill = new ArrayList<>();
    private int maxX;
    private int maxY;

    private Position startPosition;
    private Position endPosition;
    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                hill.add(readRowFromLine(line.trim()));
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path);
        }
        maxX = hill.get(0).size();
        maxY = hill.size();
    }

    private List<Character> readRowFromLine(String line) {
        List<Character> row = new ArrayList<>();
        for (char c: line.toCharArray()) {
            row.add(c);
        }
        return row;
    }

    public int partOne() {
        findStartEnd();
        List<Position> starts = new ArrayList<>(List.of(startPosition));
        return bfs(starts,endPosition);
    }

    public int partTwo() {
        List<Position> starts = new ArrayList<>();
        for (int x = 0 ; x < hill.get(0).size(); x++) {
            for (int y = 0; y < hill.size(); y++) {
                if (hill.get(y).get(x) == 'a') {
                    starts.add(new Position(x,y));
                }
            }
        }
        return bfs(starts,endPosition);
    }
    private int bfs(List<Position> starts, Position end) {
        Queue<PositionDistance> q = new LinkedList<>();
        Set<Position> visited = new LinkedHashSet<>();
        List<Position> dirs = new ArrayList<>(List.of(
                new Position(1,0),
                new Position(0,1),
                new Position(-1,0),
                new Position(0,-1)
        ));

        for (Position s: starts) {
            visited.add(s);
            q.add(new PositionDistance(s,0));
        }

        while (!q.isEmpty()) {
            int l = q.size();
            for (int i = 0; i < l; i++) {
                PositionDistance defPD = q.poll();
                Position defPos = defPD.getPosition();
                int defDist = defPD.getDistance();

                for (Position d: dirs) {
                    int newX = defPos.getPosX() + d.getPosX();
                    int newY = defPos.getPosY() + d.getPosY();

                    Position newPoint = new Position(newX, newY);

                    if (newX >= 0 && newX < maxX && newY >= 0 && newY < maxY &&
                            hill.get(newY).get(newX) - hill.get(defPos.getPosY()).get(defPos.getPosX()) <= 1 &&
                            !visited.contains(newPoint)
                    ) {
                        visited.add(newPoint);
                        q.add(new PositionDistance(new Position(newX, newY), defDist + 1));
                        if (newPoint.equals(end)) {
                            return defDist + 1;
                        }
                    }
                }
            }
        }
        throw new IllegalArgumentException("Can't find end point!");
    }

    private void findStartEnd() {
        for (int x = 0 ; x < hill.get(0).size(); x++) {
            for (int y = 0; y < hill.size(); y++) {
                if (hill.get(y).get(x) == 'S') {
                    hill.get(y).set(x, 'a');
                    startPosition = new Position(x,y);
                }
                if (hill.get(y).get(x) == 'E') {
                    hill.get(y).set(x, 'z');
                    endPosition = new Position(x,y);
                }
            }
        }
    }
}
