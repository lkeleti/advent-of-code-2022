package dev.lkeleti;

import java.util.Objects;

public class Position {
    private final long posX;
    private final long posY;

    public Position(long posX, long posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public long getPosX() {
        return posX;
    }

    public long getPosY() {
        return posY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return posX == position.posX && posY == position.posY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(posX, posY);
    }

    @Override
    public String toString() {
        return "Position{" +
                "posX=" + posX +
                ", posY=" + posY +
                '}';
    }
}
