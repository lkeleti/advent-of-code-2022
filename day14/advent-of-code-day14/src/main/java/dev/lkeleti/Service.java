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

    private long sands;

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
    }

    public long solve(String part) {
        sands = 0;
        board.clear();
        findMinMaxCords();
        if (part.equals("p2")) {
            lines.add(
                    new Line(
                            new Position(0, maxY + 2),
                            new Position(maxX*2, maxY + 2)
                    )
            );
        }
        drawLines();
        simulate(part);
        drawBoard();
        return sands;
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
        for (int y = 0;  y <= maxY + 2; y++) {
            air.add('.');
        }

        for (int x = 0; x <= maxX * 2; x++) {
            board.add(new ArrayList<>(air));
        }
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
        for (int y = 0; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                System.out.print(board.get(x).get(y));
            }
            System.out.println();
        }
    }

    private void simulate(String part) {
        boolean end = false;
        int falseCounter = 0;
        while (!end) {
            boolean canMove = true;
            Position defPos = new Position(500, 0);
            while (canMove) {
                if ((part.equals("p1") && (defPos.getPosY() + 1 > maxY ||
                        defPos.getPosX() - 1 < 0 ||
                        defPos.getPosX() + 1 > maxX)) ||
                        (part.equals("p2") && board.get(500).get(0) == 'O')
                ) {
                    end = true;
                    sands -= 1;
                    break;
                }

                char downPosValue = board.get(defPos.getPosX()).get(defPos.getPosY() + 1);
                char leftPosValue = board.get(defPos.getPosX() - 1).get(defPos.getPosY() + 1);
                char rightPosValue = board.get(defPos.getPosX() + 1).get(defPos.getPosY() + 1);

                if (downPosValue == '.') {
                    board.get(defPos.getPosX()).set(defPos.getPosY(), '.');
                    defPos.setPosY(defPos.getPosY() + 1);
                    board.get(defPos.getPosX()).set(defPos.getPosY(), 'O');
                    falseCounter = 0;
                } else {
                    if (leftPosValue == '.') {
                        board.get(defPos.getPosX()).set(defPos.getPosY(), '.');
                        defPos.setPosX(defPos.getPosX() - 1);
                        defPos.setPosY(defPos.getPosY() + 1);
                        board.get(defPos.getPosX()).set(defPos.getPosY(), 'O');
                        falseCounter = 0;
                    } else {
                        if (rightPosValue == '.') {
                            board.get(defPos.getPosX()).set(defPos.getPosY(), '.');
                            defPos.setPosX(defPos.getPosX() + 1);
                            defPos.setPosY(defPos.getPosY() + 1);
                            board.get(defPos.getPosX()).set(defPos.getPosY(), 'O');
                            falseCounter = 0;
                        } else {
                            canMove = false;
                            falseCounter += 1;
                        }
                    }
                }
            }
            if (falseCounter == 2) {
                end = true;
            }
            sands += 1;
        }
    }
}
