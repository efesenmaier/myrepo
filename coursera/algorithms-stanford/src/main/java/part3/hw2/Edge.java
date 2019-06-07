package part3.hw2;

import java.util.Objects;

public class Edge {
    public Integer u;
    public Integer v;
    public Integer weight;
    public String name;

    public Edge(int u, int v, int weight) {
        if (u < v) {
            this.u = u;
            this.v = v;
        } else {
            this.v = u;
            this.u = v;
        }
        this.weight = weight;
        this.name = String.format("[%d,%d,%d]", this.u, this.v, weight);
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

    /**
     * Returns a unique (and repeatable/normalized) name for the edge,
     * assuming single edges between vertices or distinct weights.
     * @return
     */
    public String getName() {
        return name;
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
