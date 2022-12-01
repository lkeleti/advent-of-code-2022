package dev.lkeleti;

import java.nio.file.Path;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        Service service = new Service();
        service.readInput(Path.of("src/main//resources/input.txt"));

        //The sorting only need for part 2
        service.sumCalories.sort(Comparator.reverseOrder());

        System.out.println("Answer of part 1:");
        System.out.println(service.sumCalories.stream().max(Comparator.naturalOrder()).orElseThrow(
                ()-> new IllegalArgumentException("Something went wrong!")
        ));

        Long topThreeCalories = service.sumCalories.get(0) + service.sumCalories.get(1) + service.sumCalories.get(2);
        System.out.println("Answer of part 2:");
        System.out.println(topThreeCalories);
    }
}