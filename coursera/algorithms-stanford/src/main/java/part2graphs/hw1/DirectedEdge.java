package part2graphs.hw1;

import java.util.Objects;

public class DirectedEdge {
    private Integer u;
    private Integer v;

    public DirectedEdge(Integer u, Integer v) {
        this.u = u;
        this.v = v;
    }

    public Integer getU() {
        return u;
    }

    public Integer getV() {
        return v;
    }

    public void reverse() {
        Integer temp = u;
        u = v;
        v = temp;
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

        DirectedEdge edge = (DirectedEdge)obj;
        return Objects.equals(u, edge.u) && Objects.equals(v, edge.v);
    }
}
