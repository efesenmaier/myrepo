package dynamicprogramming;

/**
 * https://www.hackerrank.com/challenges/max-array-sum/problem
 */
public class MaxArraySum {

    // Complete the maxSubsetSum function below.
    static int maxSubsetSum(int[] arr) {
        int[] maxSum = new int[arr.length];

        for (int i = 0; i < arr.length; ++i) {
            int val = arr[i] > 0 ? arr[i] : 0;

            int j = i - 2;
            int maxPrev = j >= 0 ? maxSum[j] : 0;

            j = i - 3;
            int maxPrevPrev = j >= 0 ? maxSum[j] : 0;

            maxSum[i] = val + Math.max(0, Math.max(maxPrev, maxPrevPrev));
        }

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < maxSum.length; ++i) {
            if (maxSum[i] > max) {
                max = maxSum[i];
            }
        }
        return max;
    }
}
