package sorting;

import org.testng.Assert;
import org.testng.annotations.Test;
import sorting.MedianOfSortedArrays;

@Test
public class MedianOfSortedArraysTest {
    public void test() {
        Assert.assertEquals(MedianOfSortedArrays.find(asArray(1), asArray(1)), 1.0);
        Assert.assertEquals(MedianOfSortedArrays.find(asArray(1), asArray()), 1.0);
        Assert.assertEquals(MedianOfSortedArrays.find(asArray(1, 2), asArray()), 1.5);
        Assert.assertEquals(MedianOfSortedArrays.find(asArray(1), asArray(2, 2)), 2.0);
        Assert.assertEquals(MedianOfSortedArrays.find(asArray(1, 1), asArray(2, 2)), 1.5);
        Assert.assertEquals(MedianOfSortedArrays.find(asArray(1, 2), asArray(1, 2)), 1.5);
        Assert.assertEquals(MedianOfSortedArrays.find(asArray(), asArray(2, 3)), 2.5);
    }

    private int[] asArray(int...vals) {
        return vals;
    }
}
