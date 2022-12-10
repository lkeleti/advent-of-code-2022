package dev.lkeleti;

public class Command {
    private final String name;
    private final int value;

    private final int cycle;

    public Command(String name, int value) {
        this.name = name;
        this.value = value;

        if (name.equals("noop")) {
            cycle = 1;
        }
        else {
            cycle = 2;
        }
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public int getCycle() {
        return cycle;
    }
}
