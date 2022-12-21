package dev.lkeleti;

import dev.lkeleti.operations.Operation;

public class Monkey {
    private String name;
    private Long value = null;
    private Monkey monkeyLeft;
    private Monkey monkeyRight;

    private Operation operation;

    public Monkey(String name, Long value) {
        this.name = name;
        this.value = value;
    }

    public Monkey(String name, Monkey monkeyLeft, Monkey monkeyRight, Operation operation) {
        this.name = name;
        this.monkeyLeft = monkeyLeft;
        this.monkeyRight = monkeyRight;
        this.operation = operation;
    }

    public String getName() {
        return name;
    }

    public Long getValue() {
        return value;
    }

    public Monkey getMonkeyLeft() {
        return monkeyLeft;
    }

    public Monkey getMonkeyRight() {
        return monkeyRight;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
