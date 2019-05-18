package trees;


import java.util.ArrayList;
import java.util.List;

public class LowestCommonAncestor {
    public static class Node {
        public Node left;
        public Node right;
        public int data;

        public Node(int data) {
            this.data = data;
            left = null;
            right = null;
        }
    }

    public static Node lca(Node root, int v1, int v2) {
        List<Node> path1 = findPathToDescendent(root, v1);
        if (path1 == null || path1.isEmpty()) {
            return null;
        }

        List<Node> path2 = findPathToDescendent(root, v2);
        if (path2 == null || path2.isEmpty()) {
            return null;
        }

        int i = 0;
        while (i < path1.size() && i < path2.size() && path1.get(i) == path2.get(i)) {
            ++i;
        }

        return path1.get(i-1);
    }

    public static List<Node> findPathToDescendent(Node root, int val) {
        List<Node> path = new ArrayList<>();
        Node n = root;
        if (n != null) {
            path.add(n);
        }
        while (n != null && n.data != val) {
            if (val < n.data) {
                n = n.left;
            } else {
                n = n.right;
            }
            if (n != null) {
                path.add(n);
            }
        }
        return path;
    }
}
