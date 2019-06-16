package part2.hw2;

import java.util.HashMap;
import java.util.Map;

public class Vertex {
    String name;
    private Map<String, Integer> neighbors; // End vertex with weight

    public Vertex(String name) {
        this(name, new HashMap<>());
    }

    public Vertex(String name, Map<String, Integer> neighbors) {
        this.name = name;
        this.neighbors = neighbors;
    }

    public Map<String, Integer> getNeighbors() {
        return neighbors;
    }

    public void addEdgeIfDoesntExist(String v, int weight) {
        if (!neighbors.containsKey(v)) {
            neighbors.put(v, weight);
        }
    }
}
