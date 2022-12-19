package dev.lkeleti;

public class Blueprint {
    private final String name;
    private final int oreCosts;
    private final int clayCosts;
    private final int obsidianCostsOre;
    private final int obsidianCostsClay;
    private final int geodeCostsOre;
    private final int geodeCostsObsidian;

    public Blueprint(String name, int oreCosts, int clayCosts, int obsidianCostsOre, int obsidianCostsClay, int geodeCostsOre, int geodeCostsObsidian) {
        this.name = name;
        this.oreCosts = oreCosts;
        this.clayCosts = clayCosts;
        this.obsidianCostsOre = obsidianCostsOre;
        this.obsidianCostsClay = obsidianCostsClay;
        this.geodeCostsOre = geodeCostsOre;
        this.geodeCostsObsidian = geodeCostsObsidian;
    }

    public String getName() {
        return name;
    }

    public int getOreCosts() {
        return oreCosts;
    }

    public int getClayCosts() {
        return clayCosts;
    }

    public int getObsidianCostsOre() {
        return obsidianCostsOre;
    }

    public int getObsidianCostsClay() {
        return obsidianCostsClay;
    }

    public int getGeodeCostsOre() {
        return geodeCostsOre;
    }

    public int getGeodeCostsObsidian() {
        return geodeCostsObsidian;
    }
}
