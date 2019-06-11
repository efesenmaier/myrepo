package graphs;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

@Test
public class TopologicalOrderingTest {
    @DataProvider
    private Object[][] testDataSimple() throws Exception {
        return new Object[][]{
                {Arrays.asList("A", "B", "C"), Arrays.asList(/* A -> */ Arrays.asList("B", "C"), /* B -> */ Arrays.asList(), /* C -> */ Arrays.asList("B")), "A", Arrays.asList("A", "C", "B")},
                {Arrays.asList("TELNET", "TCPIP", "NETCARD", "NETWORK", "DNS"), Arrays.asList(
                        /* TELNET -> */ Arrays.asList("TCPIP", "NETCARD"),
                        /* TCPIP -> */ Arrays.asList("NETCARD", "NETWORK"),
                        /* NETCARD -> */ Arrays.asList("NETWORK"),
                        /* NETWORK -> */ Arrays.asList(),
                        /* DNS -> */ Arrays.asList()), "TELNET", Arrays.asList("TELNET", "TCPIP", "NETCARD", "NETWORK")},
        };
    }

    @DataProvider
    private Object[][] testData() throws Exception {
        return new Object[][]{
                {Arrays.asList("A", "B", "C", "D", "E", "F", "G"), Arrays.asList(
                        /* A -> */ Arrays.asList("B", "C"),
                        /* B -> */ Arrays.asList("D", "E"),
                        /* C -> */ Arrays.asList("F", "G"),
                        /* D -> */ Arrays.asList(),
                        /* E -> */ Arrays.asList(),
                        /* F -> */ Arrays.asList(),
                        /* G -> */ Arrays.asList()), "A", Arrays.asList("A", "C", "G", "F", "B", "E", "D")},
        };
    }

    @Test(dataProvider = "testData")
    public void testTopologicalOrdering(List<String> vertices, List<List<String>> adjacencyLists, String root, List<String> expectedOrdering) {
        boolean reverse = false;
        Graph graph = new Graph();
        for (int i = 0; i < vertices.size(); ++i) {
            graph.addVertex(vertices.get(i), adjacencyLists.get(i));
        }
        TopologicalOrdering topologicalOrdering = new TopologicalOrdering(graph, root, reverse);
        List<String> ordering = topologicalOrdering.findRecursive();
        Assert.assertEquals(ordering, expectedOrdering);

        TopologicalOrdering topologicalOrdering2 = new TopologicalOrdering(graph, root, reverse);
        List<String> ordering2 = topologicalOrdering2.findIterative();
        Assert.assertEquals(ordering2, expectedOrdering);

    }
}
