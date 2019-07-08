package part2.hw2;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DijkstrasShortestPath {
    // Input
    private DirectedGraph graph;

    // Initial conditions
    private String s;
    private int n;

    // Working variables
    private Map<String, Integer> dist = new HashMap<>(); // Shortest path length - A
    private Set<String> visited = new HashSet<>(); // Visited set - X
    // Vertex W -> (GreedyScoreToW, Source Vertex U)
    private Map<String, Pair<Integer, String>> crossingVertexToGreedyScore = new HashMap<>();
    Map<String, String> shortestPathPrev = new HashMap<>();

    public DijkstrasShortestPath(DirectedGraph g, String s) {
        this.n = g.numVertices();
        this.s = s;
        this.graph = g;

        assert graph.getVertex(s) != null;
    }

    public void run() {
        visit(s, 0, s);

        while (visited.size() < n) {
            // Select min from crossing edges
            Map.Entry<String, Pair<Integer, String>> min = selectMin();
            String w = min.getKey();
            int greedyScoreToW = min.getValue().getKey();
            String u = min.getValue().getValue();


            // Visit the node (updating crossing edges)
            visit(w, greedyScoreToW, u);
        }
    }

    private Map.Entry<String, Pair<Integer, String>> selectMin() {
        Integer minScore = Integer.MAX_VALUE;
        Map.Entry<String, Pair<Integer, String>> min = null;

        assert !crossingVertexToGreedyScore.isEmpty();

        for (Map.Entry<String, Pair<Integer, String>> entry : crossingVertexToGreedyScore.entrySet()) {
            int greedyScore = entry.getValue().getKey();
            if (greedyScore < minScore) {
                min = entry;
                minScore = greedyScore;
            }
        }
        assert min != null;

        return min;
    }

    public List<Pair<String, Integer>> getShortestPath(int w) {
        return getShortestPath(Integer.toString(w));
    }

    public List<Pair<String, Integer>> getShortestPath(String w) {
        LinkedList<Pair<String, Integer>> path = new LinkedList<>();
        String i = w;
        while (!i.equals(s)) {
            String v = i;
            i = shortestPathPrev.get(i);
            int distance = graph.getVertex(i).getNeighbors().get(v);
            path.addFirst(new Pair<>(v, distance));
        }
        path.addFirst(new Pair<>(s, 0));
        return path;
    }

    private void visit(String w, int greedyScoreToW, String u) {
        assert !visited.contains(w);
        visited.add(w);

        dist.put(w, greedyScoreToW);
        shortestPathPrev.put(w, u);

        Vertex vertW = graph.getVertex(w);
        assert vertW != null;

        // Invariant: crossingVertexToGreedyScore maintains edges crossing from X to (V-X) with their weights
        for (Map.Entry<String, Integer> entry : vertW.getNeighbors().entrySet()) {
            String y = entry.getKey();
            // if W ->  is a new edge crossing X -> (V-X), where (V-X) are nodes not yet visited
            if (!visited.contains(y)) {
                assert dist.containsKey(w);
                int shortestDistThroughW = dist.get(w) + entry.getValue();
                if (crossingVertexToGreedyScore.containsKey(y)) {
                    if (crossingVertexToGreedyScore.get(y).getKey() > shortestDistThroughW) {
                        crossingVertexToGreedyScore.put(y, new Pair<>(shortestDistThroughW, w));
                    }
                } else {
                    crossingVertexToGreedyScore.put(y, new Pair<>(shortestDistThroughW, w));
                }
            }
        }

        // Remove any edges ending in w now
        crossingVertexToGreedyScore.remove(w);
    }

    public int getDistance(int w) {
        return getDistance(Integer.toString(w));
    }

    public int getDistance(String w) {
        return dist.getOrDefault(w, Integer.MAX_VALUE);
    }
}
