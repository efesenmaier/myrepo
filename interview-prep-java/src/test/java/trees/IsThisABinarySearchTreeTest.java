package trees;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

@Test
public class IsThisABinarySearchTreeTest {
    @DataProvider
    private Object[][] testData() {
        return new Object[][] {
                // Basic
                {Arrays.asList(), true},
                {Arrays.asList(1), true},
                {Arrays.asList(2, 1), true},
                {Arrays.asList(1, 2), false},
                {Arrays.asList(2, 1, 3), true},
                {Arrays.asList(2, 1, 2), false},
                {Arrays.asList(3, 2, 5, 1, null, 4, 6), true},
                {Arrays.asList(3, 2, 5, 1, null, 6, 1), false},
                {Arrays.asList(4, 2, 6, 1, 3, 5, 7), true},
        };
    }

    @Test(dataProvider = "testData")
    public void test(List<Integer> nodes, boolean expected) {
        IsThisABinarySearchTree.Node root = createTree(nodes);
        IsThisABinarySearchTree solution = new IsThisABinarySearchTree();
        Assert.assertEquals(solution.checkBST(root), expected);
    }

    private IsThisABinarySearchTree.Node createTree(List<Integer> integerList) {
        IsThisABinarySearchTree.Node[] nodes = new IsThisABinarySearchTree.Node[integerList.size()];
        // Create all nodes
        for (int i = 0; i < integerList.size(); ++i) {
            Integer val = integerList.get(i);
            if (val != null) {
                nodes[i] = new IsThisABinarySearchTree.Node(val);
            }
        }

        for (int i = 0; i < nodes.length; ++i) {
            IsThisABinarySearchTree.Node parent = nodes[i];
            if (parent == null) {
                continue;
            }

            int left = 2*i+1;
            parent.left = left < nodes.length ? nodes[left] : null;

            int right = 2*i+2;
            parent.right = right < nodes.length ? nodes[right] : null;
        }

        if (nodes.length > 0) {
            return nodes[0];
        }

        return null;
    }
}
