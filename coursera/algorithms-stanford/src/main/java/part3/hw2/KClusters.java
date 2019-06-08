package part3.hw2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

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
    Set<String> vertices = new HashSet<>();
    private HashSet<Edge> edges;

    // Algorithm variables
    long minSpacingBetweenClusters = Long.MAX_VALUE;

    /**
     *
     * @param n num vertices
     */
    public KClusters(int n) {
        // There can be at most n choose 2 vertices, or n * (n-1)/2
        // So just initialize to this capacity to avoid re-allocation and copying
        edges = new HashSet<>((n*(n-1))/2);
    }

    public void addEdge(String u, String v, Long distance) {
        if (!vertices.contains(u)) {
            vertices.add(u);
        }

        if (!vertices.contains(v)) {
            vertices.add(v);
        }

        Edge edge = new Edge(u, v, distance);
        boolean added = edges.add(edge);
        assert added;
    }

    public long find(long k) {
        Edge[] sortedEdges = edges.toArray(new Edge[edges.size()]);
        Arrays.sort(sortedEdges, Comparator.comparing(e -> e.weight));

        UnionFind unionFind = new UnionFind();

        // Initialize the connected components (clusters)
        for (String vertex : vertices) {
            unionFind.add(vertex);
        }

        for (int i = 0; i < sortedEdges.length; ++i) {
            Edge edge = sortedEdges[i];
            if (!unionFind.hasSameLeader(edge.u, edge.v)) {
                unionFind.union(edge.u, edge.v);
                if (unionFind.numConnectedComponents == k) {
                    break;
                }
            }
        }

        for (int i = 0; i < sortedEdges.length; ++i) {
            Edge edge = sortedEdges[i];
            if (!unionFind.hasSameLeader(edge.u, edge.v) && edge.weight < minSpacingBetweenClusters) {
                minSpacingBetweenClusters = edge.weight;
            }
        }

        return minSpacingBetweenClusters;
    }
}
