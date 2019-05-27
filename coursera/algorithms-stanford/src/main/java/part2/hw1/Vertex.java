package part2.hw1;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Vertex {
    private Integer id;

    /**
     * Finishing time
     */
    private int t = -1;

    private Set<Vertex> neighbors = new HashSet<>();

    public Vertex(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Set<Vertex> getNeighbors() {
        return neighbors;
    }

    public void setFinishingTime(int t) {
        this.t = t;
    }

    public int getFinishingTime() {
        return t;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Vertex vertex = (Vertex)obj;
        return Objects.equals(id, vertex.id);
    }
}
