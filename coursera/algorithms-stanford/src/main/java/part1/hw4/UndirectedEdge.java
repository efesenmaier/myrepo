package part1.hw4;

import java.util.Objects;

public class UndirectedEdge {
    private Integer u;
    private Integer v;

    public UndirectedEdge(Integer u, Integer v) {
        this.u = u;
        this.v = v;
    }

    public Integer getU() {
        return u;
    }

    public Integer getV() {
        return v;
    }

    public UndirectedEdge replace(Integer oldId, Integer newId) {
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

        UndirectedEdge edge = (UndirectedEdge)obj;
        return (Objects.equals(u, edge.u) && Objects.equals(v, edge.v)) ||
                (Objects.equals(u, edge.v) && Objects.equals(v, edge.u));
    }
}
