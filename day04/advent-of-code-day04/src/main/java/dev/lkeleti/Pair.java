package dev.lkeleti;

public class Pair {
    private final int firstPartFrom;
    private final int firstPartTo;
    private final int secondPartFrom;
    private final int secondPartTo;

    public Pair(int firstPartFrom, int firstPartTo, int secondPartFrom, int secondPartTo) {
        this.firstPartFrom = firstPartFrom;
        this.firstPartTo = firstPartTo;
        this.secondPartFrom = secondPartFrom;
        this.secondPartTo = secondPartTo;
    }

    public int getFirstPartFrom() {
        return firstPartFrom;
    }

    public int getFirstPartTo() {
        return firstPartTo;
    }

    public int getSecondPartFrom() {
        return secondPartFrom;
    }

    public int getSecondPartTo() {
        return secondPartTo;
    }
}
