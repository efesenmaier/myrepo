package part1divideandconquer.hw4;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Vertex {
    private Integer id;
    private Set<Integer> merged = new HashSet<>();

    public Vertex(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Set<Integer> getMerged() {
        return merged;
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
