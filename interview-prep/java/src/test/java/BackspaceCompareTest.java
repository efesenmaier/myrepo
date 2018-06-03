import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class BackspaceCompareTest {
    public void test() {
        String a = "y#fo##f";
        String b = "y#f#o##f";
        Assert.assertTrue(BackspaceCompare.backspaceCompare(a, b));
    }
}
