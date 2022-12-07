package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private final List<String> inputData = new ArrayList<>();

    private final OwnDirectory rootDir = new OwnDirectory("/", null);
    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                inputData.add(line.trim());
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path);
        }
        processFile();
    }

    private void processFile() {
        OwnDirectory defaultDir = rootDir;
        char mode = '0';
        for (int i = 0; i < inputData.size(); i++) {
            if (inputData.get(i).contains("$ cd /")) {
                defaultDir = rootDir;
                mode = 'r';
            } else if (inputData.get(i).contains("$ cd ..")) {
                defaultDir = defaultDir.getParent();
                mode = 'c';
            } else if (inputData.get(i).contains("$ cd ")) {
                String newDirName = inputData.get(i).split(" ")[2];
                for (OwnDirectory subDir: defaultDir.getSubdirectories()){
                    if (subDir.getName().equals(newDirName)) {
                        defaultDir = subDir;
                        break;
                    }
                }
                mode = 'c';
            }

            if (mode == 'l') {
                if (inputData.get(i).contains("dir ")) {
                    String dirName = inputData.get(i).split(" ")[1];
                    defaultDir.addDirectory(
                            new OwnDirectory(dirName, defaultDir)
                    );
                }
                else {
                    String[] datas = inputData.get(i).split(" ");
                    long fileSize = Long.parseLong(datas[0]);
                    String fileName = datas[1];
                    defaultDir.addFile(
                            new OwnFile(fileName, fileSize)
                    );
                }
            }

            if (inputData.get(i).contains("$ ls")) {
                mode = 'l';
            }
        }
    }

    private long countRecursive(OwnDirectory defaultDir) {
        long total = 0;
        if (defaultDir.getSize() <= 100000) {
            total += defaultDir.getSize();
        }
        if (!defaultDir.getSubdirectories().isEmpty()) {
            for (OwnDirectory subDir: defaultDir.getSubdirectories()) {
                total += countRecursive(subDir);
            }
        }
        return total;
    }
    public long countTotal() {
        return countRecursive(rootDir);
    }

    public long findSmallestDeletable() {
        long freeSpace = 70_000_000 - rootDir.getSize();
        long neededSpace = 30_000_000 - freeSpace;
        return findPerfectDir(rootDir,neededSpace, Long.MAX_VALUE);
    }

    private long findPerfectDir(OwnDirectory defaultDir, long neededSpace, long smallest) {
        if (!defaultDir.getSubdirectories().isEmpty()) {
            for (OwnDirectory subDir: defaultDir.getSubdirectories()) {
                long subDirSize = subDir.getSize();
                if (subDirSize >= neededSpace && subDirSize < smallest) {
                    smallest = subDirSize;
                }
                smallest = findPerfectDir(subDir,neededSpace,smallest);
            }
        }
        return smallest;
    }

    public OwnDirectory getRootDir() {
        return rootDir;
    }
}
