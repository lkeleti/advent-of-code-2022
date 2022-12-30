package dev.lkeleti;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Service {

    private final List<Equation> equations = new ArrayList<>();

    public void readInput(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            String part1 = "";
            while ((line = br.readLine()) != null) {
                if (part1.isBlank()) {
                    part1 = line.trim();
                }
                else {
                    equations.add(
                            new Equation(part1, line.trim())
                    );
                    part1 = "";
                }
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file: " + path);
        }
    }

    public long countEquations(){
        List<Long> indecies = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        long count = 0;
        JsonNode jnOne;
        JsonNode jnTwo;
        long counter = 0;
        for (Equation equation: equations) {
            try {
                jnOne = mapper.readTree(equation.getPart1());
                jnTwo = mapper.readTree(equation.getPart2());
            }
            catch (IOException  e) {
                throw new IllegalArgumentException("Something went wrong!", e);
            }
            counter++;
            if (compareEquation(jnOne, jnTwo) < 0) {
                count += counter;
                indecies.add(counter);
            }
        }
        System.out.println(indecies);
        return count;
    }

   private int compareEquation(JsonNode part1, JsonNode part2) {
        if (part1 != null && part2 == null) {
            return -1;
        }

        if (part1.isInt() && part2.isInt()) {
            return part1.asInt() - part2.asInt();
        }
        else {
            if (part1.isArray() && part2.isArray()) {
                ArrayNode p1 = (ArrayNode) part1;
                ArrayNode p2 = (ArrayNode) part2;
                int res = -1;
                int p1Size = p1.size();
                int p2Size = p2.size();
                for (int i = 0; i < Math.min(p1.size(), p2.size()); i++) {
                    res = compareEquation(p1.get(i), p2.get(i));
                    if (res < 0) {
                        return -1;
                    }
                    if (res > 0) {
                        return 1;
                    }
                }

                if (p1Size == 0 && p2Size > 0) {
                    return -1;
                }
                else if(p2Size == 0 && p1Size > 0) {
                    return 1;
                }

                if (res == 0) {
                    return p1Size - p2Size;
                }
                else {
                    return 1;
                }
            }
            else if (part1.isArray() && part2.isInt()) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    part2 = mapper.readTree("[" + part2.asText() + "]");
                } catch (IOException e) {
                    throw new IllegalArgumentException("Something went wrong!", e);
                }
                return compareEquation(part1, part2);
            }
            else if (part1.isInt() && part2.isArray()) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    part1 = mapper.readTree("[" + part1.asText() + "]");
                } catch (IOException e) {
                    throw new IllegalArgumentException("Something went wrong!", e);
                }
                return compareEquation(part1, part2);
            }
        }
        return 1;
    }
}
