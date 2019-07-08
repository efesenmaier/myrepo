package strings;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

public class SpecialPalindromicSubstringsTest {

    @DataProvider
    private Object[][] testData() throws Exception{
        return new Object[][] {
                // Basic
                {"asasd", 7},
                {"abcbaba", 10},
                {"aaaa", 10},
                {readFileToString("/strings/specialPalindromicSubstringsInput03.txt"), 1583085}
        };
    }
    @Test(dataProvider = "testData")
    public void basicTest(String str, Integer expectedResult) {
        long count = SpecialPalindromicSubstrings.substrCount(0, str);
        Assert.assertEquals(count, expectedResult.intValue());
    }

    private String readFileToString(String filename) throws Exception {
        String path = getClass().getResource(filename).getPath();
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}
