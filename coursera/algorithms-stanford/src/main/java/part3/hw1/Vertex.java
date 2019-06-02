package part3.hw1;

import java.util.HashSet;
import java.util.Set;

public class Vertex {
    public int id;
    public Set<Edge> edges = new HashSet<>(); // End vertex with weight

    public Vertex(int id) {
        this.id = id;
    }

    public void addEdge(Edge edge) {
        if (edge.u != id && edge.v != id) {
            assert false;
        }
        assert edge.u == id || edge.v == id;
        edges.add(edge);
    }
}
