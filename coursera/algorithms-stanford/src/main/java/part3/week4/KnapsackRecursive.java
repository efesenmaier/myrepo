package part3.week4;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import javafx.util.Pair;

public class KnapsackRecursive {

    // Inputs
    long[] values;
    long[] weights;
    int capacity;

    Cache<Pair<Integer, Integer>, Long> cache = CacheBuilder.newBuilder()
            .build();

    public KnapsackRecursive(long[] values, long[] weights, int capacity) {
        assert values != null;
        assert weights != null;
        assert values.length == weights.length;

        this.values = values;
        this.weights = weights;
        this.capacity = capacity;
    }

    public long find() {
        return findRecursive(values.length-1, capacity);
    }

    public long findRecursive(int i, int x) {
        if (i == -1) {
            return 0;
        }

        Pair<Integer, Integer> ix = new Pair<>(i, x);
        Long maxValue = cache.getIfPresent(ix);
        if (maxValue != null) {
            return maxValue;
        }

        // Case 1: Item i excluded
        Long maxValueCase1 = findRecursive(i-1, x);

        Long maxValueCase2 = Long.MIN_VALUE;
        long weight = weights[i];
        if (x - weight >= 0) {
            long value = values[i];
            maxValueCase2 = value + findRecursive(i-1, (int)(x-weight));
        }

        maxValue = Math.max(maxValueCase1.longValue(), maxValueCase2.longValue());
        cache.put(ix, maxValue);
        return maxValue;
    }
}
