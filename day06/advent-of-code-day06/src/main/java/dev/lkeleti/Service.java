package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Service {
    private String receivedData = "";

    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                receivedData = line;
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path);
        }
    }

    public int checkSignal() {
        for (int i = 0; i < receivedData.length()-4; i++) {
            if (isDifferent(receivedData.substring(i,i+4))) {
                return i + 4;
            }
        }
        return -1;
    }

    public int checkMessage() {
        for (int i = 0; i < receivedData.length()-14; i++) {
            if (isDifferent(receivedData.substring(i,i+14))) {
                return i + 14;
            }
        }
        return -1;
    }

    private boolean isDifferent(String signal) {
        for (int i = 0; i < signal.length()-1; i++ ) {
            for (int j = i + 1; j < signal.length(); j++) {
                if (signal.toCharArray()[i] == signal.toCharArray()[j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
