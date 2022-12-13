package dev.lkeleti;

public class PositionDistance {

    private Position position;
    private int distance;

    public PositionDistance(Position position, int distance) {
        this.position = position;
        this.distance = distance;
    }

    public Position getPosition() {
        return position;
    }

    public int getDistance() {
        return distance;
    }
}
