package part3.hw2;

import java.util.Objects;

public class Edge {
    public String u;
    public String v;
    public Long weight;

    public Edge(String u, String v, Long weight) {
        if (u.compareTo(v) < 0) {
            this.u = u;
            this.v = v;
        } else {
            this.v = u;
            this.u = v;
        }
        this.weight = weight;
        assert this.u.compareTo(this.v) < 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(u, v, weight);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != getClass()) {
            return false;
        }

        Edge otherEdge = (Edge)other;
        return Objects.equals(u, otherEdge.u) &&
                Objects.equals(v, otherEdge.v) &&
                Objects.equals(weight, otherEdge.weight);
    }
}
