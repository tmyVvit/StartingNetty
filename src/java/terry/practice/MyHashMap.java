package terry.practice;

class MyHashMap {

    public static void main(String[] args) {
        MyHashMap myHashMap = new MyHashMap();
        myHashMap.put(1, 1); // myHashMap 现在为 [[1,1]]
        myHashMap.put(2, 2); // myHashMap 现在为 [[1,1], [2,2]]
        myHashMap.get(1);    // 返回 1 ，myHashMap 现在为 [[1,1], [2,2]]
        myHashMap.get(3);    // 返回 -1（未找到），myHashMap 现在为 [[1,1], [2,2]]
        myHashMap.put(2, 1); // myHashMap 现在为 [[1,1], [2,1]]（更新已有的值）
        myHashMap.get(2);    // 返回 1 ，myHashMap 现在为 [[1,1], [2,1]]
        myHashMap.remove(2); // 删除键为 2 的数据，myHashMap 现在为 [[1,1]]
        myHashMap.get(2);

    }

    private Node[] tables;
    private int length;

    /** Initialize your data structure here. */
    public MyHashMap() {
        tables = new Node[1024];
        length = 1024;
    }

    /** value will always be non-negative. */
    public void put(int key, int value) {
        int index;
        Node pre, node;
        if ((pre = tables[index = (hash(key) & (length - 1))])== null) {
            tables[index] = new Node(key, value);
        } else {
            if (pre.key == key) pre.val = value;
            else {
                node = pre.next;
                while (node != null) {
                    if (node.key == key) {
                        node.val = value;
                        return ;
                    }
                    pre = node;
                    node = node.next;
                }
                pre.next = new Node(key, value);
            }
        }
    }

    /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
    public int get(int key) {
        int index;
        Node node;
        if (( node = tables[index = (hash(key) & (length - 1))]) != null) {
            while (node != null) {
                if (node.key == key) return node.val;
                node = node.next;
            }
        }
        return -1;
    }

    /** Removes the mapping of the specified value key if this map contains a mapping for the key */
    public void remove(int key) {
        int index;
        Node node;
        if ((node = tables[index = (hash(key) & (length - 1))]) != null) {
            if (node.key == key) {
                tables[index] = node.next;
            } else {
                while (node.next != null) {
                    Node next = node.next;
                    if (next.key == key) {
                        node.next = next.next;
                        next.next = null;
                        return;
                    }
                    node = node.next;
                }
            }
        }
    }

    private int hash(int key) {
        return key ^ (key >>> 16);
    }

    static class Node {
        int key;
        int val;
        Node next;
        Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }
}

/**
 * Your MyHashMap object will be instantiated and called as such:
 * MyHashMap obj = new MyHashMap();
 * obj.put(key,value);
 * int param_2 = obj.get(key);
 * obj.remove(key);
 */
