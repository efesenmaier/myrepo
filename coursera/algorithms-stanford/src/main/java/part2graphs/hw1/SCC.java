package part2graphs.hw1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * This class implements the Kosaraju two pass Strongly Connected Components algorithm
 * based on DFS.
 */
public class SCC {
    private DirectedGraph g;

    // Finishing time for 1st pass of algorithm
    private int t = 0;

    // Starting node
    private Vertex s = null;

    private Map<Integer, Set<Integer>> components = new HashMap<>();

    private Set<Integer> explored = new HashSet<>();

    private boolean secondPass = false;

    public SCC(DirectedGraph g) {
        this.g = g;
    }

    public void run() {
        // Reverse G
        g.reverse();

        // Compute finishing time of DFS
        for (Vertex i : g.getVertices().values()) {
            if (!explored.contains(i.getId())) {
                DFS(i);
            }
        }

        // Reverse back
        g.reverse();
        explored.clear();
        secondPass = true;

        Vertex[] verticesSortedByFinishingTime = new Vertex[g.getVertices().size()];
        for (Vertex v : g.getVertices().values()) {
            int i = verticesSortedByFinishingTime.length - v.getFinishingTime() - 1;
            assert i >= 0;
            assert i < verticesSortedByFinishingTime.length;
            assert verticesSortedByFinishingTime[i] == null;
            verticesSortedByFinishingTime[i] = v;
        }

        // Release original Graph - verticesSortedByFinishingTime is the new graph
        g = null;

        for (int i = 0; i < verticesSortedByFinishingTime.length; ++i) {
            Vertex v = verticesSortedByFinishingTime[i];
            if (!explored.contains(v.getId())) {
                s = v;
                DFS(v);
            }
        }
    }

    private void DFS(Vertex i) {
        explored.add(i.getId());

        if (secondPass) {
            if (!components.containsKey(s.getFinishingTime())) {
                components.put(s.getFinishingTime(), new HashSet<>());
            }
            components.get(s.getFinishingTime()).add(i.getId());
        }

        for (Vertex j : i.getNeighbors()) {
            if (!explored.contains(j.getId())) {
                DFS(j);
            }
        }

        if (!secondPass) {
            i.setFinishingTime(t++);
        }
    }

    public Map<Integer, Set<Integer>> components() {
        return components;
    }
}
