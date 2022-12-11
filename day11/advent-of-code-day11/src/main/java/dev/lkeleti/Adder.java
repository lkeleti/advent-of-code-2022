package dev.lkeleti;

public class Adder implements Operation{
    private int value;

    public Adder(int value) {
        this.value = value;
    }

    @Override
    public long execute(long old) {
        return old + value;
    }
}
