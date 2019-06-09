package part3.week3;

import java.util.BitSet;

public class MaxWeightIndependentSetDP {

    // Input
    long[] weights;

    // Working data
    long a[];

    boolean[] weightIncludedTemp;
    boolean[] weightIncluded;

    public MaxWeightIndependentSetDP(long[] weights) {
        this.weights = weights;
        this.weightIncludedTemp = new boolean[weights.length];
        this.weightIncluded = new boolean[weights.length];
        a = new long[weights.length+1];
    }

    public void run() {
        a[0] = 0;
        a[1] = weights[0];
        weightIncludedTemp[0] = true;

        for (int i = 1; i < weights.length; ++i) {
            // Get max weight, assuming current weight is NOT in solution
            long currentWeight = weights[i];
            long maxWeightWithoutCurrent = a[i];
            long maxWeightWithCurrent = a[i-1] + currentWeight;
            if (maxWeightWithCurrent > maxWeightWithoutCurrent) {
                a[i+1] = maxWeightWithCurrent;
                weightIncludedTemp[i] = true;
            } else {
                a[i+1] = maxWeightWithoutCurrent;
            }
        }

        for (int i = weightIncludedTemp.length-1; i >= 0;) {
            if (weightIncludedTemp[i]) {
                weightIncluded[i] = true;
                i = i-2;
            } else {
                --i;
            }
        }
    }



}
