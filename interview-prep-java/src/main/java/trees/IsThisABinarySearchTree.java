package trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
    For the purposes of this challenge, we define a binary search tree to be a binary tree with the following properties:

    The  value of every node in a node's left subtree is less than the data value of that node.
    The  value of every node in a node's right subtree is greater than the data value of that node.
    The  value of every node is distinct.
 */
public class IsThisABinarySearchTree {
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

    static boolean checkBST(Node root) {
        List<Integer> inOrderTraversalPath = inOrderTraversalPath(root);
        return isSorted(inOrderTraversalPath);
    }

    public static List<Integer> inOrderTraversalPath(Node root) {
        List<Integer> linearizedTree = new ArrayList<>();

        Deque<Node> stack = new ArrayDeque<>();
        if (root != null) {
            stack.push(root);
        }

        Set<Node> visited = new HashSet<>();

        while (!stack.isEmpty()) {
            Node n = stack.pop();
            boolean firstVisit = !visited.contains(n);

            // On first visit, just schedule visits to left first, then this node again, then right.
            if (firstVisit) {
                if (n.right != null) {
                    stack.push(n.right);
                }

                visited.add(n);
                stack.push(n);

                // Visit left sub-tree first
                if (n.left != null) {
                    stack.push(n.left);
                }
            } else {
                linearizedTree.add(n.data);
            }
        }
        return linearizedTree;
    }

    public static boolean isSorted(List<Integer> path) {
        for (int i = 0; i < path.size()-1; ++i) {
            int first = path.get(i);
            int second = path.get(i+1);
            if (first >= second) {
                return false;
            }
        }
        return true;
    }

}

