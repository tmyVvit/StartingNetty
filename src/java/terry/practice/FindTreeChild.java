package terry.practice;

import java.util.ArrayList;
import java.util.List;

/*

给你一棵树（即，一个连通的无环无向图），这棵树由编号从 0  到 n - 1 的 n 个节点组成，且恰好有 n - 1 条 edges 。树的根节点为节点 0 ，树上的每一个节点都有一个标签，也就是字符串 labels 中的一个小写字符（编号为 i 的 节点的标签就是 labels[i] ）

边数组 edges 以 edges[i] = [ai, bi] 的形式给出，该格式表示节点 ai 和 bi 之间存在一条边。

返回一个大小为 n 的数组，其中 ans[i] 表示第 i 个节点的子树中与节点 i 标签相同的节点数。

树 T 中的子树是由 T 中的某个节点及其所有后代节点组成的树。
 */

public class FindTreeChild {
    public int[] countSubTrees(int n, int[][] edges, String labels) {
        int[][] matrix = new int[n][n];
        int[] result = new int[n], grade = new int[n];
        for (int i = 0; i < n; i++) {
            if (grade[i] > 0) continue;
            List<Integer> queue = new ArrayList<>();
            queue.add(0);
            while (queue.size() > 0) {
                List<Integer> tmp = queue;
                queue = new ArrayList<>();
                for (Integer head : tmp) {
                    for (int[] edge : edges) {
                        if (edge[0] == head && grade[edge[1]] == 0) {
                            queue.add(edge[1]);
                            grade[edge[1]] = grade[edge[0]]+1;
                        } else if (edge[1] == head && grade[edge[0]] == 0) {
                            queue.add(edge[0]);
                            grade[edge[0]] = grade[edge[1]] + 1;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < n - 1; i++) {
            int x = edges[i][0], y = edges[i][1];
            matrix[x][y] = 1;
            matrix[y][x] = 1;
        }
        for (int i = 0; i < n; i++) {
            result[i] = find(i, matrix, labels, grade);
        }
        return result;
    }


    private int find(int node, int[][] matrix, String labels, int[] grade) {
        List<Integer> queue = new ArrayList<>();
        int count = 1, start = 0, end = 1;
        queue.add(node);
        while (start < end) {
            int point = queue.get(start++);
            for (int i = 0; i < matrix[point].length; i++) {
                if (matrix[point][i] == 1 && grade[point] < grade[i]) {
                    queue.add(i);
                    end++;
                    if (labels.charAt(i) == labels.charAt(node)) count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        /*4
                [[0,1],[1,2],[0,3]]
        "bbbb" */
        FindTreeChild solution = new FindTreeChild();
        int[][] edges = {{0, 2}, {0, 3}, {1, 2}};
        int[] result = solution.countSubTrees(4, edges, "aeed");
        for (int n : result) {
            System.out.println(n);
        }
    }
}
