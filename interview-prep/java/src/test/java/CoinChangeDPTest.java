import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class CoinChangeDPTest {
    public void test() {
        Assert.assertEquals(CoinChangeDP.coinChange(new int[] {1,2,3}, 6), 2);

        Assert.assertEquals(CoinChangeDP.coinChange(new int[] {2}, 3), -1);
    }
}
