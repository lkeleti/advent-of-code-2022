package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Service {

    private final List<Point> lavaPoints = new ArrayList<>();
    private Bound bounds;
    private final Set<Point> waterPoints = new TreeSet<>();
    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] datas = line.trim().split(",");
                lavaPoints.add(
                        new Point(
                                Integer.parseInt(datas[0]),
                                Integer.parseInt(datas[1]),
                                Integer.parseInt(datas[2])
                        )
                );
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path);
        }
    }

    private List<Point> getNeighbours(Point point) {
        return new ArrayList<>(List.of(
                new Point(point.getPosX()+1, point.getPosY(), point.getPosZ()),
                new Point(point.getPosX()-1, point.getPosY(), point.getPosZ()),
                new Point(point.getPosX(), point.getPosY()+1, point.getPosZ()),
                new Point(point.getPosX(), point.getPosY()-1, point.getPosZ()),
                new Point(point.getPosX(), point.getPosY(), point.getPosZ()+1),
                new Point(point.getPosX(), point.getPosY(), point.getPosZ()-1)
        ));
    }

    public long countFreeSides(int part) {
        long sum = 0;
        for (Point point: lavaPoints) {
            List<Point> neighbours = getNeighbours(point);
            int side = 6;
            for (Point neighbour: neighbours) {
                if (part == 1 && lavaPoints.contains(neighbour)) {
                    side -=1;
                }
                else {
                    if (part == 2 && !waterPoints.contains(neighbour)) {
                        side -= 1;
                    }
                }
            }
            sum += side;
        }
        return sum;
    }
    
    public long countWaterSides(int part) {
        determineBounds();
        fillWithWater();
        return countFreeSides(part);
    }

    private void fillNeighbourWithWater(Point start, List<Point> visited) {
        if (visited.contains(start) || lavaPoints.contains(start) || bounds.isOutOfBound(start)) {
            return;
        }
        waterPoints.add(start);
        visited.add(start);
        List<Point> neighbours = getNeighbours(start);
        for(Iterator<Point> p = neighbours.iterator(); p.hasNext();){
            Point point = p.next();
            if (visited.contains(point) || bounds.isOutOfBound(point)) {
                p.remove();
            }
        }

        for (Point neighbour: neighbours) {
            fillNeighbourWithWater(neighbour, new ArrayList<>(visited));
        }
    }

    private void fillWithWater() {
        fillNeighbourWithWater(bounds.getMinPoint(), new ArrayList<>());
    }

    private void determineBounds() {
        bounds = new Bound(
                new Point(
                        lavaPoints.stream().mapToLong(Point::getPosX).min().getAsLong(),
                        lavaPoints.stream().mapToLong(Point::getPosY).min().getAsLong(),
                        lavaPoints.stream().mapToLong(Point::getPosZ).min().getAsLong()
                ),
                new Point(
                        lavaPoints.stream().mapToLong(Point::getPosX).max().getAsLong(),
                        lavaPoints.stream().mapToLong(Point::getPosY).max().getAsLong(),
                        lavaPoints.stream().mapToLong(Point::getPosZ).max().getAsLong()
                )
        );
    }
}
