package dev.lkeleti;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class Service {
    private List<State> states = new ArrayList<>();
    private final Dictionary<String,Integer> points = new Hashtable<>();

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
    }

    public int getTotal() {
        int total = 0;
        for (State state: states) {
            total += roundScore(state.getMySelection(), state.getEnemySelection());
        }
        return total;
    }
}
