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
}
