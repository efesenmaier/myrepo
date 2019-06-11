package part3.week4;

public class KnapsackDP {
    // Inputs
    long[] values;
    long[] weights;
    int capacity;

    // DP value of knapsack
    long[][] a;

    public KnapsackDP(long[] values, long[] weights, int capacity) {
        assert values != null;
        assert weights != null;
        assert values.length == weights.length;

        this.values = values;
        this.weights = weights;
        this.capacity = capacity;

        this.a = new long[values.length][capacity+1];
    }

    public long find() {
        for (int x = 0; x < capacity+1; ++x) {
            for (int i = 0; i < values.length; ++i) {
                long value = values[i];
                long weight = weights[i];
                if (weight > x) {
                    a[i][x] = getA(i-1, x);
                } else {
                    a[i][x] = Math.max(getA(i-1, x), getA(i-1, (int)(x-weight)) + value);
                }
            }
        }

        return a[values.length-1][capacity];
    }

    private long getA(int i, int x) {
        assert i >= -1;
        assert i < a.length;
        assert x >= 0;
        assert x < a[0].length;

        if (i == -1) {
            return 0;
        }

        return a[i][x];
    }
}
