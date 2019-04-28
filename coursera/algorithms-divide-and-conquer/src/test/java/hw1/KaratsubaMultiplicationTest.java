package hw1;

import com.google.common.collect.Lists;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Test
public class KaratsubaMultiplicationTest {
    List<Integer> ZERO = Lists.newArrayList(0);

    List<Integer> ONE = Lists.newArrayList(1);

    @DataProvider
    private Object[][] additionTestData() {
        return new Object[][] {
                // Basic
                {Lists.newArrayList(1, 2, 3), Lists.newArrayList(4, 5, 6), Lists.newArrayList(5, 7, 9)},
                // Test empty B
                {Lists.newArrayList(1, 2, 3), Lists.newArrayList(), Lists.newArrayList(1, 2, 3)},
                // Test carry
                {Lists.newArrayList(0, 9, 9), Lists.newArrayList(9, 9), Lists.newArrayList(1, 9, 8)},
                // Test larger B
                {Lists.newArrayList(1, 1), Lists.newArrayList(9, 9, 9), Lists.newArrayList(1, 0, 1, 0)},
                {Lists.newArrayList(0), Lists.newArrayList(9, 9, 9), Lists.newArrayList(9, 9, 9)},
        };
    }

    @DataProvider
    private Object[][] subtractionTestData() {
        return new Object[][] {
                // Basic
                {Lists.newArrayList(1, 2, 3), Lists.newArrayList(9, 9), Lists.newArrayList(2, 4)},
                // Test empty B
                {Lists.newArrayList(1, 2, 3), Lists.newArrayList(1, 2, 3), ZERO},
                // Test carry
                {Lists.newArrayList(0, 9, 9), Lists.newArrayList(9, 9), ZERO},
                // Test larger B
                {Lists.newArrayList(3, 2, 1), Lists.newArrayList(0, 9, 9), Lists.newArrayList(2, 2, 2)},
                // Test borrowing over long distance
                {Lists.newArrayList(1, 0, 0, 0), ONE, Lists.newArrayList(9, 9, 9)},
        };
    }

    @DataProvider
    private Object[][] multiplyTestData() {
        return new Object[][] {
                // Basic
                {Lists.newArrayList(1, 0), Lists.newArrayList(1, 0), Lists.newArrayList(1, 0, 0)},
                // Example
                {Lists.newArrayList(1, 2, 3, 4), Lists.newArrayList(5, 6, 7, 8), Lists.newArrayList(7, 0, 0, 6, 6, 5, 2)},
        };
    }

    @Test(dataProvider = "multiplyTestData")
    public void testMultiply(List<Integer> a, List<Integer> b, List<Integer> sum) {
        int[] result = KaratsubaMultiplication.multiply(toArray(a), toArray(b));
        Assert.assertEquals(toList(result), sum);
        Assert.assertEquals(toBigInteger(toArray(a)).multiply(toBigInteger(toArray(b))), toBigInteger(result));
    }

    @Test(dataProvider = "additionTestData")
    public void testAdd(List<Integer> a, List<Integer> b, List<Integer> sum) {
        int[] result = KaratsubaMultiplication.add(toArray(a), toArray(b));
        Assert.assertEquals(toList(result), sum);
        Assert.assertEquals(toBigInteger(toArray(a)).add(toBigInteger(toArray(b))), toBigInteger(result));
    }

    @Test(dataProvider = "subtractionTestData")
    public void testSubtract(List<Integer> a, List<Integer> b, List<Integer> expectedResult) {
        int[] result = KaratsubaMultiplication.subtract(toArray(a), toArray(b));
        Assert.assertEquals(toList(result), expectedResult);
        Assert.assertEquals(toBigInteger(toArray(a)).subtract(toBigInteger(toArray(b))), toBigInteger(result));
    }

    public void testMultiplyHomeworkAnswer() {
        String xStr = "3141592653589793238462643383279502884197169399375105820974944592";
        String yStr = "2718281828459045235360287471352662497757247093699959574966967627";
        int[] x = toArray("3141592653589793238462643383279502884197169399375105820974944592");
        int[] y = toArray("2718281828459045235360287471352662497757247093699959574966967627");
        int[] result = KaratsubaMultiplication.multiply(x, y);
        BigInteger expectedResult = new BigInteger(xStr).multiply(new BigInteger(yStr));
        BigInteger actualResult = toBigInteger(result);
        Assert.assertEquals(actualResult, expectedResult);
    }

    /**
     * Tests random integer multiplication of 2 big integers with a value represented by 1-128 bits.
     */
    public void testRandomIntegerMultiplication() {
        int numBits = 128;
        Random random = new Random();
        for (int i = 0; i < 1000; ++i) {
            BigInteger xBi = new BigInteger(Math.max(random.nextInt(numBits), 1), random);
            BigInteger yBi = new BigInteger(Math.max(random.nextInt(numBits), 1), random);

            int[] x = toArray(xBi.toString());
            int[] y = toArray(yBi.toString());

            int[] result = KaratsubaMultiplication.multiply(x, y);
            BigInteger expectedResult = xBi.multiply(yBi);
            BigInteger actualResult = toBigInteger(result);
            Assert.assertEquals(actualResult, expectedResult);
        }
    }

    public int[] toArray(List<Integer> list) {
        int[] arr = new int[list.size()];
        int i = 0;
        for (Integer j : list) {
            arr[i++] = j;
        }
        return arr;
    }

    public List<Integer> toList(int[] array) {
        return Arrays.stream(array).boxed()
                .collect(Collectors.toList());
    }

    public BigInteger toBigInteger(int[] arr) {
        String value = "";
        for (int i : arr) {
            value += i;
        }
        if (value.isEmpty()) {
            return BigInteger.ZERO;
        }
        else return new BigInteger(value);
    }

    int[] toArray(String bigValue) {
        List<Integer> list = new ArrayList<>();
        for (char c : bigValue.toCharArray()) {
            list.add(Integer.valueOf("" + c));
        }
        return toArray(list);
    }
}
