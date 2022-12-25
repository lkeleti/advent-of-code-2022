package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Service {
    private final List<String> snafuStrings = new ArrayList<>();
    private final List<Long> snafuNumbers = new ArrayList<>();
    private final Map<Character,Integer> digitConverter = new TreeMap<>();

    public Service() {
        digitConverter.put('2',2);
        digitConverter.put('1',1);
        digitConverter.put('0',0);
        digitConverter.put('-',-1);
        digitConverter.put('=',-2);
    }

    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                snafuStrings.add(line.trim());
                snafuNumbers.add(snafuToDecimal(line.trim()));
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path);
        }
    }

    private long snafuToDecimal(String snafu) {
        long number = 0;

        for (int i = 0; i < snafu.length(); i++) {
            number += (Math.pow(5,i) * digitConverter.get(snafu.charAt(snafu.length()- 1 - i)));
        }
        return number;
    }

    private String decimalToSnafu(long number) {
        StringBuilder res = new StringBuilder();
        while (number > 0) {
            switch ((int) (number % 5)) {
                case 0:
                    res.insert(0, '0');
                    break;
                case 1:
                    res.insert(0, '1');
                    break;
                case 2:
                    res.insert(0, '2');
                    break;
                case 3:
                    number += 5;
                    res.insert(0, '=');
                    break;
                case 4:
                    number += 5;
                    res.insert(0, '-');
                    break;
            }
            number /= 5;
        }
        return res.toString();
    }

    public String getSums() {
        System.out.println(snafuNumbers.stream().mapToLong(value -> value).sum());
        return decimalToSnafu(snafuNumbers.stream().mapToLong(value -> value).sum());
    }

}
