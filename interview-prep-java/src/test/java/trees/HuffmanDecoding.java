package trees;

public class HuffmanDecoding {
    public static class Node {
         public int frequency; // the frequency of this tree
        public char data;
        public Node left, right;
    }

    void decode(String s, Node root) {
        Node current = root;

        int i = 0;

        StringBuffer decoded = new StringBuffer();

        while (i < s.length()) {
            char val = s.charAt(i);
            current = val == '0' ? current.left : current.right;
            if (isLeafNode(current)) {
                decoded.append(current.data);
                current = root;
            }

            ++i;
        }
        System.out.println(decoded.toString());
    }

    boolean isLeafNode(Node current) {
        return current.left == null;
    }
}
