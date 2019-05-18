package trees;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class GreaterizeBSTTest {
    public void test() {
        GreaterizeBST.TreeNode root = new GreaterizeBST.TreeNode(5);
        GreaterizeBST.TreeNode right = new GreaterizeBST.TreeNode(13);
        root.right = right;
        GreaterizeBST.TreeNode left = new GreaterizeBST.TreeNode(2);
        root.left = left;
        GreaterizeBST.convertBST(root);
        Assert.assertEquals(root.right.val, 13);
        Assert.assertEquals(root.val, 18);
        Assert.assertEquals(root.left.val, 20);

        Assert.assertNull(GreaterizeBST.convertBST(null));
    }
}
