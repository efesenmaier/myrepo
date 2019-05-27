package part2.hw2;

import com.google.common.collect.ImmutableMap;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Test
public class DijkstrasShortestPathTest {

    @Test
    public void testLectureExample() {
        Vertex[] vertices = new Vertex[4];
        vertices[0] = new Vertex(new ImmutableMap.Builder().put(1, 1).put(2, 4).build());
        vertices[1] = new Vertex(new ImmutableMap.Builder().put(2, 2).put(3, 6).build());
        vertices[2] = new Vertex(new ImmutableMap.Builder().put(3, 3).build());
        vertices[3] = new Vertex(Collections.emptyMap());

        DijkstrasShortestPath dijkstrasShortestPath = new DijkstrasShortestPath(vertices, 0);
        dijkstrasShortestPath.run();

        int[] dists = dijkstrasShortestPath.getDistances();
        Assert.assertEquals(dists[0], 0);
        Assert.assertEquals(dists[1], 1);
        Assert.assertEquals(dists[2], 3);
        Assert.assertEquals(dists[3], 6);
    }

    public void testAssignment() {
        Vertex[] vertices = loadGraph(200, "dijkstraData.txt");
        DijkstrasShortestPath dijkstrasShortestPath = new DijkstrasShortestPath(vertices, 0);
        dijkstrasShortestPath.run();

        int[] dists = dijkstrasShortestPath.getDistances();
        // 7,37,59,82,99,115,133,165,188,197.
        System.out.println("Shortest paths: " +
                dists[6] + "," +
                dists[36] + "," +
                dists[58] + "," +
                dists[81] + "," +
                dists[98] + "," +
                dists[114] + "," +
                dists[132] + "," +
                dists[164] + "," +
                dists[187] + "," +
                dists[196]);
    }

    private Vertex[] loadGraph(int n, String filename) {
        Vertex[] vertices = new Vertex[n];
        for (int i = 0; i < vertices.length; ++i) {
            vertices[i] = new Vertex(Collections.emptyMap());
        }

        Map<Integer, Vertex> verticesMap = new HashMap<>();
        Pattern pattern = Pattern.compile("\\s*(\\d+)\\,(\\d+)\\s*");
        try (Scanner scanner = new Scanner(getClass().getClassLoader().getResourceAsStream(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                try(Scanner lineScanner = new Scanner(line)) {
                    int id = lineScanner.nextInt();

                    Map<Integer, Integer> neighbors = new HashMap<>();
                    Matcher m = pattern.matcher(lineScanner.nextLine());
                    while (m.find()) {
                        int w = Integer.parseInt(m.group(1))-1;
                        int weight = Integer.parseInt(m.group(2));
                        neighbors.put(w, weight);
                    }
                    Vertex vertex = new Vertex(neighbors);
                    verticesMap.put(id-1, vertex);
                }
            }
        }

        for (Map.Entry<Integer, Vertex> entry : verticesMap.entrySet()) {
            vertices[entry.getKey()] = entry.getValue();
        }
        return vertices;
    }
}
