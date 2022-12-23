package dev.lkeleti;

public class Elf {
    private Position defaultPos;
    private Position plannedPos;

    public Elf(Position defaultPos) {
        this.defaultPos = defaultPos;
    }

    public Position getDefaultPos() {
        return defaultPos;
    }

    public void setDefaultPos(Position defaultPos) {
        this.defaultPos = defaultPos;
    }

    public Position getPlannedPos() {
        return plannedPos;
    }

    public void setPlannedPos(Position plannedPos) {
        this.plannedPos = plannedPos;
    }
}
