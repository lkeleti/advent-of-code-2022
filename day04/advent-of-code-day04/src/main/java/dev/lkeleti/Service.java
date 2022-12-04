package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {

    private final List<Pair> pairs = new ArrayList<>();

    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                readFromLines(line);
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path);
        }
    }

    private void readFromLines(String line) {
        String[] datas = line.trim().split(",");
        FromTo firstPart =createFromTo(datas[0]);
        FromTo secondPart =createFromTo(datas[1]);
        pairs.add(
                new Pair(
                        firstPart.getFrom(),
                        firstPart.getTo(),
                        secondPart.getFrom(),
                        secondPart.getTo()
                )
        );
    }

    private FromTo createFromTo(String data) {
        String[] fromTo = data.split("-");

        int from = Integer.parseInt(fromTo[0]);
        int to = Integer.parseInt(fromTo[1]);

        return new FromTo(from,to);
    }

    public long countFullOverlaps() {
        long total = 0;
        for (Pair pair: pairs) {
            if ((pair.getFirstPartFrom() >= pair.getSecondPartFrom() && pair.getFirstPartTo() <= pair.getSecondPartTo()) ||
                    (pair.getSecondPartFrom() >= pair.getFirstPartFrom() && pair.getSecondPartTo() <= pair.getFirstPartTo())
            ) {
                total++;
            }
        }
        return total;
    }

    public long countPartlyOverlaps() {
        long total = 0;
        for (Pair pair: pairs) {
            if ((pair.getFirstPartTo() >= pair.getSecondPartFrom() && pair.getFirstPartTo() <= pair.getSecondPartTo()) ||
                    (pair.getSecondPartTo() >= pair.getFirstPartFrom() && pair.getSecondPartTo() <= pair.getFirstPartTo())
            ) {
                total++;
            }
        }
        return total;
    }
}
