package dev.lkeleti;

import java.util.ArrayList;
import java.util.List;

public class OwnDirectory {
    private String name;
    private long size;
    private OwnDirectory parent;
    private List<OwnDirectory> subdirectories = new ArrayList<>();
    private List<OwnFile> ownFiles = new ArrayList<>();

    public OwnDirectory(String name, OwnDirectory parent) {
        this.name = name;
        this.parent = parent;
        this.size = 0;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public OwnDirectory getParent() {
        return parent;
    }

    public List<OwnDirectory> getSubdirectories() {
        return subdirectories;
    }

    public List<OwnFile> getOwnFiles() {
        return ownFiles;
    }

    public void addFile(OwnFile file) {
        ownFiles.add(file);
        size += file.getSize();
    }

    public void addDirectory(OwnDirectory directory) {
        subdirectories.add(directory);
    }
}
