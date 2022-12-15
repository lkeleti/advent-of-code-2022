package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Service {

    private Map<Position, Character> matrix = new LinkedHashMap<>();
    private List<Measure> measureList = new ArrayList<>();

    private final long ROW = 2_000_000;
    private final long CORD = 4_000_000;
    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                readCordsFromLine(line.trim());
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path);
        }
    }

    private void readCordsFromLine(String line) {
        String[] datas = line.split("=");
        long sX = Long.parseLong(datas[1].substring(0, datas[1].indexOf(",")));
        long sY = Long.parseLong(datas[2].substring(0, datas[2].indexOf(":")));
        long bX = Long.parseLong(datas[3].substring(0, datas[3].indexOf(",")));
        long bY = Long.parseLong(datas[4]);
        measureList.add(
                new Measure(
                        new Position(sX, sY),
                        new Position(bX, bY)
                )
        );
    }

    public long simulate1() {
        for (Measure measure: measureList) {
            long senX = measure.getSensorPos().getPosX();
            long senY = measure.getSensorPos().getPosY();
            if ( senY == ROW) {
                matrix.put(measure.getSensorPos(),'S');
            }
            if (measure.getBeaconPos().getPosY() == ROW) {
                matrix.put(measure.getBeaconPos(),'B');
            }
            long distance = Math.abs(senX - measure.getBeaconPos().getPosX()) + Math.abs(senY - measure.getBeaconPos().getPosY());
            if (senY + distance >= ROW && senY - distance <= ROW) {
                long width = distance - Math.abs(ROW - senY);
                for (long i = senX - width; i <= senX + width; i ++) {
                    matrix.computeIfAbsent(
                            new Position(i, ROW), position -> '#'
                    );
                }
            }
        }
        long counter = 0;
        for (char c: matrix.values()) {
            if (c == '#') {
                counter++;
            }
        }
        return counter;
    }

    public long simulate2() {
        matrix.clear();
        for (Measure measure: measureList) {
            long senX = measure.getSensorPos().getPosX();
            long senY = measure.getSensorPos().getPosY();
            long bacX = measure.getBeaconPos().getPosX();
            long bacY = measure.getBeaconPos().getPosY();
            if ( senY >= 0 && senY <= CORD && senX >= 0 && senX <= CORD) {
                matrix.put(measure.getSensorPos(),'S');
            }
            if ( bacY >= 0 && bacY <= CORD && bacX >= 0 && bacX <= CORD) {
                matrix.put(measure.getBeaconPos(),'B');
            }
            long distance = Math.abs(senX - measure.getBeaconPos().getPosX()) + Math.abs(senY - measure.getBeaconPos().getPosY());
            for (long j = senY - distance; j <= senY + distance; j++) {
                long width = distance - Math.abs(j - senY);
                for (long i = senX - width; i <= senX + width; i++) {
                    matrix.computeIfAbsent(
                            new Position(i, j), position -> '#'
                    );
                }
            }
        }
        long freq = 0;
        for (long i = 0; i <= CORD; i++) {
            for (long j = 0; j <= CORD; j++) {
                if (!matrix.containsKey(new Position(i, j))) {
                    return (i * 4000000) + j;
                }
            }
        }
        throw new IllegalArgumentException("Can't find freq");
    }
}
