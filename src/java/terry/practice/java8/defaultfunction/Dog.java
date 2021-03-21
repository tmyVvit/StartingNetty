package terry.practice.java8.defaultfunction;

import java.util.ArrayList;
import java.util.List;

public class Dog implements Animal, FourLegs {
    @Override
    public void print() {
        System.out.println("This is a dog");
    }
}
