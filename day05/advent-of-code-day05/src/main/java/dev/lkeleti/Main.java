package dev.lkeleti;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Service service = new Service();
        service.readInput(Path.of("src/main/resources/input.txt"));
        System.out.println("Answer of part 1:");
        System.out.println(service.listLastLetters("p1"));
        service.readInput(Path.of("src/main/resources/input.txt"));
        System.out.println("Answer of part 2:");
        System.out.println(service.listLastLetters("p2"));
    }
}