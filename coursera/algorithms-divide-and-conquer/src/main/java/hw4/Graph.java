package hw4;

import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Graph {
    private ArrayList<Edge> edges = new ArrayList<>();
    private Map<Integer, Vertex> vertices = new HashMap<>();

    public Graph() { }

    public void addVertex(Integer id) {
        if (!vertices.containsKey(id)) {
            vertices.put(id, new Vertex(id));
        }
    }

    public void addEdge(Integer uId, Integer vId) {
        addVertex(uId);
        addVertex(vId);
        Edge edge = new Edge(uId, vId);
        if (!edges.contains(edge)) {
            edges.add(edge);
        }
    }

    public  Map<Integer, Vertex> getVertices() {
        return vertices;
    }

    public Vertex getVertex(Integer id) {
        return vertices.get(id);
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void runRandomContraction() {
        Random rand = new Random();
        while (vertices.size() > 2) {
            int randomEdge = rand.nextInt(edges.size());
            Edge e = edges.get(randomEdge);
            merge(e);
        }
    }

    public void merge(Integer id1, Integer id2) {
        Edge edge = new Edge(id1, id2);
        merge(edge);
    }

    public void merge(Edge edge) {
        Assert.assertTrue(edges.contains(edge));

        Assert.assertTrue(vertices.containsKey(edge.getU()));
        Assert.assertTrue(vertices.containsKey(edge.getV()));
        Vertex u = vertices.get(edge.getU());
        Vertex v = vertices.get(edge.getV());
        edges = edges.stream()
                // Remove all edges between the 2 nodes
                .filter(e -> !e.equals(edge))
                // Update all edges to replace V with U
                .map(e -> e.replace(v.getId(), u.getId()))
                // Filter out any loops
                .filter(e -> !e.isLoop())
                .collect(Collectors.toCollection(ArrayList::new));

        u.getMerged().addAll(v.getMerged());
        u.getMerged().add(v.getId());

        vertices.remove(v.getId());
        edges.remove(edge);
    }
}
