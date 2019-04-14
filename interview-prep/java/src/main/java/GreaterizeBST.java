/**
 * From https://leetcode.com/problems/convert-bst-to-greater-tree/description/
 */
public class GreaterizeBST {
     public static class TreeNode {
          int val;
          TreeNode left;
          TreeNode right;
          TreeNode(int x) { val = x; }
     }

    public static TreeNode convertBST(TreeNode root) {
        greaterize(root, 0);
        return root;
    }

    static int greaterize(TreeNode cur, int sum) {
        if (cur == null) {
            return sum;
        }

        sum = greaterize(cur.right, sum);
        cur.val += sum;
        return greaterize(cur.left, cur.val);
    }
}
