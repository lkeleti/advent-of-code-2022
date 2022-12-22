package dev.lkeleti;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Service service = new Service();
        service.readInput(Path.of("src/main/resources/input.txt"));
        System.out.println("Answer of part 1:");
        System.out.println(service.simulate());
        System.out.println("Answer of part 2:");
        //4582 low
        //164022 high
        //83334 low
    }
}