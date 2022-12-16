package dev.lkeleti;

import java.util.Objects;

public class BaconPos {
    private final long posXFrom;
    private final long posXTo;

    public BaconPos(long posXFrom, long posXTo) {
        this.posXFrom = posXFrom;
        this.posXTo = posXTo;
   }

    public long getPosXFrom() {
        return posXFrom;
    }

    public long getPosXTo() {
        return posXTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaconPos baconPos = (BaconPos) o;
        return posXFrom == baconPos.posXFrom && posXTo == baconPos.posXTo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(posXFrom, posXTo);
    }

    @Override
    public String toString() {
        return "BaconPos{" +
                "posXFrom=" + posXFrom +
                ", posXTo=" + posXTo +
                '}';
    }
}
