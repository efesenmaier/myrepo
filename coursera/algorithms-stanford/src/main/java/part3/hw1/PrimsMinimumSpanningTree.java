package part3.hw1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class PrimsMinimumSpanningTree {

    // Vertex ID to Vertex
    Map<Integer, Vertex> vertices = new HashMap<>();

    // Algorithm variables
    private HashSet<Integer> seen = new HashSet<>();
    private HashSet<Edge> crossingEdges = new HashSet<>();
    private HashSet<Edge> mst = new HashSet<>();

    public void addEdge(int u, int v, int weight) {
        if (!vertices.containsKey(u)) {
            vertices.put(u, new Vertex(u));
        }

        if (!vertices.containsKey(v)) {
            vertices.put(v, new Vertex(v));
        }
        Edge edge = new Edge(u, v, weight);

        // Add undirected edge
        vertices.get(u).addEdge(edge);
        vertices.get(v).addEdge(edge);
    }

    public long run() {
        assert !vertices.isEmpty();
        int s = vertices.keySet().iterator().next();

        addVertexToX(s);

        while (seen.size() < vertices.size()) {
            Edge edge = findMinCrossingEdge();

            int v = seen.contains(edge.u) ? edge.v : edge.u;
            mst.add(edge);

            addVertexToX(v);
        }

        long mstTotalWeight = 0;
        for (Edge edge : mst) {
            mstTotalWeight += edge.weight;
        }

        return mstTotalWeight;
    }

    Edge findMinCrossingEdge() {
        assert !crossingEdges.isEmpty();
        Iterator<Edge> i = crossingEdges.iterator();
        Edge minEdge = i.next();
        while (i.hasNext()) {
            Edge edge = i.next();
            if (edge.weight < minEdge.weight) {
                minEdge = edge;
            }
        }
        return minEdge;
    }

    void addVertexToX(int v) {
        seen.add(v);

        // Remove edges entirely in X
        for (Iterator<Edge> i = crossingEdges.iterator(); i.hasNext();) {
            Edge edge = i.next();
            if (seen.contains(edge.u) && seen.contains(edge.v)) {
                i.remove();
            }
        }

        // Add crossing edges
        for (Edge edge : vertices.get(v).edges) {
            if (!seen.contains(edge.getOtherVertex(v))) {
                crossingEdges.add(edge);
            }
        }
    }
}
