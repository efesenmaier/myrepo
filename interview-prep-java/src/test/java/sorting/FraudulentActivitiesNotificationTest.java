package sorting;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

@Test
public class FraudulentActivitiesNotificationTest {
    @DataProvider
    private Object[][] testData() throws Exception {
        return new Object[][] {
                // Basic
                {5, 2, "2 3 4 2 3 6 8 4 5"},
                {4, 0, "1 2 3 4 4"},
                {10000, 633, readFileToString("/FraudulentActivitiesTestCase1.txt")},
        };
    }

    @Test(dataProvider = "testData")
    public void test(Integer d, Integer expected, String expenditure) {
        Integer result = FraudulentActivitiesNotification.activityNotifications(toIntArray(expenditure), d);
        Assert.assertEquals(result, expected);
    }

    @DataProvider
    private Object[][] testMedianData() {
        return new Object[][] {
                // Basic
                {"1 2 3", 2.0},
                {"1 2 3 4", 2.5},
                {"2 3 1 2 3 4 2 3", 2.5},
        };
    }

    @Test(dataProvider = "testMedianData")
    public void testMedian2(String expenditure, Double expected) {
        int[] history = toIntArray(expenditure);
        int[] countOfAmounts = new int[FraudulentActivitiesNotification.MAX_AMOUNT];
        for (int i = 0; i < history.length; ++i) {
            countOfAmounts[history[i]]++;
        }
        Double result = FraudulentActivitiesNotification.getMedianFromCounts(countOfAmounts, history);
        Assert.assertEquals(result, expected);
    }

    private int[] toIntArray(String list) {
        String[] ints = list.split("\\s");
        return Arrays.stream(ints)
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private String readFileToString(String filename) throws Exception {
        String path = getClass().getResource(filename).getPath();
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}
