package terry.practice;

import java.util.LinkedHashMap;
import java.util.LinkedList;

// 给定一个循环数组（最后一个元素的下一个元素是数组的第一个元素），输出每个元素的下一个更大元素。数字 x 的下一个更大的元素是按数组遍历顺序，
// 这个数字之后的第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1。
//
//来源：力扣（LeetCode）
//链接：https://leetcode-cn.com/problems/next-greater-element-ii
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
public class NextBiggerNumII {
    public static void main(String[] args) {
        NextBiggerNumII bigger = new NextBiggerNumII();
        int[] nums = {1, 2, 1};
        print(bigger.nextGreaterElements(nums));

    }

    public static void print(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(arr[i]);
        }
        System.out.println();
    }

    public int[] nextGreaterElements(int[] nums) {
        if (nums == null || nums.length == 0) return new int[0];
        // 使用单调栈
        LinkedList<Integer> stack = new LinkedList<Integer>();
        int[] res = new int[nums.length];
        int[] flag = new int[nums.length];
        for (int i = 0; i < 2*nums.length; i++) {
            int index = i % nums.length;
            while(!stack.isEmpty() && nums[index] > stack.peekLast()) {
                int ind = stack.removeLast();
                if (flag[ind] == 0) {
                    res[ind] = nums[index];
                    flag[ind] = 1;
                }
            }
            stack.addLast(index);
        }
        while(!stack.isEmpty()) {
            int ind = stack.removeFirst();
            if (flag[ind] == 0) {
                flag[ind] = 1;
                res[ind] = -1;
            }
        }
        return res;
    }
}
