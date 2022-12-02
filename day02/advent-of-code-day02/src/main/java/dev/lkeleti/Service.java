package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Service {
    private List<State> states = new ArrayList<>();
    private final Map<String,Integer> points = new HashMap<>();
    private final Map<String,String> lose = new HashMap<>();
    private final Map<String,String> win = new HashMap<>();

    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                readFromLine(line);
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path);
        }
    }

    private void readFromLine(String line) {
        String[] datas = line.split(" ");
        states.add(
                new State(datas[0], datas[1].trim())
        );
    }

    private long roundScore(String playerOne, String playerTwo) {
        long score = 0;
        score += points.get(playerOne);

        if (points.get(playerOne) == points.get(playerTwo)) {
            score += 3;
        }
        else {
            if ((playerOne.equals("X") && playerTwo.equals("C")) || (playerOne.equals("Y") && playerTwo.equals("A")) || (playerOne.equals("Z") && playerTwo.equals("B"))) {
                score += 6;
            }
        }
        return score;
    }


    public int getTotal(String part) {
        int total = 0;
        for (State state: states) {
            if (part.equals("p1")) {
                total += roundScore(state.getMySelection(), state.getEnemySelection());
            }
            else {
                total += finalScore(state.getMySelection(), state.getEnemySelection());
            }
        }
        return total;
    }

    private int finalScore(String mySelection, String enemySelection) {
        int score = 0;
        if (mySelection.equals("X")) {
            score += points.get(lose.get(enemySelection));
        }
        else if (mySelection.equals("Y")) {
            score += points.get(enemySelection);
            score += 3;
        }
        else {
            score += points.get(win.get(enemySelection));
            score += 6;
        }
        return score;
    }

    public Service() {
        points.put("A",1);
        //A - rock
        points.put("B",2);
        //B - paper
        points.put("C",3);
        //C - scissor
        points.put("X",1);
        //X - rock
        points.put("Y",2);
        //Y - paper
        points.put("Z",3);
        //Z - scissor

        lose.put("A","C");
        lose.put("B","A");
        lose.put("C","B");

        win.put("A","B");
        win.put("B","C");
        win.put("C","A");
    }
}
