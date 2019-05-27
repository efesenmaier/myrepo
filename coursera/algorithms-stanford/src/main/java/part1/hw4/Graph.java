package part1.hw4;

import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Graph {
    private ArrayList<UndirectedEdge> edges = new ArrayList<>();
    private Map<Integer, Vertex> vertices = new HashMap<>();
    private Random rand = new Random();

    public Graph() { }

    public void addVertex(Integer id) {
        if (!vertices.containsKey(id)) {
            vertices.put(id, new Vertex(id));
        }
    }

    public void addEdge(Integer uId, Integer vId) {
        addVertex(uId);
        addVertex(vId);
        UndirectedEdge edge = new UndirectedEdge(uId, vId);
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

    public ArrayList<UndirectedEdge> getEdges() {
        return edges;
    }

    public void runRandomContraction() {
        while (vertices.size() > 2) {
            int randomEdge = rand.nextInt(edges.size());
            UndirectedEdge e = edges.get(randomEdge);
            merge(e);
        }
    }

    public void merge(Integer u, Integer v) {
        UndirectedEdge edge = new UndirectedEdge(u, v);
        merge(edge);
    }

    public void merge(UndirectedEdge edge) {
        Assert.assertTrue(edges.contains(edge));

        Assert.assertTrue(vertices.containsKey(edge.getU()));
        Assert.assertTrue(vertices.containsKey(edge.getV()));
        Vertex u = vertices.get(edge.getU());
        Vertex v = vertices.get(edge.getV());
        edges = edges.stream()
                // Remove all edges between the 2 nodes
                .filter(e -> !e.equals(edge))
                // Update all edges to replace V with U
                .map(e -> e.replace(edge.getV(), edge.getU()))
                // Filter out any loops
                .filter(e -> !e.isLoop())
                .collect(Collectors.toCollection(ArrayList::new));

        u.getMerged().addAll(v.getMerged());
        u.getMerged().add(v.getId());

        vertices.remove(edge.getV());
    }
}
