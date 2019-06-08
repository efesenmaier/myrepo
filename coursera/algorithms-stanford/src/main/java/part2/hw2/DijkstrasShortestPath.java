package part2.hw2;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DijkstrasShortestPath {
    // Initial conditions
    private int s;
    private int n;
    private Vertex[] vertices;

    private int[] dist; // Shortest path length - A
    private Set<Integer> visited = new HashSet<>(); // Visited set - X
    // Vertex W -> (GreedyScoreToW, Source Vertex U)
    private Map<Integer, Pair<Integer, Integer>> crossingVertexToGreedyScore = new HashMap<>();
    Map<Integer, Integer> shortestPathPrev = new HashMap<>();

    public DijkstrasShortestPath(Vertex[] vertices, int s) {
        this.vertices = vertices;
        this.n = vertices.length;
        this.s = s;
        this.dist = new int[n];

        assert s >= 0;
        assert s < n;
    }

    public void run() {
        visit(s, 0, s);

        while (visited.size() < n) {
            // Select min from crossing edges
            Map.Entry<Integer, Pair<Integer, Integer>> min = selectMin();
            int w = min.getKey();
            int greedyScoreToW = min.getValue().getKey();
            int u = min.getValue().getValue();


            // Visit the node (updating crossing edges)
            visit(w, greedyScoreToW, u);
        }
    }

    private Map.Entry<Integer, Pair<Integer, Integer>> selectMin() {
        Integer minScore = Integer.MAX_VALUE;
        Map.Entry<Integer, Pair<Integer, Integer>> min = null;

        assert !crossingVertexToGreedyScore.isEmpty();

        for (Map.Entry<Integer, Pair<Integer, Integer>> entry : crossingVertexToGreedyScore.entrySet()) {
            int greedyScore = entry.getValue().getKey();
            if (greedyScore < minScore) {
                min = entry;
                minScore = greedyScore;
            }
        }
        assert min != null;

        return min;
    }

    public List<Integer> getShortestPath(Integer w) {
        LinkedList<Integer> path = new LinkedList<>();
        int i = w;
        while (i != s) {
            path.addFirst(i);
            i = shortestPathPrev.get(i);
        }
        path.addFirst(s);
        return path;
    }

    private void visit(int w, int greedyScoreToW, int u) {
        assert w >= 0;
        assert w < n;

        assert !visited.contains(w);
        visited.add(w);

        dist[w] = greedyScoreToW;
        shortestPathPrev.put(w, u);

        Vertex vertW = vertices[w];

        // Invariant: crossingVertexToGreedyScore maintains edges crossing from X to (V-X) with their weights
        for (Map.Entry<Integer, Integer> entry : vertW.getNeighbors().entrySet()) {
            int y = entry.getKey();
            // if W ->  is a new edge crossing X -> (V-X), where (V-X) are nodes not yet visited
            if (!visited.contains(y)) {
                int shortestDistThroughW = dist[w] + entry.getValue();
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

    public int[] getDistances() {
        return dist;
    }
}
