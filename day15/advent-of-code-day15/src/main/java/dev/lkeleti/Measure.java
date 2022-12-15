package dev.lkeleti;

public class Measure {
    private final Position sensorPos;
    private final Position beaconPos;

    public Measure(Position sensorPos, Position beaconPos) {
        this.sensorPos = sensorPos;
        this.beaconPos = beaconPos;
    }

    public Position getSensorPos() {
        return sensorPos;
    }

    public Position getBeaconPos() {
        return beaconPos;
    }
}
