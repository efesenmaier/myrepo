package strings;

import org.testng.Assert;
import org.testng.annotations.Test;
import strings.MaxRepeatingSubstring;

@Test
public class MaxRepeatingSubstringTest {

    public void test() {
        //test("pwwkew", 3);
        //test("asjrgapa", 6);
        //test("abcdeaga", 6);
        test("abcabcbb", 3);
    }

    private void test(String s, int expected) {
        int max = MaxRepeatingSubstring.find(s);
        Assert.assertEquals(max, expected);
    }
}
