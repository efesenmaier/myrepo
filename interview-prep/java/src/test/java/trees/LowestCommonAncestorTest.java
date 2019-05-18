package trees;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static trees.LowestCommonAncestor.Node;

@Test
public class LowestCommonAncestorTest {


    @DataProvider
    private Object[][] testData() {
        return new Object[][] {
                // Basic
                {"6\n4 2 3 1 7 6\n1 7", 4},
                {"2\n" + "1 2\n" + "1 2", 1},
                {"7\n" + "5 3 8 2 4 6 7\n" + "7 3", 5},
        };
    }
    @Test(dataProvider = "testData")
    public void basicTest(String str, Integer expectedAncestor) {
        Node ancestor = test(str);
        if (expectedAncestor == null) {
            Assert.assertNull(ancestor);
        } else {
            Assert.assertEquals(ancestor.data, expectedAncestor.intValue());
        }
    }

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

    private static Node test(String input) {
        return test(new ByteArrayInputStream(input.getBytes()));
    }
    private static Node test(InputStream input) {
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
        return LowestCommonAncestor.lca(root, v1, v2);
    }
}
