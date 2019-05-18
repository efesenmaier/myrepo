package arrays;

import arrays.LongestMountain;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class LongestMountainTest {
    public void test() {
        Assert.assertEquals(LongestMountain.longestMountain(new int[] {1,2,1}), 3);
        Assert.assertEquals(LongestMountain.longestMountain(new int[] {1,2,1,1}), 3);
        Assert.assertEquals(LongestMountain.longestMountain(new int[] {1,1,2,1}), 3);
        Assert.assertEquals(LongestMountain.longestMountain(new int[] {1,2,2,1}), 0);
        Assert.assertEquals(LongestMountain.longestMountain(new int[] {1,1,1}), 0);
        Assert.assertEquals(LongestMountain.longestMountain(new int[] {2,1,4,7,3,2,5}), 5);
        Assert.assertEquals(LongestMountain.longestMountain(new int[] {2,1,4,7,3,2,1}), 6);
        //[875,884,239,731,723,685]
        Assert.assertEquals(LongestMountain.longestMountain(new int[] {875,884,239,731,723,685}), 4);
        //[0,1,0,0,1,0,0,1,1,0,0,0,1,1,0,1,0,1,0,1,0,0,0,1,0,0,1,1,0,1,1,1,1,1,0,0,1,0,1,1,0,0,0,0,0,0,0,0,1,1,0,0,1,1,1,1,0,0,0,1,0,0,1,1,0,0,0,1,0,0,1,1,0,0,0,0,1,0,0,1,1,1,1,1,1,1,0,1,1,0,1,1,1,0,0,0,1,0,1,1]
        Assert.assertEquals(LongestMountain.longestMountain(new int[] {0,1,0,0,1,0,0,1,1,0,0,0,1,1,0,1,0,1,0,1,0,0,0,1,0,0,1,1,0,1,1,1,1,1,0,0,1,0,1,1,0,0,0,0,0,0,0,0,1,1,0,0,1,1,1,1,0,0,0,1,0,0,1,1,0,0,0,1,0,0,1,1,0,0,0,0,1,0,0,1,1,1,1,1,1,1,0,1,1,0,1,1,1,0,0,0,1,0,1,1}), 3);
    }
}
