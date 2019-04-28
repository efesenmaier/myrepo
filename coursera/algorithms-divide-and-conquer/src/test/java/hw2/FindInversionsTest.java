package hw2;

import com.google.common.collect.Lists;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Scanner;

@Test
public class FindInversionsTest {

    @DataProvider
    private Object[][] testData() {
        return new Object[][] {
                // Basic
                {Lists.newArrayList(5, 4, 3, 2, 1), 10},
                {Lists.newArrayList(1, 3, 5, 2, 4, 6), 3},
        };
    }
    @Test(dataProvider = "testData")
    public void basicTest(List<Integer> a, Integer expectedCount) {
        int[] arr = toArray(a);
        long count = FindInversions.find(arr);
        Assert.assertEquals(count, expectedCount.intValue(), "Array starting with " + a.subList(0, Math.min(3, a.size())) + " failed");
    }

    public int[] toArray(List<Integer> list) {
        int[] arr = new int[list.size()];
        int i = 0;
        for (Integer j : list) {
            arr[i++] = j;
        }
        return arr;
    }

    public void test100KData() {
        Scanner scanner = new Scanner(getClass()
                .getClassLoader().getResourceAsStream("integerArray100K.txt"));
        int[] arr = new int[100000];
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = scanner.nextInt();
        }

        System.out.println("Found inversion count in 100K file: " + FindInversions.find(arr));
    }
}
