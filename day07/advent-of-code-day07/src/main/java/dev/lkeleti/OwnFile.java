package dev.lkeleti;

public class OwnFile {
    private String name;
    private long size;

    public OwnFile(String name, long size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }
}
