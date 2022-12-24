package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Service {
private List<Elf> elves = new ArrayList<>();
private Map<Direction,Direction> directionMap = new HashMap<>();
private Queue<Direction> direction = new LinkedList<>();

    public Service() {
        directionMap.put(Direction.N, Direction.S);
        directionMap.put(Direction.S, Direction.W);
        directionMap.put(Direction.W, Direction.E);
        directionMap.put(Direction.E, Direction.N);

        direction.add(Direction.N);
        direction.add(Direction.S);
        direction.add(Direction.W);
        direction.add(Direction.E);
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

    public long simulate(int part) {
        int counter = 0;
        boolean noMoves = false;
        while((part == 1 && counter < 10) || (part == 2 && noMoves == false)) {
            plan();
            checkSamePositions();
            if (elves.stream().filter(e->e.getPlannedPos() != null).count() == 0) {
                return counter += 11;
            }
            move();

            Direction rotateDir = direction.poll();
            direction.add(rotateDir);
            counter++;
        }
        int minX = elves.stream().mapToInt(e->e.getDefaultPos().getPosX()).min().getAsInt();
        int maxX = elves.stream().mapToInt(e->e.getDefaultPos().getPosX()).max().getAsInt();
        int minY = elves.stream().mapToInt(e->e.getDefaultPos().getPosY()).min().getAsInt();
        int maxY = elves.stream().mapToInt(e->e.getDefaultPos().getPosY()).max().getAsInt();
        return ((maxX-minX+1) * (maxY-minY+1)) - elves.size() ;
    }

    private void drawElves(int minX, int maxX, int minY, int maxY) {
        for (int i = minY; i <= maxY; i++) {
            List<Character> row = new ArrayList<>();
            for (int j = minX; j <= maxX; j++) {
                char c;
                Position checkPosition = new Position(j,i);
                if (elves.stream().anyMatch(e -> e.getDefaultPos().equals(checkPosition))){
                    c = '#';
                }
                else {
                    c = '.';
                }
                row.add(c);
            }
            row.stream().forEach(System.out::print);
            System.out.println();
        }
        System.out.println("--------------------------------------------------");
    }

    private void plan() {
        for (Elf elf : elves) {
            if (willMove(elf)) {
                 switch (direction.peek()) {
                     case E:
                        checkEast(elf, 0);
                        break;
                    case N:
                        checkNorth(elf, 0);
                        break;
                    case S:
                        checkSouth(elf, 0);
                        break;
                    case W:
                        checkWest(elf, 0);
                        break;
                }
            }
        }
    }

    private boolean willMove(Elf elf) {
        int posX = elf.getDefaultPos().getPosX();
        int posY = elf.getDefaultPos().getPosY();
        int northPosX = posX + Direction.N.getMask().getPosX();
        int northPosY = posY + Direction.N.getMask().getPosY();
        int northEastPosX = posX + Direction.NE.getMask().getPosX();
        int northEastPosY = posY + Direction.NE.getMask().getPosY();
        int northWestPosX = posX + Direction.NW.getMask().getPosX();
        int northWestPosY = posY + Direction.NW.getMask().getPosY();
        int southPosX = posX + Direction.S.getMask().getPosX();
        int southPosY = posY + Direction.S.getMask().getPosY();
        int southEastPosX = posX + Direction.SE.getMask().getPosX();
        int southEastPosY = posY + Direction.SE.getMask().getPosY();
        int southWestPosX = posX + Direction.SW.getMask().getPosX();
        int southWestPosY = posY + Direction.SW.getMask().getPosY();
        int westPosX = posX + Direction.W.getMask().getPosX();
        int westPosY = posY + Direction.W.getMask().getPosY();
        int eastPosX = posX + Direction.E.getMask().getPosX();
        int eastPosY = posY + Direction.E.getMask().getPosY();
        if (elves.stream().noneMatch(e -> e.getDefaultPos().equals(new Position(northPosX, northPosY)) ||
                e.getDefaultPos().equals(new Position(northEastPosX, northEastPosY)) ||
                e.getDefaultPos().equals(new Position(northWestPosX, northWestPosY)) ||
                e.getDefaultPos().equals(new Position(southPosX, southPosY)) ||
                e.getDefaultPos().equals(new Position(southEastPosX, southEastPosY)) ||
                e.getDefaultPos().equals(new Position(southWestPosX, southWestPosY)) ||
                e.getDefaultPos().equals(new Position(westPosX, westPosY)) ||
                e.getDefaultPos().equals(new Position(eastPosX, eastPosY))
        )) {
            return false;
        }
        return true;
    }

    private void checkNorth(Elf elf, int counter) {
        int posX = elf.getDefaultPos().getPosX();
        int posY = elf.getDefaultPos().getPosY();
        int northPosX = posX + Direction.N.getMask().getPosX();
        int northPosY = posY + Direction.N.getMask().getPosY();
        int northEastPosX = posX + Direction.NE.getMask().getPosX();
        int northEastPosY = posY + Direction.NE.getMask().getPosY();
        int northWestPosX = posX + Direction.NW.getMask().getPosX();
        int northWestPosY = posY + Direction.NW.getMask().getPosY();

        if (elves.stream().noneMatch(e -> e.getDefaultPos().equals(new Position(northPosX, northPosY)) ||
                e.getDefaultPos().equals(new Position(northEastPosX, northEastPosY)) ||
                e.getDefaultPos().equals(new Position(northWestPosX, northWestPosY)))) {
            elf.setPlannedPos(new Position(northPosX, northPosY));
            elf.setDirection(Direction.S);
        }
        else {
            if (counter < 4) {
                checkSouth(elf, counter+1);
            }
            else {
                elf.setPlannedPos(null);
            }
        }
    }

    private void checkSouth(Elf elf, int counter) {
        int posX = elf.getDefaultPos().getPosX();
        int posY = elf.getDefaultPos().getPosY();
        int southPosX = posX + Direction.S.getMask().getPosX();
        int southPosY = posY + Direction.S.getMask().getPosY();
        int southEastPosX = posX + Direction.SE.getMask().getPosX();
        int southEastPosY = posY + Direction.SE.getMask().getPosY();
        int southWestPosX = posX + Direction.SW.getMask().getPosX();
        int southWestPosY = posY + Direction.SW.getMask().getPosY();

        if (elves.stream().noneMatch(e -> e.getDefaultPos().equals(new Position(southPosX, southPosY)) ||
                e.getDefaultPos().equals(new Position(southEastPosX, southEastPosY)) ||
                e.getDefaultPos().equals(new Position(southWestPosX, southWestPosY)))) {
            elf.setPlannedPos(new Position(southPosX, southPosY));
            elf.setDirection(Direction.W);
        }
        else {
            if (counter < 4) {
                checkWest(elf, counter+1);
            }
            else {
                elf.setPlannedPos(null);
            }
        }
    }

    private void checkWest(Elf elf, int counter) {
        int posX = elf.getDefaultPos().getPosX();
        int posY = elf.getDefaultPos().getPosY();
        int westPosX = posX + Direction.W.getMask().getPosX();
        int westPosY = posY + Direction.W.getMask().getPosY();
        int northWestPosX = posX + Direction.NW.getMask().getPosX();
        int northWestPosY = posY + Direction.NW.getMask().getPosY();
        int southWestPosX = posX + Direction.SW.getMask().getPosX();
        int southWestPosY = posY + Direction.SW.getMask().getPosY();

        if (elves.stream().noneMatch(e -> e.getDefaultPos().equals(new Position(westPosX, westPosY)) ||
                e.getDefaultPos().equals(new Position(northWestPosX, northWestPosY)) ||
                e.getDefaultPos().equals(new Position(southWestPosX, southWestPosY)))) {
            elf.setPlannedPos(new Position(westPosX, westPosY));
            elf.setDirection(Direction.E);
        }
        else {
            if (counter < 4) {
                checkEast(elf, counter+1);
            }
            else {
                elf.setPlannedPos(null);
            }
        }
    }

    private void checkEast(Elf elf, int counter) {
        int posX = elf.getDefaultPos().getPosX();
        int posY = elf.getDefaultPos().getPosY();
        int eastPosX = posX + Direction.E.getMask().getPosX();
        int eastPosY = posY + Direction.E.getMask().getPosY();
        int northEastPosX = posX + Direction.NE.getMask().getPosX();
        int northEastPosY = posY + Direction.NE.getMask().getPosY();
        int southEastPosX = posX + Direction.SE.getMask().getPosX();
        int southEastPosY = posY + Direction.SE.getMask().getPosY();

        if (elves.stream().noneMatch(e -> e.getDefaultPos().equals(new Position(eastPosX, eastPosY)) ||
                e.getDefaultPos().equals(new Position(northEastPosX, northEastPosY)) ||
                e.getDefaultPos().equals(new Position(southEastPosX, southEastPosY)))) {
            elf.setPlannedPos(new Position(eastPosX, eastPosY));
            elf.setDirection(Direction.N);
        }
        else {
            if (counter < 4) {
                checkNorth(elf, counter+1);
            }
            else {
                elf.setPlannedPos(null);
            }
        }
    }

    private void checkSamePositions() {
        for (Iterator<Elf> e = elves.iterator(); e.hasNext();) {
            Elf elf = e.next();
            if (elf.getPlannedPos() != null) {
                List<Elf> samePosElf = elves.stream().filter(es -> es.getPlannedPos() != null && es.getPlannedPos().equals(elf.getPlannedPos())).toList();
                if (samePosElf.size() > 1) {
                    for (Elf spe: samePosElf) {
                        spe.setPlannedPos(null);
                    }
                }
            }
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
