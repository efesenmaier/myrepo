package graphs;

import javafx.util.Pair;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * Topological ordering on a DAG (directed acyclic graph) is used for workflow problems, scheduling problems,
 * project management.
 *
 */
public class TopologicalOrdering {

    Graph graph;
    String root;
    boolean reverse;
    public TopologicalOrdering(Graph graph, String root, boolean reverse) {
        this.graph = graph;
        this.root = root;
        this.reverse = reverse;
    }

    Set<String> visited = new HashSet<>();
    LinkedList<String> ordering = new LinkedList<>();

    List<String> findRecursive() {
        topologicalOrderingRecursive(root);
        return ordering;
    }

    List<String> findIterative() {
        Node current = graph.vertices.get(root);
        ArrayDeque<Pair<String, Iterator<String>>> stack = new ArrayDeque();
        stack.push(new Pair<>(root, current.children.iterator()));

        while (!stack.isEmpty()) {
            Pair<String, Iterator<String>> context = stack.pop();

            String currentName = context.getKey();
            Iterator<String> i = context.getValue();
            visited.add(currentName);

            if (i.hasNext()) {
                // Not done with parent, push it back for now
                stack.push(context);

                String childName = i.next();
                Node childNode = graph.vertices.get(childName);

                if (!visited.contains(childName)) {
                    stack.push(new Pair<>(childName, childNode.children.iterator()));
                }
            } else {
                if (reverse) {
                    ordering.addLast(currentName);
                } else {
                    ordering.addFirst(currentName);
                }
            }
        }

        return ordering;
    }

    void topologicalOrderingRecursive(String currentName) {
        Node current = graph.vertices.get(currentName);
        assert current != null;

        // Mark as visited (prevents visiting nodes twice in the DAG, as well as cycles)
        if (visited.contains(currentName)) {
            return;
        }
        visited.add(currentName);

        for (Iterator<String> i = current.children.iterator(); i .hasNext();) {
            String childName = i.next();
            Node child = graph.vertices.get(childName);
            topologicalOrderingRecursive(child.name);
        }

        if (reverse) {
            ordering.addLast(currentName);
        } else {
            ordering.addFirst(currentName);
        }
    }

}
