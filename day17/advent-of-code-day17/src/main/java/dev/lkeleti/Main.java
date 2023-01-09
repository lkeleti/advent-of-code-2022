package dev.lkeleti;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Service service = new Service();
        service.readInput(Path.of("src/main/resources/input.txt"));
        System.out.println("Answer of part 1:");
        System.out.println(service.simulate());
        System.out.println("Answer of part 2:");
        //4448 high
        //3032
        //3179 good?
    }
}