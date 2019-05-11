package part1divideandconquer.hw4;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@Test
public class GraphMinCutTest {

    public void testHubAndSpokesMerge() {
        Graph graph = createHubAndSpokes(3);

        graph.merge(0, 1);
        Assert.assertNull(graph.getVertex(1));
        Assert.assertFalse(graph.getEdges().contains(new Edge(0, 1)));
        Assert.assertTrue(graph.getVertex(0).getMerged().contains(1));
        Assert.assertEquals(graph.getVertices().size(), 3);
        Assert.assertEquals(graph.getEdges().size(), 2);

        graph.merge(2, 0);
        Assert.assertNull(graph.getVertex(0));
        Assert.assertFalse(graph.getEdges().contains(new Edge(0, 2)));
        Assert.assertEquals(graph.getVertex(2).getMerged(), toSet(0, 1));
        Assert.assertEquals(graph.getVertices().size(), 2);
        Assert.assertEquals(graph.getEdges().size(), 1);
    }

    public void textLectureExampleMerges() {
        Graph graph = new Graph();
        for (int i = 0; i <= 3; ++i) {
            graph.addVertex(i);
        }
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);

        Assert.assertEquals(graph.getVertices().size(), 4);
        Assert.assertEquals(graph.getEdges().size(), 5);

        graph.merge(0, 2);
        Assert.assertNull(graph.getVertex(2));
        Assert.assertEquals(graph.getVertices().size(), 3);
        Assert.assertEquals(graph.getEdges().size(), 4);
        Assert.assertEquals(graph.getVertex(0).getMerged(), toSet(2));
        // Doubled up edge from 0 and 1
        Assert.assertEquals(countEdgesBetween(graph, 0, 1), 2);
        // 0 "inherits" edge between 2 and 3
        Assert.assertEquals(countEdgesBetween(graph, 0, 3), 1);

        graph.merge(0, 1);
        Assert.assertNull(graph.getVertex(1));
        Assert.assertEquals(graph.getVertices().size(), 2);
        Assert.assertEquals(graph.getEdges().size(), 2);
        Assert.assertEquals(graph.getVertex(0).getMerged(), toSet(2, 1));
        Assert.assertEquals(countEdgesBetween(graph, 0, 3), 2);
    }

    public void textLectureExample2Merges() {
        Graph graph = new Graph();
        for (int i = 0; i <= 3; ++i) {
            graph.addVertex(i);
        }
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);

        Assert.assertEquals(graph.getVertices().size(), 4);
        Assert.assertEquals(graph.getEdges().size(), 5);

        graph.merge(0, 2);
        Assert.assertNull(graph.getVertex(2));
        Assert.assertEquals(graph.getVertices().size(), 3);
        Assert.assertEquals(graph.getEdges().size(), 4);
        Assert.assertEquals(graph.getVertex(0).getMerged(), toSet(2));
        // Doubled up edge from 0 and 1
        Assert.assertEquals(countEdgesBetween(graph, 0, 1), 2);
        // 0 "inherits" edge between 2 and 3
        Assert.assertEquals(countEdgesBetween(graph, 0, 3), 1);

        graph.merge(1, 3);
        Assert.assertNull(graph.getVertex(3));
        Assert.assertEquals(graph.getVertices().size(), 2);
        Assert.assertEquals(graph.getEdges().size(), 3);
        Assert.assertEquals(graph.getVertex(1).getMerged(), toSet(3));
        Assert.assertEquals(countEdgesBetween(graph, 0, 1), 3);
    }

    public void textRunRandomContractionOnLectureExample() {
        Graph graph = new Graph();
        for (int i = 0; i <= 3; ++i) {
            graph.addVertex(i);
        }
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);

        graph.runRandomContraction();
        Assert.assertTrue(graph.getEdges().size() >= 2);
        Assert.assertTrue(graph.getEdges().size() <= 3);
    }

    public void findMinCut() {
        int minCutFound = Integer.MAX_VALUE;
        //Graph g = loadGraphFromFile();
        // Have to run n^2 times to have high probability of finding absolute min on any input
        // but just running 200 times on this input for speed
        int N = 200; //* g.getVertices().size();
        for (int i = 0; i < N; ++i) {
            Graph graph = loadGraphFromFile();
            graph.runRandomContraction();
            int cutFound = graph.getEdges().size();
            minCutFound = Math.min(cutFound, minCutFound);
            System.out.println("Min cut found on iteration " + i + ": " + cutFound + ", all-time min: " + minCutFound);
        }
    }

    private <T> Set<T> toSet(T... elements) {
        HashSet<T> set = new HashSet<>();
        set.addAll(Arrays.asList(elements));
        return set;
    }

    private long countEdgesBetween(Graph graph, Integer u, Integer v) {
        Edge edge = new Edge(u, v);
        return graph.getEdges().stream().filter(e -> e.equals(edge)).count();
    }

    private Graph createHubAndSpokes(int numSpokes) {
        Graph graph = new Graph();
        graph.addVertex(0);
        for (int i = 1; i <= numSpokes; ++i) {
            graph.addVertex(i);
            graph.addEdge(0, i);
        }
        Assert.assertEquals(graph.getVertices().size(), numSpokes + 1);
        Assert.assertEquals(graph.getEdges().size(), numSpokes);
        return graph;
    }

    private Graph loadGraphFromFile() {
        Scanner scanner = new Scanner(getClass()
                .getClassLoader().getResourceAsStream("minCutGraph.txt"));
        Graph graph = new Graph();
        while (scanner.hasNextLine()) {
            Scanner scanner2 = new Scanner(scanner.nextLine());
            if (scanner2.hasNextInt()) {
                int id = scanner2.nextInt();
                graph.addVertex(id);
                while (scanner2.hasNextInt()) {
                    int other = scanner2.nextInt();
                    // This will create vertices if needed
                    graph.addEdge(id, other);
                }
            }
        }

        return graph;
    }
}
