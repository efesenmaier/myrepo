package hashing;

import hashing.CountTriplets;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Test
public class CountTripletsTest {

    @DataProvider
    private Object[][] testData() throws Exception {
        return new Object[][] {
                // Basic
                {2L, 3L, "1 2 1 2 4"},
                {1L, 161700L, "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1"},
                {10L, 1339347780085L, readFileToString("/CountTripletsTestCase10.txt")},
        };
    }

    @Test(dataProvider = "testData")
    public void test(Long r, Long expected, String input) {
        String[] ints = input.split("\\s");
        List<Long> longs = toIntArray(ints);
        long result = CountTriplets.countTriplets(longs, r);
        Assert.assertEquals(result, expected.longValue());
    }

    private List<Long> toIntArray(String[] arr) {
        return Arrays.stream(arr)
                .map(s -> Long.parseLong(s))
                .collect(Collectors.toList());
    }

    private String readFileToString(String filename) throws Exception {
        String path = getClass().getResource(filename).getPath();
        return new String(Files.readAllBytes(Paths.get(path)));
    }

}
