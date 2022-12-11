package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Service {

    private final List<String> input = new ArrayList<>();
    private final Map<Integer, Monkey> monkeys = new TreeMap<>();
    public void readInput(Path path) {

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                input.add(line.trim());
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path);
        }
        processInput();
    }

    private void processInput() {
        for (int i = 0; i < input.size(); i+= 7) {
            int name = Integer.parseInt(input.get(i).substring(7,8));

            Monkey defMonkey = new Monkey(
                    name,
                    getOperationFromLine(input.get(i+2)),
                    Integer.parseInt(input.get(i+3).substring(19)),
                    Integer.parseInt(input.get(i+4).substring(25)),
                    Integer.parseInt(input.get(i+5).substring(26))
            );
            getItemsFromLine(input.get(i+1), defMonkey);
            monkeys.put(name,defMonkey);
        }
    }

    private Operation getOperationFromLine(String line) {
        String name = line.substring(21,22);
        String value = line.substring(23);
        if (value.equals("old")) {
            name = "^";
            value = "2";
        }

        if (name.equals("+")) {
            return new Adder(Integer.parseInt(value));
        } else if (name.equals("*")) {
            return new Multiplier(Integer.parseInt(value));
        }
        else {
            return new Power();
        }
    }

    private void getItemsFromLine(String line, Monkey defMonkey) {
        String[] datas = line.substring(16).split(",");
        for (String data: datas) {
            defMonkey.addItem(Integer.parseInt(data.trim()));
        }
    }


    private void monkeyInspects(int maxRound, String part) {
        int prod = 1;
        for (Monkey monkey: monkeys.values()) {
            prod *= monkey.getTest();
        }
        for (int round = 0; round < maxRound; round++) {
            for (Monkey monkey: monkeys.values()) {
                int itemSize = monkey.getItemSize();
                for (int item = 0; item < itemSize; item++) {
                    int newMonkey;
                    long worryLevel = monkey.getOperation().execute(monkey.getFirstItem());
                    monkey.incInspectCounter();
                    if (part.equals("p1")) {
                        worryLevel /= 3;
                    }
                    else {
                        worryLevel = worryLevel % prod;
                    }

                    if (worryLevel % monkey.getTest() == 0) {
                        newMonkey = monkey.getTrueMonkey();
                    }
                    else {
                        newMonkey = monkey.getFalseMonkey();
                    }
                    monkeys.get(newMonkey).addItem(worryLevel);
                }
            }
        }
    }

    public long findMax(int round, String part) {
        init();
        monkeyInspects(round,part);
        List<Long> items = new ArrayList<>();
        for (Monkey monkey: monkeys.values()) {
            items.add(monkey.getInspectCounter());
        }

        items.sort(Collections.reverseOrder());
        return items.get(0) * items.get(1);
    }

    private void init() {
        monkeys.clear();
        input.clear();
        readInput(Path.of("src/main/resources/input.txt"));
    }
}
