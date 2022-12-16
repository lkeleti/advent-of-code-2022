package dev.lkeleti;

import java.util.ArrayList;
import java.util.List;

public class Valve {
    private String name;
    private boolean open = false;
    private int flow;
    private List<Valve> valves = new ArrayList<>();

    public Valve(String name) {
        this.name = name;
    }

    public Valve(String name, int flow) {
        this.name = name;
        this.flow = flow;
    }

    public String getName() {
        return name;
    }

    public boolean isOpen() {
        return open;
    }

    public int getFlow() {
        return flow;
    }

    public List<Valve> getValves() {
        return valves;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }

    public void addValve(Valve valve) {
        valves.add(valve);
    }
}
