package part2graphs.hw3;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Test
public class RunningMedianTest {
    @DataProvider
    private Object[][] testData() throws Exception {
        return new Object[][] {
                // Basic
                {Arrays.asList(0, 100, 100, 0, 0, 100, 50, 50), Arrays.asList(0, 0, 100, 0, 0, 0, 50, 50)},
        };
    }

    @Test(dataProvider = "testData")
    public void test(List<Integer> values, List<Integer> medians) {
        RunningMedian<Integer> runningMedian = new RunningMedian<>();

        assert values.size() == medians.size();

        for (int i = 0; i < values.size(); ++i) {
            int value = values.get(i);
            runningMedian.add(value);
            Integer median = medians.get(i);
            Assert.assertEquals(runningMedian.median(), median);
        }
    }

    public void testAssignmentData() {
        String filename = "median.txt";
        RunningMedian<Integer> runningMedian = new RunningMedian<>();
        long sum = 0;
        try (Scanner scanner = new Scanner(getClass().getClassLoader().getResourceAsStream(filename))) {
            while (scanner.hasNextInt()) {
                runningMedian.add(scanner.nextInt());
                sum = Math.addExact(sum, runningMedian.median().longValue());
            }
        }

        System.out.println("Sum of medians % 10000: " + (sum % 10000));
    }
}
