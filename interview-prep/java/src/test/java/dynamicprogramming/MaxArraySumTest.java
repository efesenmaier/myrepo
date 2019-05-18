package dynamicprogramming;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Test
public class MaxArraySumTest {
    @DataProvider
    private Object[][] testData() throws Exception {
        return new Object[][] {
                // Sample test cases
                {Arrays.asList(-2, 1, 3, -4, 5), 8},
                {Arrays.asList(3, 7, 4, 6, 5), 13},
                {Arrays.asList(2, 1, 5, 8, 4), 11},
                {Arrays.asList(3, 5, -7, 8, 10), 15},
                {toList("-10 8 3 -4 2 7 -15 6 1 1 2 -1 -1"), 23},
                {toList(readFileToString("/dynamicprogramming/maxarraysum0.txt")), 151598486}
        };
    }

    @Test(dataProvider = "testData")
    public void test(List<Integer> list, Integer expected) {
        int[] arr = toArray(list);
        Integer max = MaxArraySum.maxSubsetSum(arr);
        Assert.assertEquals(max, expected);
    }

    private String readFileToString(String filename) throws Exception {
        String path = getClass().getResource(filename).getPath();
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    private List<Integer> toList(String s) {
        Scanner scanner = new Scanner(s);
        List<Integer> arr = new ArrayList<>();
        while (scanner.hasNextInt()) {
            arr.add(scanner.nextInt());
        }
        return arr;
    }

    private int[] toArray(List<Integer> list) {
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            arr[i] = list.get(i);
        }
        return arr;
    }
}
