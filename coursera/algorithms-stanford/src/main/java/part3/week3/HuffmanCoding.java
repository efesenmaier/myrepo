package part3.week3;

import javafx.util.Pair;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class HuffmanCoding {
    static class Node {
        public Node(String symbol, long weight) {
            this.symbol = symbol;
            this.weight = weight;
        }

        public Node(Node left, Node right) {
            this.symbol = left.symbol + right.symbol;
            this.left = left;
            this.right = right;
            this.weight = left.weight + right.weight;
            this.height = Math.max(left.height, right.height) + 1;
        }

        long weight;
        String symbol;
        Node left;
        Node right;
        long height;

        public long getWeight() {
            return weight;
        }

        public long getHeight() {
            return height;
        }
    }


    /**
     * If you want a more level tree, could break ties with comparing height - .thenComparing(Node::getHeight)
     */
    private PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparing(Node::getWeight));

    Node root = null;

    Map<String, String> symbolEncoding = new HashMap<>();
    Map<String, Integer> symbolLength = new HashMap<>();

    public void addSymbol(String symbol, long weight) {
        pq.add(new Node(symbol, weight));
    }

    public void run() {
        while (pq.size() > 2) {
            Node a = pq.poll();
            Node b = pq.poll();

            pq.add(new Node(a, b));
        }

        Node a = pq.poll();
        Node b = pq.poll();

        root = new Node(a, b);

        dfs();
    }

    void dfs() {
        Deque<Pair<Node, String>> s = new ArrayDeque<>();
        s.push(new Pair<>(root, ""));

        while (s.size() > 0) {
            // Current must be a left most child (leaf)
            Pair<Node, String> currNode = s.pop();
            Node curr = currNode.getKey();
            String encoding = currNode.getValue();

            if (curr.left == null && curr.right == null) {
                assert curr.symbol != null;
                symbolEncoding.put(curr.symbol, encoding);
                symbolLength.put(curr.symbol, encoding.length());
            }

            if (curr.right != null) {
                s.push(new Pair<>(curr.right, encoding + "1"));
            }

            if (curr.left != null) {
                s.push(new Pair<>(curr.left, encoding + "0"));
            }
        }
    }

}
