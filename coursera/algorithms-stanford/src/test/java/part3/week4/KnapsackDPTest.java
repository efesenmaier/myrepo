package part3.week4;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Scanner;

@Test
public class KnapsackDPTest {

    public void testFromLecture() {
        int capacity = 6;
        long[] values = {3, 2, 4, 4};
        long[] weights = {4, 3, 2, 3};

        KnapsackDP knapsackDP = new KnapsackDP(values, weights, capacity);

        long maxValue = knapsackDP.find();
        System.out.println("Max value: " + maxValue);
        Assert.assertEquals(maxValue, 8);
    }

    public void testAssignmentData() {
        int capacity;
        long[] values;
        long[] weights;
        try (Scanner scanner = new Scanner(getClass().getClassLoader().getResourceAsStream("knapsack1.txt"))) {
            // Ignore # of symbols
            capacity = scanner.nextInt();
            int numItems = scanner.nextInt();
            values = new long[numItems];
            weights = new long[numItems];

            for (int i = 0; i < numItems; ++i) {
                values[i] = scanner.nextLong();
                weights[i] = scanner.nextLong();
            }
        }

        KnapsackRecursive knapsackDP = new KnapsackRecursive(values, weights, capacity);
        long maxValue = knapsackDP.find();
        System.out.println("Max value: " + maxValue);
        Assert.assertEquals(maxValue, 2493893L);
    }

    public void testAssignmentData2() {
        int capacity;
        long[] values;
        long[] weights;
        try (Scanner scanner = new Scanner(getClass().getClassLoader().getResourceAsStream("knapsack_big.txt"))) {
            // Ignore # of symbols
            capacity = scanner.nextInt();
            int numItems = scanner.nextInt();
            values = new long[numItems];
            weights = new long[numItems];

            for (int i = 0; i < numItems; ++i) {
                values[i] = scanner.nextLong();
                weights[i] = scanner.nextLong();
            }
        }

        KnapsackRecursive knapsack = new KnapsackRecursive(values, weights, capacity);
        long maxValue = knapsack.find();
        System.out.println("Max value: " + maxValue);
        Assert.assertEquals(maxValue, 4243395L);
    }


}
