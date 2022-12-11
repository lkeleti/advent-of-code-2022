package dev.lkeleti;

public class Multiplier implements Operation{
    private int value;

    public Multiplier(int value) {
        this.value = value;
    }

    @Override
    public long execute(long old) {
        return old * value;
    }
}
