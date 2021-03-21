package terry.practice.java8.defaultfunction;

public interface Animal {
    default void print() {
        System.out.println("This is an animal");
    }
}
