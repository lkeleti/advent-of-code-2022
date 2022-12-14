package dev.lkeleti;

public class Line {
    private final Position startPos;
    private final Position endPos;

    public Line(Position startPos, Position endPos) {
        this.startPos = startPos;
        this.endPos = endPos;
    }

    public Position getStartPos() {
        return startPos;
    }

    public Position getEndPos() {
        return endPos;
    }

    @Override
    public String toString() {
        return "Line{" +
                "startPos=" + startPos +
                ", endPos=" + endPos +
                '}';
    }
}
