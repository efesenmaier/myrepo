import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class MinSwapsToSortArrayTest {
    @DataProvider
    private Object[][] testData() {
        return new Object[][] {
                // Basic
                {Arrays.asList(), 0},
                {Arrays.asList(1), 0},
                {Arrays.asList(1, 2), 0},
                {Arrays.asList(2, 1), 1},
                {Arrays.asList(1, 2, 3), 0},
                {Arrays.asList(1, 3, 2), 1},
                {Arrays.asList(2, 1, 3), 1},
                {Arrays.asList(2, 3, 1), 2},
                {Arrays.asList(3, 2, 1), 1},
                {Arrays.asList(3, 1, 2), 2},
                {Arrays.asList(1, 2, 3, 4), 0},
                {Arrays.asList(4, 3, 2, 1), 2},
                {Arrays.asList(4, 1, 2, 3), 3},
                {Arrays.asList(7, 1, 3, 2, 4, 5, 6), 5},
                {Arrays.asList(1, 3, 5, 2, 4, 6, 7), 3},

        };
    }

    @Test(dataProvider = "testData")
    public void test(List<Integer> array, Integer expectedMinSwaps) {
        int minSwaps = MinSwapsToSortArray.minimumSwaps(toArray(array));
        Assert.assertEquals(minSwaps, expectedMinSwaps.intValue());
    }

    private int[] toArray(List<Integer> list) {
        int[] arr = new int[list.size()];
        int i = 0;
        for (Integer j : list) {
            arr[i++] = j;
        }
        return arr;
    }

}
