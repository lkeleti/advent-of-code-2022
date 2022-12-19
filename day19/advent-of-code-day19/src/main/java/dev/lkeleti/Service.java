package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {

    private List<Blueprint> blueprints = new ArrayList<>();

    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                readBlueprintFromLine(line.trim());
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path);
        }
    }

    private void readBlueprintFromLine(String line) {
        String name = line.substring(0, line.indexOf(":"));
        int start = line.indexOf("Each ore robot costs ") + 21;
        int oreCosts = Integer.parseInt(line.substring(start, start +  1));
        start = line.indexOf("Each clay robot costs ") + 22;
        int clayCosts = Integer.parseInt(line.substring(start, start +  1));
        start = line.indexOf("Each obsidian robot costs ") + 26;
        int obsidianCostsOre = Integer.parseInt(line.substring(start, start +  1));
        start = line.indexOf("ore and ") + 8;
        int end = line.indexOf(" clay.");
        int obsidianCostsClay = Integer.parseInt(line.substring(start, end));
        start = line.indexOf("Each geode robot costs ") + 23;
        int geodeCostsOre = Integer.parseInt(line.substring(start, start +  1));
        start = line.indexOf("ore and ", start) + 8;
        end = line.indexOf(" obsidian.");
        int geodeCostsObsidian = Integer.parseInt(line.substring(start, end));
        blueprints.add(
                new Blueprint(name, oreCosts, clayCosts, obsidianCostsOre, obsidianCostsClay, geodeCostsOre, geodeCostsObsidian)
        );
    }
}
