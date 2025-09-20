package org.example.strategy_pattern;

import java.util.Arrays;

public class Main {
    static class Pair implements Comparable<Pair>{
        int idx;
        int val;

        Pair(int idx, int val) {
            this.idx = idx;
            this.val = val;
        }

        @Override
        public int compareTo(Pair o) {
            return this.val - o.val;
        }
    }

    public int[] twoSum(int[] nums, int target) {
        Pair[] pairs = new Pair[nums.length];
        for (int i = 0; i < nums.length; i++) {
            pairs[i] = new Pair(i, nums[i]);
        }
        Arrays.sort(pairs);
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int sum = pairs[left].val + pairs[right].val;
            if (sum < target) {
                left++;
            } else if (sum > target) {
                right--;
            } else {
                return new int[]{pairs[left].idx, pairs[right].idx};
            }
        }
        return new int[]{-1, -1};
    }

    public static void main(String[] args) {
        Main m = new Main();
        int[] nums = {3, 2, 4};
        int target = 6;
        int[] res = m.twoSum(nums, target);
        System.out.println("[" + res[0] + ", " + res[1] + "]");
    }
}
