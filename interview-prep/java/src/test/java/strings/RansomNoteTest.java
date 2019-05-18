package strings;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import strings.RansomNote;

@Test
public class RansomNoteTest {
    @DataProvider
    private Object[][] testData() {
        return new Object[][] {
                // Basic
                {"give me one grand today night", "give one grand today", true},
                {"two times three is not four", "two times two is four", false},
                {"ive got a lovely bunch of coconuts", "ive got some coconuts", false},

        };
    }

    @Test(dataProvider = "testData")
    public void test(String magazine, String note, boolean expected) {
        String[] magazineWords = magazine.split(" ");
        String[] noteWords = note.split(" ");
        boolean result = RansomNote.containsWords(magazineWords, noteWords);
        Assert.assertEquals(result, expected);
    }
}
