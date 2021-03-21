package terry.practice.test;

public class Man extends Person {
    protected int age;
    public Man() {
        age = 18;
    }

    public void eat() {
        System.out.println("man eat");
    }

    public static void main(String[] args) {
        Man p = new Man();
        p.eat();
        System.out.println(p.age);
    }
}
