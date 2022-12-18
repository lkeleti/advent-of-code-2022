package dev.lkeleti;

import java.util.Objects;

public class Point implements Comparable<Point>{
    private final long posX;
    private final long posY;
    private final long posZ;

    public Point(long posX, long posY, long posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public long getPosX() {
        return posX;
    }

    public long getPosY() {
        return posY;
    }

    public long getPosZ() {
        return posZ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return posX == point.posX && posY == point.posY && posZ == point.posZ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(posX, posY, posZ);
    }

    @Override
    public String toString() {
        return "Point{" +
                "posX=" + posX +
                ", posY=" + posY +
                ", posZ=" + posZ +
                '}';
    }

    @Override
    public int compareTo(Point o) {
        if (posX == o.getPosX() && posY == o.getPosY() && posZ == o.getPosZ()) {
            return 0;
        }
        else {
            return (int)(posX * 100 + posY * 10 + posZ - o.getPosX() * 100 - o.getPosY() * 10 - posZ);
        }
    }
}
