package sorting;

import org.testng.Assert;
import org.testng.annotations.Test;
import sorting.HandOfStraights;

@Test
public class HandOfStraightsTest {
    public void test() {
        Assert.assertTrue(HandOfStraights.isNStraightHand(new int[] {1,2,3},  3));
        Assert.assertTrue(HandOfStraights.isNStraightHand(new int[] {1,2,3,1,2,3},  3));
        Assert.assertTrue(HandOfStraights.isNStraightHand(new int[] {1,2,3,2,3,4,3,4,5},  3));

        Assert.assertFalse(HandOfStraights.isNStraightHand(new int[] {1,2,3,1,2},  3));
        Assert.assertFalse(HandOfStraights.isNStraightHand(new int[] {1,2,3,1,2,5},  3));
    }
}
