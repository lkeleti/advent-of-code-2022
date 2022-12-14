package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {

    private List<Line> lines = new ArrayList<>();
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;

    private final List<List<Character>> board = new ArrayList<>();
    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                readLinesFromLine(line.trim());
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path);
        }

        findMinMaxCords();
    }

    private void findMinMaxCords() {
        minX = lines.stream().mapToInt(l->l.getStartPos().getPosX()).min().getAsInt();
        maxX = lines.stream().mapToInt(l->l.getStartPos().getPosX()).max().getAsInt();
        minY = lines.stream().mapToInt(l->l.getStartPos().getPosY()).min().getAsInt();
        maxY = lines.stream().mapToInt(l->l.getStartPos().getPosY()).max().getAsInt();
        int tmp = lines.stream().mapToInt(l->l.getEndPos().getPosX()).min().getAsInt();
        minX = Math.min(minX, tmp);
        tmp = lines.stream().mapToInt(l->l.getEndPos().getPosY()).min().getAsInt();
        minY = Math.min(minY, tmp);
        tmp = lines.stream().mapToInt(l->l.getEndPos().getPosX()).max().getAsInt();
        maxX = Math.max(maxX, tmp);
        tmp = lines.stream().mapToInt(l->l.getEndPos().getPosY()).max().getAsInt();
        maxY = Math.max(maxY, tmp);

        List<Character> air = new ArrayList<>();
        for (int y = 0;  y <= maxY; y++) {
            air.add('.');
        }

        for (int x = 0; x <= maxX; x++) {
            board.add(new ArrayList<>(air));
        }
        drawLines();
        drawBoard();
    }

    private void readLinesFromLine(String line) {
        String[] datas = line.split(" -> ");
        Position start;
        Position end = null;
        for (String data: datas) {
            String[] coords = data.split(",");
            start = end;
            end = new Position(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));

            if (start != null) {
                lines.add(new Line(start,end));
            }
        }
    }

    private void drawLines() {
        for (Line line : lines) {
            if (line.getStartPos().getPosX() == line.getEndPos().getPosX())  {
                int startY = Math.min(line.getStartPos().getPosY(), line.getEndPos().getPosY());
                int endY = Math.max(line.getStartPos().getPosY(), line.getEndPos().getPosY());
                for (int y = startY; y <= endY; y++) {
                    board.get(line.getEndPos().getPosX()).set(y,'#');
                }
            }

            if (line.getStartPos().getPosY() == line.getEndPos().getPosY())  {
                int startX = Math.min(line.getStartPos().getPosX(), line.getEndPos().getPosX());
                int endX = Math.max(line.getStartPos().getPosX(), line.getEndPos().getPosX());
                for (int x = startX; x <= endX; x++) {
                    board.get(x).set(line.getEndPos().getPosY(),'#');
                }
            }
        }
    }

    private void drawBoard() {
        board.get(500).set(0, 'O');
        for (int y = 0; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                System.out.print(board.get(x).get(y));
            }
            System.out.println();
        }
    }

}
