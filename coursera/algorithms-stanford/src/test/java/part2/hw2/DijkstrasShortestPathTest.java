package part2.hw2;

import javafx.util.Pair;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Test
public class DijkstrasShortestPathTest {

    @Test
    public void testLectureExample() {
        DirectedGraph graph = new DirectedGraph();
        graph.addEdge("0", "1", 1);
        graph.addEdge("0", "2", 4);
        graph.addEdge("1", "2", 2);
        graph.addEdge("1", "3", 6);
        graph.addEdge("2", "3", 3);

        DijkstrasShortestPath dijkstrasShortestPath = new DijkstrasShortestPath(graph, "0");
        dijkstrasShortestPath.run();

        Assert.assertEquals(dijkstrasShortestPath.getDistance(0), 0);
        Assert.assertEquals(dijkstrasShortestPath.getDistance(1), 1);
        Assert.assertEquals(dijkstrasShortestPath.getDistance(2), 3);
        Assert.assertEquals(dijkstrasShortestPath.getDistance(3), 6);
        Assert.assertEquals(dijkstrasShortestPath.getShortestPath(3), Arrays.asList("0", "1", "2", "3"));
    }

    @Test
    public void testConnorsMap() {
        DirectedGraph graph = new DirectedGraph();
        graph.addEdges("Seattle", Arrays.asList(pair("Portland", 300), pair("Minneapolis", 2000), pair("Denver", 1400)));
        graph.addEdges("Portland", Arrays.asList(pair("Denver", 1000), pair("Los Angeles", 1500), pair("Minneapolis", 1600)));
        graph.addEdges("Los Angeles", Arrays.asList(pair("Denver", 900), pair("Houston", 1400)));
        graph.addEdges("Denver", Arrays.asList(pair("Minneapolis", 800), pair("Chicago", 750), pair("St. Louis", 600), pair("New Orleans", 900)));
        graph.addEdges("Houston", Arrays.asList(pair("St. Louis", 800), pair("New Orleans", 150)));
        graph.addEdges("New Orleans", Arrays.asList(pair("Atlanta", 180), pair("Washington DC", 560)));
        graph.addEdges("Minneapolis", Arrays.asList(pair("Chicago", 500), pair("St. Louis", 800)));
        graph.addEdges("Chicago", Arrays.asList(pair("St. Louis", 400), pair("New York", 800)));
        graph.addEdges("St. Louis", Arrays.asList(pair("New Orleans", 600), pair("Washington DC", 800)));
        graph.addEdges("New York", Arrays.asList(pair("Washington DC", 370)));
        graph.addEdges("Atlanta", Arrays.asList(pair("Washington DC", 400)));

        DijkstrasShortestPath dijkstrasShortestPath = new DijkstrasShortestPath(graph, "Seattle");
        dijkstrasShortestPath.run();

        String destination = "Washington DC";
        System.out.println("Shortest path from Seattle to " + destination + " is: " + dijkstrasShortestPath.getShortestPath(destination) + " with total distance " + dijkstrasShortestPath.getDistance(destination));
        //Assert.assertEquals(dijkstrasShortestPath.getShortestPath(3), Arrays.asList("0", "1", "2", "3"));
    }

    private Pair<String, Integer> pair(String u, Integer weight) {
        return new Pair<>(u, weight);
    }

    public void testAssignment() {
        DirectedGraph graph = loadGraph(200, "dijkstraData.txt");
        DijkstrasShortestPath dijkstrasShortestPath = new DijkstrasShortestPath(graph, "1");
        dijkstrasShortestPath.run();

        // 7,37,59,82,99,115,133,165,188,197.
        String shortestDists = dijkstrasShortestPath.getDistance(7) + "," +
                dijkstrasShortestPath.getDistance(37) + "," +
                dijkstrasShortestPath.getDistance(59) + "," +
                dijkstrasShortestPath.getDistance(82) + "," +
                dijkstrasShortestPath.getDistance(99) + "," +
                dijkstrasShortestPath.getDistance(115) + "," +
                dijkstrasShortestPath.getDistance(133) + "," +
                dijkstrasShortestPath.getDistance(165) + "," +
                dijkstrasShortestPath.getDistance(188) + "," +
                dijkstrasShortestPath.getDistance(197);

        System.out.println("Shortest paths: " + shortestDists);
        Assert.assertEquals(shortestDists, "2599,2610,2947,2052,2367,2399,2029,2442,2505,3068");
    }

    private DirectedGraph loadGraph(int n, String filename) {
        DirectedGraph graph = new DirectedGraph();

        Pattern pattern = Pattern.compile("\\s*(\\d+)\\,(\\d+)\\s*");
        try (Scanner scanner = new Scanner(getClass().getClassLoader().getResourceAsStream(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                try(Scanner lineScanner = new Scanner(line)) {
                    String u = lineScanner.next();

                    Matcher m = pattern.matcher(lineScanner.nextLine());
                    while (m.find()) {
                        String v = m.group(1);
                        int weight = Integer.parseInt(m.group(2));
                        graph.addEdge(u, v, weight);
                    }
                }
            }
        }
        return graph;
    }
}
