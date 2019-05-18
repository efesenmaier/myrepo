import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LongestPalindromeSubstringTest {

    @DataProvider
    private Object[][] testData() {
        return new Object[][] {
                // Basic
                {"aabad", "aba"},
                {"cbbd", "bb"},
                {"acadac", "cadac"},
                {"acaacac", "acaaca"},
                {"acaacac", "acaaca"},
                {"I refer you to a palindrome", " refer "},
        };
    }
    @Test(dataProvider = "testData")
    public void basicTest(String str, String expectedResult) {
        String palindrome = LongestPalindromeSubstring.find(str);
        System.out.println(palindrome);
        Assert.assertEquals(palindrome, expectedResult);
    }
}
