package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Service {

    private final Map<Position, Character> matrix = new LinkedHashMap<>();
    private final Map<Long, List<BaconPos>> matrix2 = new LinkedHashMap<>();
    private final List<Measure> measureList = new ArrayList<>();

    private static final long ROW = 2_000_000;
    private static final long CORD = 4_000_000;
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
                BaconPos baconPos = new BaconPos(measure.getSensorPos().getPosX(), measure.getSensorPos().getPosX());
                if (!matrix2.containsKey(senY)) {
                    matrix2.put(senY, new ArrayList<>());
                }
                matrix2.get(senY).add(baconPos);
            }

            if ( bacY >= 0 && bacY <= CORD && bacX >= 0 && bacX <= CORD) {
                BaconPos baconPos = new BaconPos(measure.getBeaconPos().getPosX(), measure.getBeaconPos().getPosX());
                if (!matrix2.containsKey(bacY)) {
                    matrix2.put(bacY, new ArrayList<>());
                }
                matrix2.get(bacY).add(baconPos);
            }

            long distance = Math.abs(senX - measure.getBeaconPos().getPosX()) + Math.abs(senY - measure.getBeaconPos().getPosY());
            for (long j = senY - distance; j <= senY + distance; j++) {
                long width = distance - Math.abs(j - senY);
                BaconPos baconPos = new BaconPos(senX - width, senX + width);
                if (!matrix2.containsKey(j)) {
                    matrix2.put(j, new ArrayList<>());
                }
                matrix2.get(j).add(baconPos);

            }
        }

        for (long i = 0; i <= CORD; i++) {
            List<BaconPos> defBaconPos = matrix2.get(i);
            if (defBaconPos.isEmpty()) {
                if (defBaconPos.get(0).getPosXTo() > 0 || defBaconPos.get(0).getPosXTo() < CORD) {
                    System.out.println(defBaconPos.get(0));
                    return 0;
                }
            }
            else {
                defBaconPos.sort(Comparator.comparingLong(BaconPos::getPosXFrom));
                long to = 0;
                for (int j = 0; j < defBaconPos.size(); j++){
                    if (to < defBaconPos.get(j).getPosXFrom()) {
                        return (to + 1) * 4000000 + i ;
                    }
                    to = Math.max(to, defBaconPos.get(j).getPosXTo());
                }
            }
        }
        throw new IllegalArgumentException("Can't find freq");
    }
}
