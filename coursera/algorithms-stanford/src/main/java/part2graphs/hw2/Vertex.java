package part2graphs.hw2;

import java.util.Map;

public class Vertex {
    private Map<Integer, Integer> neighbors; // End vertex with weight

    public Vertex(Map<Integer, Integer> neighbors) {
        this.neighbors = neighbors;
    }

    public Map<Integer, Integer> getNeighbors() {
        return neighbors;
    }
}
