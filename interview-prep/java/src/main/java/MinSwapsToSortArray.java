
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

/**
 * See https://www.geeksforgeeks.org/minimum-number-swaps-required-sort-array/ for background
 */
public class MinSwapsToSortArray {
    private static class Pair<K, V> {
        private K key;
        private V value;
        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }

        @Override
        public boolean equals(Object other) {
            if (other == null || other.getClass() != getClass()) {
                return false;
            }

            Pair<K, V> otherPair = (Pair<K, V>)other;
            return Objects.equals(key, otherPair.key) &&
                    Objects.equals(value, otherPair.value);
        }
    }

    // Complete the minimumSwaps function below.
    static int minimumSwaps(int[] arr) {
        int n = arr.length;

        // In order to find the rightful position of each element, create a pair with the
        // elements value and it's position in the array.
        ArrayList<Pair<Integer, Integer>> sorted = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            sorted.add(new Pair<>(arr[i], i));
        }

        // Sort the array by value
        // Afterwards, the second elements will form a cycle of swapped elements
        Collections.sort(sorted, Comparator.comparing(Pair::getKey));

        int minSwaps = 0;
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; ++i) {
            if (visited[i]) {
                continue;
            }

            visited[i] = true;

            if (sorted.get(i).getValue() == i) {
                continue;
            }

            int next = sorted.get(i).getValue();
            int cycleLength = 1;
            while (!visited[next]) {
                visited[next] = true;
                ++cycleLength;
                next = sorted.get(next).getValue();
            }

            minSwaps += (cycleLength - 1);
        }

        return minSwaps;
    }

}
