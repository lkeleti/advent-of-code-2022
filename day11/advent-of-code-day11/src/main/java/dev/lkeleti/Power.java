package dev.lkeleti;

public class Power implements Operation{

    @Override
    public long execute(long old) {
        return old * old;
    }
}
