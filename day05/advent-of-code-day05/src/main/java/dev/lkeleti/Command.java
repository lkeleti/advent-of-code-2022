package dev.lkeleti;

public class Command {

    private int numberOfMoves;
    private int fromColumn;
    private int toColumn;

    public Command(int numberOfMoves, int fromColumn, int toColumn) {
        this.numberOfMoves = numberOfMoves;
        this.fromColumn = fromColumn;
        this.toColumn = toColumn;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public int getFromColumn() {
        return fromColumn;
    }

    public int getToColumn() {
        return toColumn;
    }
}
