package dev.lkeleti;

public class Bound {
    private final Point minPoint;
    private final Point maxPoint;

    public Bound(Point minPoint, Point maxPoint) {
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;
    }

    public Point getMinPoint() {
        return minPoint;
    }

    public Point getMaxPoint() {
        return maxPoint;
    }

    public boolean isOutOfBound(Point point) {
        return !(getMinPoint().getPosX() <= point.getPosX() &&
                getMinPoint().getPosY() <= point.getPosY() &&
                getMinPoint().getPosZ() <= point.getPosZ() &&

                point.getPosX() <= getMaxPoint().getPosX() &&
                point.getPosY() <= getMaxPoint().getPosY() &&
                point.getPosZ() <= getMaxPoint().getPosZ()
        );
    }
}
