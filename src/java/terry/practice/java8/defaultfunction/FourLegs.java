package terry.practice.java8.defaultfunction;

public interface FourLegs {
    default void print() {
        System.out.println("I have four legs");
    }
}
