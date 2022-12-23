package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Service {
private List<Elf> elves = new ArrayList<>();
private Map<Direction,Direction> directionMap = new HashMap<>();

    public Service() {
        directionMap.put(Direction.N, Direction.S);
        directionMap.put(Direction.S, Direction.W);
        directionMap.put(Direction.W, Direction.E);
        directionMap.put(Direction.E, Direction.N);
    }

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

    public long simulate() {
        plan();
        checkSamePositions();
        move();
        return 0L;
    }

    private void plan() {
        for (Elf elf : elves) {
            Direction defDirection = elf.getDirection();
            switch(defDirection) {
                case E: checkEast();
                break;
                case N: checkEast();
                break;
                case S: checkEast();
                break;
                case W: checkEast();
                break;
            }
        }
    }

    private void checkSamePositions() {
        for (Iterator<Elf> e = elves.iterator(); e.hasNext();) {
            Elf elf = e.next();
            elves.stream().filter(es->es.getPlannedPos().equals(elf.getPlannedPos())).forEach(t -> t.setPlannedPos(null));
        }
    }

    private void move() {
        for (Elf elf: elves) {
            if (elf.getPlannedPos() != null) {
                elf.setDefaultPos(elf.getPlannedPos());
                elf.setPlannedPos(null);
            }
        }
    }
}
