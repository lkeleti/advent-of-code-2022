package dev.lkeleti;

public class Point {
    private final long posX;
    private final long posY;
    private final long posZ;

    public Point(long posX, long posY, long posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public long getPosX() {
        return posX;
    }

    public long getPosY() {
        return posY;
    }

    public long getPosZ() {
        return posZ;
    }
}
