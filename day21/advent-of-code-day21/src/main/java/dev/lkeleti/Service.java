package dev.lkeleti;

import dev.lkeleti.operations.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Service {

    private Map<String,Monkey> monkeys = new TreeMap<>();
    private List<String> noValuesMonkey = new ArrayList<>();
    public void readInput(Path path) {
        monkeys.clear();
        noValuesMonkey.clear();
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                readMonkeyFromLine(line.trim());
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path);
        }
    }

    private void readMonkeyFromLine(String line) {
        String[] datas = line.split(": ");
        String name = datas[0];

        if (!datas[1].contains(" ")) {
            Monkey monkey;
            if (!monkeys.containsKey(name)) {
                monkey = new Monkey(name, Long.parseLong(datas[1]));
                monkeys.put(name, monkey);
            }
            else {
                monkeys.get(name).setValue(Long.parseLong(datas[1]));
            }
        }
        else {
            String monkeyLeftName = datas[1].substring(0,4);
            String monkeyRightName = datas[1].substring(7);
            String operationName= datas[1].substring(5,6);
            Monkey monkeyLeft;
            Monkey monkeyRight;
            Operation operation = getOperationFromName(operationName);

            if (!monkeys.containsKey(monkeyLeftName)) {
                monkeys.put(monkeyLeftName, new Monkey(monkeyLeftName, null));
            }
            monkeyLeft = monkeys.get(monkeyLeftName);

            if (!monkeys.containsKey(monkeyRightName)) {
                monkeys.put(monkeyRightName, new Monkey(monkeyRightName, null));
            }
            monkeyRight = monkeys.get(monkeyRightName);

            monkeys.put(name, new Monkey(name, monkeyLeft, monkeyRight, operation));
        }
    }

    private Operation getOperationFromName(String operationName) {
        Operation operation;
        switch (operationName.charAt(0)) {
            case '+':
                operation = new Adder();
                break;
            case '-':
                operation =  new Substractor();
                break;
            case '*':
                operation =  new Multiplier();
                break;
            case '/':
                operation =  new Divider();
                break;
            default:
                operation =  null;
                break;
        }
        return operation;
    }

    public long decode() {
        findNoValuesMonkey(1);
        while(monkeys.get("root").getValue() == null) {
            for (Iterator<String> i = noValuesMonkey.iterator();i.hasNext();) {
                String name = i.next();
                Monkey monkey = monkeys.get(name);
                Long leftValue = monkeys.get(monkey.getMonkeyLeft().getName()).getValue();
                Long rightValue = monkeys.get(monkey.getMonkeyRight().getName()).getValue();
                if (leftValue != null &&
                        rightValue != null
                ) {
                    monkey.setValue(monkey.getOperation().execute(leftValue, rightValue));
                    i.remove();
                }
            }
        }
        return monkeys.get("root").getValue();
    }

    private void findNoValuesMonkey(int part) {
        for (Monkey monkey: monkeys.values()) {
            if (monkey.getValue() == null) {
                noValuesMonkey.add(monkey.getName());
            }
        }
        if (part == 2) {
            noValuesMonkey.remove("root");
        }
    }

    public long decodeTwo() {
        findNoValuesMonkey(2);
        monkeys.get("humn").setValue(null);
        boolean change = true;
        while(change) {
            change = false;
            for (Iterator<String> i = noValuesMonkey.iterator();i.hasNext();) {
                String name = i.next();
                Monkey monkey = monkeys.get(name);
                Long leftValue = monkeys.get(monkey.getMonkeyLeft().getName()).getValue();
                Long rightValue = monkeys.get(monkey.getMonkeyRight().getName()).getValue();
                if (!(monkey.getMonkeyLeft().getName().equals("humn") || monkey.getMonkeyRight().getName().equals("humn"))) {
                    if (leftValue != null &&
                            rightValue != null
                    ) {
                        monkey.setValue(monkey.getOperation().execute(leftValue, rightValue));
                        i.remove();
                        change = true;
                    }
                }
            }
        }
        System.out.println(monkeys.get("root").getMonkeyLeft().getValue());
        System.out.println(monkeys.get("root").getMonkeyRight().getValue());
        return 0L;
    }
}
