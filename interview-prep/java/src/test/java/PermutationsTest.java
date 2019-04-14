import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PermutationsTest {
    @Test
    public void test() {
        for (int length = 1; length < 10; ++length) {
            int[] arr = new int[length];
            for (int i = 0; i < arr.length; ++i) {
                arr[i] = i+1;
            }
            long start = System.nanoTime();
            List<List<Integer>> perms = Permutations.permutations(arr);
            long duration = System.nanoTime() - start;
            System.out.println("Recursive permutations on size " + length + " took " + duration / 1000000.0 + " ms");
            validatePermutations(arr, perms);

            start = System.nanoTime();
            perms = Permutations.permutationsIterative(arr);
            duration = System.nanoTime() - start;
            System.out.println("Iterative permutations on size " + length + " took " + duration / 1000000.0 + " ms");
            validatePermutations(arr, perms);
        }
    }

    void validatePermutations(int[] arr, List<List<Integer>> perms) {
        Assert.assertNotNull(perms);
        Assert.assertEquals(perms.size(), factorial(arr.length));
        Set<List<Integer>> allPermsAsSet = new HashSet<>();
        for (List<Integer> perm : perms) {
            Assert.assertEquals(perm.size(), arr.length);
            Set<Integer> permAsSet = new HashSet<>();
            permAsSet.addAll(perm);
            for (int i : arr) {
                Assert.assertTrue(permAsSet.contains(i));
            }
            Assert.assertFalse(allPermsAsSet.contains(perm));
            allPermsAsSet.add(perm);
        }
    }

    int factorial(int n) {
        if (n == 1) {
            return 1;
        }

        return n * factorial(n-1);
    }
}
