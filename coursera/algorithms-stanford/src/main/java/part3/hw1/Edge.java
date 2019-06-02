package part3.hw1;

import java.util.Objects;

public class Edge {
    public Integer u;
    public Integer v;
    public Integer weight;

    public Edge(int u, int v, int weight) {
        if (u < v) {
            this.u = u;
            this.v = v;
        } else {
            this.v = u;
            this.u = v;
        }
        this.weight = weight;
        assert this.u < this.v;
    }

    int getOtherVertex(int u) {
        if (this.u == u) {
            return this.v;
        } else {
            assert this.v == u;
            return this.u;
        }
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
