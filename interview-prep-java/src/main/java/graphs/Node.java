package graphs;

import java.util.LinkedList;

public class Node {
    String name;
    LinkedList<Node> children = new LinkedList();

    Node(String name) {
        this.name = name;
    }

    void addChild(Node child) {
        assert child != null;
        assert child.name != null;
        assert child != this; // No self-loops
        children.add(child);
    }
}
