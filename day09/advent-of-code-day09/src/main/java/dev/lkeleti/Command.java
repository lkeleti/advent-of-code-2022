package dev.lkeleti;

public class Command {
    private final char direction;
    private final int distance;

    public Command(char direction, int distance) {
        this.direction = direction;
        this.distance = distance;
    }

    public char getDirection() {
        return direction;
    }

    public int getDistance() {
        return distance;
    }
}
