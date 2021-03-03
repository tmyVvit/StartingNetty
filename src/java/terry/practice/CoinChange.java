package terry.practice;
/*
给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。

        你可以认为每种硬币的数量是无限的。

        来源：力扣（LeetCode）
        链接：https://leetcode-cn.com/problems/coin-change
        著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

 */
public class CoinChange {
    public int coinChange(int[] coins, int amount) {
        int[] cache = new int[amount];
        return change(coins, cache, amount);
    }
    public int change(int[] coins, int[] cache, int amount) {
        if (amount < 0) return -1;
        if (amount == 0) return 0;
        if (cache[amount-1]!=0) return cache[amount-1];
        int min = Integer.MAX_VALUE;
        for(int coin : coins) {
            // 每次选中一个硬币计算
            int res = change(coins, cache, amount - coin);
            // 如果有结果并且小于之前的最小值，则更新最小值
            if (res >= 0 && res < min) {
                min = res + 1;
            }
        }
        // 将本次计算结果存入cache中
        cache[amount-1] = min == Integer.MAX_VALUE ? -1 : min;
        return cache[amount-1];
    }
}
