import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * https://www.hackerrank.com/challenges/count-triplets-1
 *
 *
 */
public class CountTriplets {

    // Complete the countTriplets function below.
    static long countTriplets(List<Long> arr, long r) {
        long rSq = r * r;
        long numTriplets = 0;

        HashMap<Long, Long> iCounts = new HashMap<>();
        HashMap<Pair<Long, Long>, Long> ijCounts = new HashMap<>();

        for (Iterator<Long> i = arr.iterator(); i.hasNext();) {
            long value = i.next();

            // Update count of triplets (assuming value is k)
            if (value % rSq == 0) {
                long iValue = value / rSq;
                long jValue = value / r;

                Pair<Long, Long> ijPair = new Pair<>(iValue, jValue);
                numTriplets += ijCounts.getOrDefault(ijPair, 0L);
            }

            // Update count of i, j pairs (assuming value is j)
            if (value % r == 0) {
                long iValue = value / r;
                long iCount = iCounts.getOrDefault(iValue, 0L);

                if (iCount > 0) {
                    Pair<Long, Long> ijPair = new Pair<>(iValue, value);
                    ijCounts.put(ijPair, ijCounts.getOrDefault(ijPair, 0L) + iCount);
                }
            }

            // Update count of i values (assuming value is i)
            iCounts.put(value, iCounts.getOrDefault(value, 0L) + 1);
        }
        return numTriplets;
    }

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
}
