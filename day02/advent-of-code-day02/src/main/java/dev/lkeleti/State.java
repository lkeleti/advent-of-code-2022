package dev.lkeleti;

public class State {
    private String mySelection;
    private String enemySelection;

    public State(String mySelection, String enemySelection) {
        this.mySelection = mySelection;
        this.enemySelection = enemySelection;
    }

    public String getMySelection() {
        return mySelection;
    }

    public String getEnemySelection() {
        return enemySelection;
    }
}
