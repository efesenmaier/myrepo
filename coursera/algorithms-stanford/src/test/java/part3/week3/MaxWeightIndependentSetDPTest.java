package part3.week3;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Scanner;

@Test
public class MaxWeightIndependentSetDPTest {

    public void test() {
        long[] weights = {1, 4, 5, 4};
        MaxWeightIndependentSetDP dp = new MaxWeightIndependentSetDP(weights);

        dp.run();
        System.out.println(Arrays.toString(dp.a));
        System.out.println(Arrays.toString(dp.weightIncluded));
    }

    public void testAssignmentData() {
        long[] weights;
        try (Scanner scanner = new Scanner(getClass().getClassLoader().getResourceAsStream("mwis.txt"))) {
            // Ignore # of symbols
            int numWeights = scanner.nextInt();
            weights = new long[numWeights];

            for (int i = 0; i < numWeights; ++i) {
                weights[i] = scanner.nextLong();
            }
        }
        MaxWeightIndependentSetDP dp = new MaxWeightIndependentSetDP(weights);
        dp.run();

        int[] indices = {1, 2, 3, 4, 17, 117, 517, 997};
        String output = "";
        for (int i : indices) {
            if (dp.weightIncluded[i-1]) {
                output += "1";
            } else {
                output += "0";
            }
        }

        System.out.println(output);
        Assert.assertEquals(output, "10100110");
    }
}
