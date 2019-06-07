package part3.hw2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Union find tracks connected components by electing a "leader" for each component.
 * Checking for cycles then is a O(1) operation since you can check if 2 nodes are in the same
 * component.
 */
public class UnionFind {
    static class Node {
        Node(String name) {
            assert name != null;
            this.name = name;
            this.leader = this;
            this.followers.add(this);
        }

        String name;
        Node leader;

        String getLeaderName() {
            return leader.name;
        }

        // Maintained in leader
        long getLeaderSize() {
            return leader.followers.size();
        }

        void updateLeader(Node newLeader) {
            assert leader != newLeader;
            leader.followers.remove(this);
            leader = newLeader;
            leader.followers.add(this);
        }

        Set<Node> followers = new HashSet<>();

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }

        @Override
        public boolean equals(Object other) {
            if (other == null || !(other instanceof Node)) {
                return false;
            }

            Node otherNode = (Node)other;
            assert name != null;
            return name.equals(otherNode.name);
        }
    }

    private Map<String, Node> nodes = new HashMap<>();

    public void add(String x) {
        Node replaced = nodes.put(x, new Node(x));
        assert replaced == null;
    }

    public String find(String x) {
        Node xNode = nodes.get(x);
        assert xNode != null;
        if (xNode == null) {
            return null;
        }
        return xNode.getLeaderName();
    }

    public void union(String x, String y) {
        Node xLeader = nodes.get(x).leader;
        assert xLeader != null;
        if (xLeader == null) {
            return;
        }

        Node yLeader = nodes.get(y);
        assert yLeader != null;
        if (yLeader == null) {
            return;
        }

        if (xLeader.getLeaderSize() < yLeader.getLeaderSize()) {
            updateLeader(xLeader, yLeader);
        } else {
            updateLeader(yLeader, xLeader);
        }
    }

    public boolean hasSameLeader(String x, String y) {
        return find(x) == find(y);
    }

    private void updateLeader(Node xLeader, Node yLeader) {
        long xSize = xLeader.getLeaderSize();
        long ySize = yLeader.getLeaderSize();
        // Update nodes in X (including X) to have Y leader
        for (Node n : xLeader.followers) {
            n.updateLeader(yLeader);
        }

        assert xLeader.followers.size() == 0;
        assert yLeader.getLeaderSize() == xSize + ySize;
    }
}
