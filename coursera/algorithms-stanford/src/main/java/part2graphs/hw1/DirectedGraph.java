package part2graphs.hw1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class DirectedGraph {
    private HashSet<DirectedEdge> edges = new HashSet<>();

    /**
     * Potentially sparse map from node ID -> Vertex.
     */
    private HashMap<Integer, Vertex> vertices = new HashMap<>();

    public DirectedGraph() { }

    public void addVertex(Integer id) {
        if (!vertices.containsKey(id)) {
            vertices.put(id, new Vertex(id));
        }
    }

    public void addEdge(Integer uId, Integer vId) {
        addVertex(uId);
        addVertex(vId);
        DirectedEdge edge = new DirectedEdge(uId, vId);
        if (!edges.contains(edge)) {
            edges.add(edge);
            addEdgeToVertices(edge);
        }
    }

    public void addReversedEdge(Integer u, Integer v) {
        addEdge(v, u);
    }

    public void reverse() {
        vertices.values().stream()
                .forEach(v -> v.getNeighbors().clear());

        edges.stream()
                .forEach(e -> {
                    e.reverse();
                    addEdgeToVertices(e);
                });
    }

    private void addEdgeToVertices(DirectedEdge e) {
        Vertex u = getVertex(e.getU());
        Vertex v = getVertex(e.getV());
        u.getNeighbors().add(v);
    }

    public  Map<Integer, Vertex> getVertices() {
        return vertices;
    }

    public HashSet<DirectedEdge> getEdges() {
        return edges;
    }

    public Vertex getVertex(int i) {
        return vertices.get(i);
    }
}
