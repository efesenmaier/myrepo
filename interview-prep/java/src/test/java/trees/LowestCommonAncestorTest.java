package trees;

import org.testng.annotations.Test;
import trees.LowestCommonAncestor;

import java.io.InputStream;
import java.util.Scanner;

import static trees.LowestCommonAncestor.Node;

@Test
public class LowestCommonAncestorTest {



    private static Node insert(Node root, int data) {
        if(root == null) {
            return new Node(data);
        } else {
            Node cur;
            if(data <= root.data) {
                cur = insert(root.left, data);
                root.left = cur;
            } else {
                cur = insert(root.right, data);
                root.right = cur;
            }
            return root;
        }
    }

    private static int test(InputStream input) {
        Scanner scan = new Scanner(input);
        int t = scan.nextInt();
        Node root = null;
        while(t-- > 0) {
            int data = scan.nextInt();
            root = insert(root, data);
        }
        int v1 = scan.nextInt();
        int v2 = scan.nextInt();
        scan.close();
        Node ans = LowestCommonAncestor.lca(root, v1, v2);
        return ans.data;
    }
}
