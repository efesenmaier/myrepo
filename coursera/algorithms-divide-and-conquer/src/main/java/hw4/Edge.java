package hw4;

import org.testng.Assert;

import java.util.Objects;

public class Edge {
    private Integer u;
    private Integer v;

    public Edge(Integer u, Integer v) {
        this.u = u;
        this.v = v;
    }

    public Integer getU() {
        return u;
    }

    public Integer getV() {
        return v;
    }

    public Edge replace(Integer oldId, Integer newId) {
        if (u.equals(oldId)) {
            u = newId;
        }
        if (v.equals(oldId)) {
            v = newId;
        }
        return this;
    }

    public boolean isLoop() {
        return u.equals(v);
    }

    @Override
    public int hashCode() {
        return Objects.hash(u, v);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Edge edge = (Edge)obj;
        return (Objects.equals(u, edge.u) && Objects.equals(v, edge.v)) ||
                (Objects.equals(u, edge.v) && Objects.equals(v, edge.u));
    }
}
