package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Service {
    private final List<Integer> numbers = new ArrayList<>();
    private List<Integer> encryptedData;

    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                numbers.add(Integer.parseInt(line.trim()));
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path);
        }
    }

    public int decrypt() {
        encryptedData = new ArrayList<>(numbers);
        int sizeOfNumbers = numbers.size();

        for (int number: numbers) {
            int defPosition = encryptedData.indexOf(number);
            encryptedData.remove(defPosition);
            int nextPosition = defPosition + number;
            if (number < 0) {
                nextPosition = -number % (sizeOfNumbers - 1);
                nextPosition = (sizeOfNumbers - 1) - nextPosition + 1;
            } else {
                nextPosition = nextPosition % (sizeOfNumbers - 1);
            }

            encryptedData.add(nextPosition, number);
        }


        return encryptedData.get(1000 % (sizeOfNumbers -1 )) + encryptedData.get(2000 % (sizeOfNumbers -1 )) + encryptedData.get(3000 % (sizeOfNumbers -1 ));
    }
}
