package dev.lkeleti.operations;

public class Substractor implements Operation{
    @Override
    public Long execute(Long a, Long b) {
        return a-b;
    }
}
