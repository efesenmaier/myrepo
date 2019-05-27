package graphs;

import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * See description at https://www.hackerrank.com/challenges/ctci-bfs-shortest-reach/problem
 */
public class BFSShortestPathUndirectedGraph {

    private static final int EDGE_LENGTH = 6;
    private int n;
    private int s;
    private Set<Integer>[] neighbors;

    private int[] distances;
    private List<Integer> distancesFinal = new ArrayList<>();

    /**
     * n = number of nodes, s = starting node
     * @param n
     */
    public BFSShortestPathUndirectedGraph(int n) {
        assert n > 1;

        this.n = n;
        this.neighbors = new HashSet[n];
        this.distances = new int[n];

        for (int i = 0; i < n; ++i) {
            // Initialize adjacency lists
            this.neighbors[i] = new HashSet<>();

            // Initialize distance to each node to -1
            this.distances[i] = -1;
        }
    }

    public void addEdge(int u, int v) {
        u = u-1;
        v = v-1;
        assert u >= 0;
        assert u < n;
        assert v >= 0;
        assert v < n;

        if (u == v) {
            // Ignore self loops
            return;
        }

        this.neighbors[u].add(v);
        this.neighbors[v].add(u);
    }

    public void find(int s) {
        s = s-1;
        this.s = s;
        assert s >= 0;
        assert s < n;

        Deque<Integer> q = new ArrayDeque<>();

        distances[s] = 0;
        q.addLast(s);

        while (!q.isEmpty()) {
            // Get the next node to visit
            Integer u = q.removeFirst();
            assert u >= 0;
            assert u < n;

            // Ensure its distance has already been calculated (its been visited)
            assert distances[u] != -1;
            int newDist = distances[u] + 1;

            for (Integer v : neighbors[u]) {
                if (distances[v] == -1) {
                    distances[v] = newDist;

                    q.addLast(v);
                }
            }
        }

        /**
         * For convenience, calculate final distances to nodes, excluding S, and
         * multiplying positive distances by EDGE_LENGTH.
         */
        for (int j = 0; j < n; ++j) {
            if (j != s) {
                int dist = distances[j];
                if (dist < 0) {
                    distancesFinal.add(dist);
                } else {
                    distancesFinal.add(dist * EDGE_LENGTH);
                }
            }
        }
    }

    private int[] getDistances() {
        return distances;
    }

    public List<Integer> getDistancesFinal() {
        return distancesFinal;
    }

    static List<BFSShortestPathUndirectedGraph> run(InputStream in) {
        List<BFSShortestPathUndirectedGraph> graphs = new ArrayList<>();
        Scanner scanner = new Scanner(in);
        int q = scanner.nextInt();
        for (int i = 0; i < q; ++i) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();

            BFSShortestPathUndirectedGraph bfs = new BFSShortestPathUndirectedGraph(n);

            for (int j = 0; j < m; ++j) {
                int u = scanner.nextInt();
                int v = scanner.nextInt();

                bfs.addEdge(u, v);
            }

            int s = scanner.nextInt();
            bfs.find(s);
            graphs.add(bfs);
        }
        return graphs;
    }

    private void printOutputs(List<BFSShortestPathUndirectedGraph> graphs) {
        for (BFSShortestPathUndirectedGraph bfs : graphs) {
            for (Integer dist : bfs.getDistancesFinal()) {
                System.out.print(dist + " ");
            }
        }
    }
}
