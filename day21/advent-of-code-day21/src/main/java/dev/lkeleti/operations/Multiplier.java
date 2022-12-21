package dev.lkeleti.operations;

public class Multiplier implements Operation{

    @Override
    public Long execute(Long a, Long b) {
        return a*b;
    }
}
