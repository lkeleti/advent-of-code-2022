package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Service {

    private final Map<String, Valve> valves = new TreeMap<>();
    private final List<Integer> flows = new ArrayList<>();
    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                readValvesFromLine(line.trim());
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path);
        }
    }

    private void readValvesFromLine(String line) {
        //Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
        String name = line.substring(6,8);
        int rate = Integer.parseInt(line.substring(23,line.indexOf(";")));
        String[] names;
        if (line.contains("valves ")) {
            names = line.trim().substring(line.indexOf("valves ") + 7).split(",");
        }
        else {
            names = new String[]{line.trim().substring(line.indexOf("tunnel leads to valve ") + 22).trim()};
        }
        if (!valves.containsKey(name.trim())) {
            valves.put(name.trim(),
                    new Valve(name.trim(),rate)
            );
        }
        valves.get(name.trim()).setFlow(rate);
        for (String n: names) {
            if (!valves.containsKey(n.trim())) {
                valves.put(n.trim(), new Valve(n.trim())
                );
            }
            valves.get(name.trim()).addValve(valves.get(n.trim()));
        }
    }

    public int partOne() {
        Map<String, Boolean> isOpen = new TreeMap<>();
        solve(valves.get("AA"), isOpen, 0, 0);
        return flows.stream().mapToInt(f -> f).max().getAsInt();
    }
    private void solve(Valve start, Map<String,Boolean> isOpen, int minute, int sumFlow) {
        if (minute >= 30) {
            flows.add(sumFlow);
        }
        else {
            int cikl = 1;
            if (start.getFlow() > 0 && !start.isOpen() && !isOpen.containsKey(start.getName())) {
                cikl = 2;
            }
            for (int i = 0; i < cikl; i++) {
                if (i == 1) {
                    sumFlow = getSumFlow(isOpen, sumFlow);
                    isOpen.put(start.getName(), true);
                    minute++;
                }
                for (Valve valve : start.getValves()) {
                    minute++;
                    sumFlow = getSumFlow(isOpen, sumFlow);
                    Map<String, Boolean> newOpenList = new TreeMap<>(isOpen);
                    solve(valve, newOpenList, minute, sumFlow);
                }
            }
        }
    }

    private int getSumFlow(Map<String, Boolean> isOpen, int sumFlow) {
        for (String name : isOpen.keySet()) {
            sumFlow += valves.get(name).getFlow();
        }
        return sumFlow;
    }

}
