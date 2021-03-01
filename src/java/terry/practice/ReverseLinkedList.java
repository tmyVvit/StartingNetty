package terry.practice;

public class ReverseLinkedList {

    public static void main(String[] args) {
        ReverseLinkedList reverseLinkedList = new ReverseLinkedList();
        int[] arr = {1,2,3};
        int[] empty = {};
        reverseLinkedList.reverseAndPrint(arr);
        reverseLinkedList.reverseAndPrint(empty);
    }

    public void reverseAndPrint(int[] arr) {
        System.out.print("Origin: ");
        Node list = arrayToList(arr);
        printList(list);
        Node reverse = reverse(list);
        System.out.print("Reversed: ");
        printList(reverse);
    }

    public Node arrayToList(int[] arr) {
        if (arr == null || arr.length == 0) return null;
        Node head, node;
        head = new Node(arr[0]);
        node = head;
        for (int i = 1; i < arr.length; i++) {
            node.next = new Node(arr[i]);
            node = node.next;
        }
        return head;
    }

    public void printList(Node head) {
        Node node = head;
        while (node != null) {
            System.out.print(node.val + " -> ");
            node = node.next;
        }
        System.out.println("null");
    }

    public Node reverse(Node head) {
        if (head == null || head.next == null) return head;
        Node pre = null, curr = head, next = head.next;
        while (curr != null) {
            curr.next = pre;
            pre = curr;
            curr = next;
            if (curr != null) next = curr.next;
        }
        return pre;
    }
}

class Node {
    int val;
    Node next;
    Node(int val){
        this.val = val;
    }
}
