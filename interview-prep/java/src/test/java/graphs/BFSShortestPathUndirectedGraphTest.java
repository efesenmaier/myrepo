package graphs;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Test
public class BFSShortestPathUndirectedGraphTest {

    @DataProvider
    private Object[][] testData() throws Exception {
        return new Object[][]{
                {"2\n4 2\n1 2\n1 3\n1\n3 1\n2 3\n2", "6 6 -1\n-1 6"},
                {"1\n6 4\n1 2\n2 3\n3 4\n1 5\n1", "6 12 18 6 -1"},
                {"1\n7 4\n1 2\n1 3\n3 4\n2 5\n2", "6 12 18 6 -1 -1"},
        };
    }

    @Test(dataProvider = "testData")
    public void test(String queries, String expected) {
        List<BFSShortestPathUndirectedGraph> graphs = BFSShortestPathUndirectedGraph.run(new ByteArrayInputStream(queries.getBytes()));
        List<List<Integer>> lists = createLists(expected);
        Assert.assertEquals(graphs.size(), lists.size());
        for (int i = 0; i < graphs.size(); ++i) {
            List<Integer> distances = graphs.get(i).getDistancesFinal();
            List<Integer> expectedDistances = lists.get(i);
            Assert.assertEquals(distances, expectedDistances);
        }
    }

    private List<List<Integer>> createLists(String distances) {
        List<List<Integer>> all = new ArrayList<>();
        Scanner scanner = new Scanner(distances);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            all.add(createIntList(line));
        }
        return all;
    }

    private List<Integer> createIntList(String distances) {
        List<Integer> list = new ArrayList<>();
        Scanner scanner = new Scanner(distances);
        while (scanner.hasNextInt()) {
            Integer dist = scanner.nextInt();

            list.add(dist);
        }
        return list;
    }
}
