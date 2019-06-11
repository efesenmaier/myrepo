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
        topologicalOrderingRecursive(graph.getNode(root));
        return ordering;
    }

    List<String> findIterative() {
        ArrayDeque<Pair<Node, Iterator<Node>>> stack = new ArrayDeque();

        Node current = graph.getNode(root);
        stack.push(new Pair<>(current, current.children.iterator()));

        while (!stack.isEmpty()) {
            Pair<Node, Iterator<Node>> context = stack.pop();

            current = context.getKey();
            Iterator<Node> i = context.getValue();
            visited.add(current.name);

            if (i.hasNext()) {
                // Not done with parent, push it back for now
                stack.push(context);

                // Schedule child for visit
                Node child = i.next();
                if (!visited.contains(child.name)) {
                    stack.push(new Pair<>(child, child.children.iterator()));
                }
            } else {
                // All children have been visited, output the node name
                // For topological ordering, insert at front
                // For reverse topological ordering, append at end
                if (reverse) {
                    ordering.addLast(current.name);
                } else {
                    ordering.addFirst(current.name);
                }
            }
        }

        return ordering;
    }

    void topologicalOrderingRecursive(Node current) {
        assert current != null;

        // Mark as visited (prevents visiting nodes twice in the DAG, as well as cycles)
        if (visited.contains(current.name)) {
            return;
        }
        visited.add(current.name);

        for (Iterator<Node> i = current.children.iterator(); i .hasNext();) {
            Node child = i.next();
            topologicalOrderingRecursive(child);
        }

        // All children have been visited, output the node name
        // For topological ordering, insert at front
        // For reverse topological ordering, append at end
        if (reverse) {
            ordering.addLast(current.name);
        } else {
            ordering.addFirst(current.name);
        }
    }

}
