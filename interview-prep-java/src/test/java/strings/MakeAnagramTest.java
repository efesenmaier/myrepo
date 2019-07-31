package strings;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MakeAnagramTest {
    @DataProvider
    private Object[][] testData() {
        return new Object[][] {
                // Basic
                {"cde", "dcf", 2},
        };
    }
    @Test(dataProvider = "testData")
    public void basicTest(String a, String b, Integer expectedResult) {
        int actualResult = MakeAnagram.makeAnagram(a, b);
        Assert.assertEquals(actualResult, expectedResult.intValue());
    }

}
