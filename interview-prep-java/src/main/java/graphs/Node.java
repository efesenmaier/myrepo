package graphs;

import java.util.LinkedList;

public class Node {
    String name;
    LinkedList<String> children = new LinkedList();

    Node(String name) {
        this.name = name;
    }

    void addChild(String child) {
        assert child != null;
        assert !child.equals(name);
        children.add(child);
    }
}
