package dev.lkeleti;

import java.util.Objects;

public class Position {
    private final int posX;
    private final int posY;

    public Position(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
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
