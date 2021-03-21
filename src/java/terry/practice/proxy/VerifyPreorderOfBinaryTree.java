package terry.practice.proxy;

import java.util.Stack;

//序列化二叉树的一种方法是使用前序遍历。
// 当我们遇到一个非空节点时，我们可以记录下这个节点的值。
// 如果它是一个空节点，我们可以使用一个标记值记录，例如 #。
// 序列化二叉树的一种方法是使用前序遍历。当我们遇到一个非空节点时，我们可以记录下这个节点的值。如果它是一个空节点，我们可以使用一个标记值记录，例如 #。
// https://leetcode-cn.com/problems/verify-preorder-serialization-of-a-binary-tree/
public class VerifyPreorderOfBinaryTree {

    public static void main(String[] args) {
        VerifyPreorderOfBinaryTree verify = new VerifyPreorderOfBinaryTree();
        boolean res = verify.isValidSerialization("#");
        System.out.println(res);
    }

    public boolean isValidSerialization(String preorder) {
        if (preorder == null || preorder.length() < 1) return false;
        if ("#".equals(preorder)) return true;
        String[] nodes = preorder.split(",");
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        for (int i = 1; i < nodes.length; i++) {
            String node = nodes[i];
            if (stack.isEmpty()) return false;
            if ("#".equals(node)) {
                while (!stack.isEmpty() && stack.peek() == 1) stack.pop();
                if (!stack.isEmpty()) {
                    stack.pop();
                    stack.push(1);
                }
            } else {
                stack.push(0);
            }
        }
        return stack.isEmpty();
    }

}
