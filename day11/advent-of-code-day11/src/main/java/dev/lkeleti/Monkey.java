package dev.lkeleti;

import java.util.ArrayList;
import java.util.List;

public class Monkey {

    private final int name;
    private final List<Long> items = new ArrayList<>();
    private final Operation operation;
    private final int test;
    private final int trueMonkey;
    private final int falseMonkey;

    public long inspectCounter = 0;

    public Monkey(int name, Operation operation, int test, int trueMonkey, int falseMonkey) {
        this.name = name;
        this.operation = operation;
        this.test = test;
        this.trueMonkey = trueMonkey;
        this.falseMonkey = falseMonkey;
    }

    public int getName() {
        return name;
    }

    public Operation getOperation() {
        return operation;
    }

    public int getTest() {
        return test;
    }

    public int getTrueMonkey() {
        return trueMonkey;
    }

    public int getFalseMonkey() {
        return falseMonkey;
    }

    public long getInspectCounter() {
        return inspectCounter;
    }

    public void addItem(long item) {
        items.add(item);
    }

    public long getFirstItem() {
        long item = items.get(0);
        items.remove(0);
        return item;
    }

    public int getItemSize() {
        return items.size();
    }

    public void incInspectCounter() {
        inspectCounter++;
    }
}
