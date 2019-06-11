package graphs;

import java.util.LinkedHashMap;
import java.util.List;

public class Graph {
    LinkedHashMap<String, Node> vertices = new LinkedHashMap<>();

    void addVertex(String name, List<String> children) {
        assert name != null;
        assert children != null;

        Node vertex = vertices.getOrDefault(name, new Node(name));
        for (String childName : children) {
            Node child = vertices.getOrDefault(childName, new Node(childName));
            if (!vertices.containsKey(childName)) {
                vertices.put(childName, child);
            }
            vertex.addChild(child);
        }
        vertices.put(name, vertex);
    }

    Node getNode(String name) {
        return vertices.get(name);
    }

}
