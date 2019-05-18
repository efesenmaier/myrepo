import java.util.Random;

/**
 * See https://www.hackerrank.com/challenges/fraudulent-activity-notifications
 */
public class FraudulentActivitiesNotification {
    public static int MAX_AMOUNT = 200;
    // Complete the activityNotifications function below.
    static int activityNotifications(int[] expenditure, int d) {
        int[] history = new int[d];
        int[] sortedAmounts = new int[d];
        int[] countOfAmounts = new int[MAX_AMOUNT];

        int numNotifications = 0;
        for (int i = 0; i < expenditure.length; ++i) {
            int amount = expenditure[i];

            if (i >= d) {
                double twiceMedian = 2.0 * getMedianFromCounts(countOfAmounts, sortedAmounts);
                if (amount >= twiceMedian) {
                    ++numNotifications;
                }
                --countOfAmounts[history[i%d]];
            }

            history[i%d] = amount;
            ++countOfAmounts[amount];
        }
        return numNotifications;
    }

    static double getMedianFromCounts(int[] countOfAmounts, int[] sortedAmounts) {
        int j = 0;
        for (int i = 0; i < countOfAmounts.length; ++i) {
            int count = countOfAmounts[i];
            while (count > 0) {
                sortedAmounts[j++] = i;
                --count;
            }
        }

        boolean odd = sortedAmounts.length % 2 == 1;
        int median = (sortedAmounts.length - 1) / 2;
        if (odd) {
            return sortedAmounts[median];
        }
        return (sortedAmounts[median] + sortedAmounts[median + 1]) / 2.0;
    }

}
