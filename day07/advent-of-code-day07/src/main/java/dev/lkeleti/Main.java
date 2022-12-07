package dev.lkeleti;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Service service = new Service();
        service.readInput(Path.of("src/main/resources/input.txt"));
        System.out.println("Answer of part 1:");
        System.out.println(service.countTotal());
        System.out.println("Answer of part 2:");
        System.out.println("The size of the root directory is: " + service.getRootDir().getSize());
        System.out.println(service.findSmallestDeletable());
        //1563865 too high
    }
}