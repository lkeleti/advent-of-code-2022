package dev.lkeleti;

public class State {
    private String mySelection;
    private String enemySelection;

    public State(String enemySelection, String mySelection) {
        this.enemySelection = enemySelection;
        this.mySelection = mySelection;
    }

    public String getMySelection() {
        return mySelection;
    }

    public String getEnemySelection() {
        return enemySelection;
    }
}
