package terry.practice;

public class Node1 {
    int i;
    Node1 next;

    Node1(int i) {
        this.i = i;
    }

    Node1(Node1 n, int i) {
        this.i = i;
        this.next = n;
    }

    @Override
    public String toString() {
        return i + " ";
    }

    public static void test1() {
        Node1 nn = new Node1(0);
        Node1 n1 = new Node1(nn, 1);
        Node1 n2 = n1;

        n1.next = n1 = new Node1(nn, 3);
    }

    public static void test2() {
        Node1 nn = new Node1(0);
        Node1 n1 = new Node1(nn, 1);
        Node1 n2 = n1;

        n1 = new Node1(nn, 3);
        n1.next = n1;
    }
}