package dev.lkeleti;

public class Main {
    public static void main(String[] args) {
        Service service = new Service();
        System.out.println("Answer of part 1:");
        System.out.println(service.findMax(20,"p1"));
        System.out.println("Answer of part 2:");
        System.out.println(service.findMax(10000,"p2"));
    }
}