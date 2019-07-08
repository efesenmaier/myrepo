package part2.hw2;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectedGraph {
    private Map<String, Vertex> vertices = new HashMap<>();

    public void addVertexIfDoesntExist(String name) {
        if (!vertices.containsKey(name)) {
            vertices.put(name, new Vertex(name));
        }
    }

    public void addEdges(String u, List<Pair<String, Integer>> edges) {
        for (Pair<String, Integer> edge : edges) {
            addEdge(u, edge.getKey(), edge.getValue());
        }
    }

    public void addEdge(String u, String v, int weight) {
        addVertexIfDoesntExist(u);
        addVertexIfDoesntExist(v);
        Vertex uVert = getVertex(u);
        uVert.addEdgeIfDoesntExist(v, weight);
    }

    public Vertex getVertex(String name) {
        assert vertices.containsKey(name);
        return vertices.get(name);
    }

    public int numVertices() {
        return vertices.size();
    }
}
