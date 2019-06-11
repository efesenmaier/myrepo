package graphs;

import java.util.LinkedHashMap;
import java.util.List;

public class Graph {
    LinkedHashMap<String, Node> vertices = new LinkedHashMap<>();

    void addVertex(String name, List<String> children) {
        assert name != null;
        assert children != null;

        Node vertex = vertices.getOrDefault(name, new Node(name));
        for (String child : children) {
            if (!vertices.containsKey(child)) {
                vertices.put(child, new Node(child));
            }
            vertex.addChild(child);
        }
        vertices.put(name, vertex);
    }

}
