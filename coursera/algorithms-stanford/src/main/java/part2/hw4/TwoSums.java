package part2.hw4;

import java.util.HashMap;

/**
 * This class takes a range
 */
public class TwoSums {

    public static long findCountOfTargetsThatHasPairThatSumsToTarget(HashMap<Long, Long> integersCounts, long min, long max) {
        int count = 0;
        for (long target = min; target <= max; ++target) {
            if (TwoSums.hasPairThatSumsToTarget(integersCounts, target)) {
                ++count;
            }
        }
        return count;
    }

    public static boolean hasPairThatSumsToTarget(HashMap<Long, Long> integersCounts, long target) {
        for (HashMap.Entry<Long, Long> entry : integersCounts.entrySet()) {
            long a = entry.getKey();
            long countA = entry.getValue();
            long b = target - a;
            if ((a != b && integersCounts.containsKey(b)) ||
                    (a == b && countA > 1)) {
                return true;
            }
        }
        return false;
    }
}
