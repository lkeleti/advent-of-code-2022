package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {

    private List<Command> commandList = new ArrayList<>();
    private final char[] crt = " ".repeat(240).toCharArray();
    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                readCommandFromLine(line.trim());
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path);
        }
    }

    private void readCommandFromLine(String line) {
        String[] datas = line.split(" ");
        if (line.equals("noop")) {
            commandList.add( new Command("noop",-1));
        }
        else {
            commandList.add( new Command(datas[0],Integer.parseInt(datas[1])));
        }
    }

    public long processCommands() {
        int i = 0;
        int cycle = 0;
        int commandCycle = 1;
        int register = 1;
        long summa = 0;
        while (i < commandList.size()) {
            Command defaultCommand = commandList.get(i);
            int sync = cycle % 40;

            if (sync == register - 1 || sync == register || sync == register + 1) {
                crt[cycle] = '#';
            }

            if (defaultCommand.getName().equals("addx")) {
                if (commandCycle == defaultCommand.getCycle()) {
                    i++;
                    commandCycle = 0;
                    register += defaultCommand.getValue();
                }
            }
            else {
                i++;
                commandCycle = 0;
            }

            cycle++;
            commandCycle++;
            if (cycle == 20 || cycle == 60 || cycle == 100 || cycle == 140 || cycle == 180 || cycle == 220) {
                summa += (register*cycle);
            }
        }
        return summa;
    }
    public void printCrt() {
        for (int i = 0; i < crt.length; i ++) {
            if (i % 40 == 0) {
                System.out.printf("%n");
                System.out.printf("%s", crt[i]);
            }
            else {
                System.out.printf("%s", crt[i]);
            }
        }
        System.out.println("");
    }
}
