package dynamicprogramming;

public class LengthOfLongestCommonSubsequence {

    static int find(int[] x, int[] y) {
        int max = 0;
        int[][] c = new int[x.length][y.length];

        for (int i = 0; i < x.length; ++i) {
            for (int j = 0; j < y.length; ++j) {
                if (x[i] == y[j]) {
                    int subSeqMax = 0;
                    if (i >= 1 && j >= 1) {
                        subSeqMax = c[i-1][j-1];
                    }
                    c[i][j] = subSeqMax + 1;
                    if (c[i][j] > max) {
                        max = c[i][j];
                    }
                } else {
                    int subSeqMax1 = 0;
                    if (i >= 1) {
                        subSeqMax1 = c[i-1][j];
                    }
                    int subSeqMax2 = 0;
                    if (j >= 1) {
                        subSeqMax2 = c[i][j-1];
                    }
                    c[i][j] = Math.max(subSeqMax1, subSeqMax2);
                    if (c[i][j] > max) {
                        max = c[i][j];
                    }
                }
            }
        }

        return max;
    }
}
