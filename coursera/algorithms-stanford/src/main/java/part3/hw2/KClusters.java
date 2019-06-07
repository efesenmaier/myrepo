package part3.hw2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * This K clusters algorithm is simply Kruskal's Minimum Spanning Tree (MST) algorithm
 * aborted early, when the MST has K clusters left.
 * 1. Each point is in a separate cluster
 * 2. Repeat until only K clusters:
 *    2a) let p,q = closest pair of separated points
 *    2b) merge the clusters containing p,q into a single cluster
 *
 */
public class KClusters {
    // Algorithm variables
    Map<Integer, Vertex> vertices = new HashMap<>();
    private HashSet<Edge> edges;

    //
    private HashSet<Edge> mst = new HashSet<>();

    /**
     *
     * @param n num vertices
     */
    public KClusters(int n) {
        // There can be at most n choose 2 vertices, or n * (n-1)/2
        edges = new HashSet<>((n*(n-1))/2);
    }

    public void addEdge(int u, int v, int distance) {
        Edge edge = new Edge(u, v, distance);
        boolean replaced = edges.add(edge);
        assert !replaced;
    }

    public void find() {
        Edge[] sortedEdges = edges.toArray(new Edge[edges.size()]);
        Arrays.sort(sortedEdges, Comparator.comparing(e -> e.weight));

        UnionFind unionFind = new UnionFind();

        for (int i = 0; i < sortedEdges.length; ++i) {
            unionFind.add();
        }
    }
}
