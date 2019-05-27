package dynamicprogramming;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LengthOfLongestCommonSubsequenceTest {
    @DataProvider
    private Object[][] testData() throws Exception {
        return new Object[][] {
                {toIntArray("HARRY"), toIntArray("SALLY"), 2},
                {toIntArray("AA"), toIntArray("BB"), 0},
                {toIntArray("SHINCHAN"), toIntArray("NOHARAAA"), 3},
                {toIntArray("ABCDEF"), toIntArray("FBDAMN"), 2},
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] x, int[] y, Integer expected) {
        Integer length = LengthOfLongestCommonSubsequence.find(x, y);
        Assert.assertEquals(length, expected);
    }

    private int[] toIntArray(String s) {
        return s.chars().toArray();
    }
}
