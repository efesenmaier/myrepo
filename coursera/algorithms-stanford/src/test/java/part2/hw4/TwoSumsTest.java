package part2.hw4;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

@Test
public class TwoSumsTest {

    @DataProvider
    private Object[][] testData() throws Exception {
        return new Object[][] {
                {Arrays.asList(0, 0, 1, 2, 5, 8, 10, 10), 8, 10, 3},
                {Arrays.asList(0, 0, 1, 5, 8, 10, 10), 10, 10, 1},
                {Arrays.asList(-3, -1, 1, 2, 9, 11, 7, 6, 2), 3, 10, 8},
                // Counts a + b = t, where a = b and count(a) > 1
                {Arrays.asList(2, 2), 4, 4, 1},
                // Doesn't count a + b = t, where a = b and count(a) == 1
                {Arrays.asList(1, 2, 4), 4, 4, 0},
        };
    }

    @Test(dataProvider = "testData")
    public void test(List<Integer> values, Integer min, Integer max, Integer expectedCount) {
        HashMap<Long, Long> integerCounts = new HashMap<>();
        for (int i = 0; i < values.size(); ++i) {
            long value = values.get(i);
            integerCounts.put(value, integerCounts.getOrDefault(value, 0L) + 1L);
        }

        long count = TwoSums.findCountOfTargetsThatHasPairThatSumsToTarget(integerCounts, min, max);
        Assert.assertEquals(count, expectedCount.longValue());
    }


    /**
     * Runs too slow for unit tests.
     */
    @Test(enabled = false)
    public void testAssignmentData() {
        String filename = "twoSums.txt";
        HashMap<Long, Long> integerCount = new HashMap<>();

        try (Scanner scanner = new Scanner(getClass().getClassLoader().getResourceAsStream(filename))) {
            while (scanner.hasNextLong()) {
                long value = scanner.nextLong();
                integerCount.put(value, integerCount.getOrDefault(value, 0L) + 1L);
            }
        }

        long count = TwoSums.findCountOfTargetsThatHasPairThatSumsToTarget(integerCount, -10000L, 10000L);

        System.out.println("Number of targets with distinct two sums: " + count);
        Assert.assertEquals(count, 427);
    }

}
