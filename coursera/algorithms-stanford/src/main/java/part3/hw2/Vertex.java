package part3.hw2;

import java.util.HashSet;
import java.util.Set;

public class Vertex {
    // Vertex name/label/id
    public String name;
    public Set<Edge> edges = new HashSet<>(); // End vertex with weight

    public Vertex(String name) {
        this.name = name;
    }

    public void addEdge(Edge edge) {
        if (!edge.u.equals(name) && !edge.v.equals(name)) {
            assert false;
        }
        assert edge.u.equals(name) || edge.v.equals(name);
        edges.add(edge);
    }
}
