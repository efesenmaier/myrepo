package part3.hw2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

        boolean isLeader() {
            return leader == this;
        }

        // Maintained in leader
        long getSize() {
            return followers.size();
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

    Map<String, Node> nodes = new HashMap<>();
    long numConnectedComponents = 0;

    public void add(String x) {
        Node replaced = nodes.put(x, new Node(x));
        assert replaced == null;
        ++numConnectedComponents;
    }

    public String find(String x) {
        return getLeader(x).name;
    }

    private Node getNode(String x) {
        Node xNode = nodes.get(x);
        assert xNode != null;
        if (xNode == null) {
            return null;
        }
        return xNode;
    }

    private Node getLeader(String x) {
        return getNode(x).leader;
    }

    public void union(String x, String y) {
        assert !hasSameLeader(x, y);

        Node xLeader = getLeader(x);
        Node yLeader = getLeader(y);

        if (xLeader.getSize() < yLeader.getSize()) {
            updateLeader(xLeader, yLeader);
        } else {
            updateLeader(yLeader, xLeader);
        }
        --numConnectedComponents;
    }

    public boolean hasSameLeader(String x, String y) {
        return find(x).equals(find(y));
    }

    private void updateLeader(Node xLeader, Node yLeader) {
        assert xLeader.isLeader();
        assert yLeader.isLeader();

        long xSize = xLeader.getSize();
        long ySize = yLeader.getSize();
        // Update nodes in X (including X) to have Y leader
        List<Node> followers = xLeader.followers.stream().collect(Collectors.toList());
        // Avoid ConcurrentModificationException by streaming to a list first
        followers.stream().forEach(node -> node.updateLeader(yLeader));

        assert xLeader.followers.size() == 0;
        assert yLeader.getSize() == xSize + ySize;
    }
}
