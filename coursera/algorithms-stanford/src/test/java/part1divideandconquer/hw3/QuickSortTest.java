package part1divideandconquer.hw3;

import com.google.common.collect.Lists;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

@Test
public class QuickSortTest {
    @DataProvider
    private Object[][] testData() {
        return new Object[][] {
                {Lists.newArrayList()},
                {Lists.newArrayList(1)},
                {Lists.newArrayList(1, 2)},
                {Lists.newArrayList(1, 2, 3)},
                {Lists.newArrayList(3, 1, 2)},
                {Lists.newArrayList(2, 3, 1)},
                {Lists.newArrayList(5, 4, 3, 2, 1)},
                {Lists.newArrayList(1, 3, 5, 2, 4, 6)},
                {Lists.newArrayList(3, 1, 6, 2, 7, 9, 0)},
                {Lists.newArrayList(59, 40, 6, 55, 46, 30, 75, 22, 37, 12, 90, 23, 58, 50, 88, 60, 97, 6, 14, 63, 51, 58, 63, 66, 2, 37, 35, 0, 0, 37, 0, 36, 39, 71, 35, 16, 6, 98, 96, 83, 39, 51, 66, 59, 57, 87, 22, 13, 24, 43, 14, 63, 95, 60, 64, 88, 50, 87, 1, 5, 38, 96)},
        };
    }

    @Test(dataProvider = "testData")
    public void basicTest(List<Integer> a) {
        int[] expected = toArray(a);
        Arrays.sort(expected);

        for (QuickSort.PivotStrategy s : QuickSort.PivotStrategy.values()) {
            int[] arr = toArray(a);
            int comparisons = QuickSort.sortAndCountComparisons(arr, s);
            Assert.assertEquals(toList(arr), toList(expected), "Failed comparison using strategy " + s + " on list: " + a);
            System.out.println("Comparisons using strategy " + s + ": " + comparisons);
        }
    }

    @Test
    public void randomTestData() {
        Random random = new Random();
        int maxListSize = 100;
        int maxValue = 100;
        for (int i = 0; i < 100; ++i) {
            List<Integer> randomList = new ArrayList<>();
            int size = random.nextInt(maxListSize-1) + 1;
            for (int j = 0; j < size; ++j) {
                randomList.add(random.nextInt(maxValue));
            }
            basicTest(randomList);
        }
    }

    @DataProvider
    private Object[][] testMedianData() {
        return new Object[][] {
                {Lists.newArrayList(1, 2), 0},
                {Lists.newArrayList(1, 2, 3), 1},
                {Lists.newArrayList(3, 1, 2), 2},
                {Lists.newArrayList(2, 3, 1), 0},
                {Lists.newArrayList(5, 4, 3, 2, 1), 2},
                {Lists.newArrayList(1, 3, 5, 2, 4, 6), 2},
                {Lists.newArrayList(3, 1, 6, 2, 7, 9, 0), 3},
        };
    }

    @Test(dataProvider = "testMedianData")
    public void testMedianOfThree(List<Integer> a, int expected) {
        int[] arr = toArray(a);
        int medianIndex = QuickSort.chooseMedianPivot(arr, 0, arr.length-1);
        Assert.assertEquals(medianIndex, expected, "Chose wrong median for list: " + a);
    }

    public void testQuickSortDataFile() {
        List<Integer> list = loadFromFile("quickSortData10K.txt");
        basicTest(list);
    }

    private int[] toArray(List<Integer> list) {
        int[] arr = new int[list.size()];
        int i = 0;
        for (Integer j : list) {
            arr[i++] = j;
        }
        return arr;
    }

    private List<Integer> toList(int[] array) {
        return Arrays.stream(array).boxed()
                .collect(Collectors.toList());
    }

    private List<Integer> loadFromFile(String filename) {
        Scanner scanner = new Scanner(getClass()
                .getClassLoader().getResourceAsStream(filename));
        List<Integer> list = new ArrayList<>();
        while(scanner.hasNextInt()) {
            list.add(scanner.nextInt());
        }
        return list;
    }
}
