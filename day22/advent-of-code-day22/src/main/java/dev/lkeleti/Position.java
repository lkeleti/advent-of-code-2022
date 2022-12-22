package dev.lkeleti;

public class Position {
    private int posX;
    private int posY;
    private char direction;

    public Position(int posX, int posY, char direction) {
        this.posX = posX;
        this.posY = posY;
        this.direction = direction;
    }

    public Position(Position position) {
        posX = position.getPosX();
        posY = position.getPosY();
        direction = position.getDirection();
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public void incX() {
        posX += 1;
    }

    public void incY() {
        posY += 1;
    }

    public void decX() {
        posX -= 1;
    }

    public void decY() {
        posY -= 1;
    }

    @Override
    public String toString() {
        return "Position{" +
                "posX=" + posX +
                ", posY=" + posY +
                ", direction=" + direction +
                '}';
    }
}
