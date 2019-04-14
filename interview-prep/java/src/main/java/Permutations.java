import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;

public class Permutations {
    /**
     * Recursive solution is for a set of numbers A, where P is
     * the permutations of A, then
     * if size(A) = 1, then P={A};
     * else
     *   foreach (a in A)
     *     subset = A - a
     *     foreach (subPerm in permutations(subset))
     *       P += list(a, subPerm)
     * @param arr
     * @return
     */
    public static List<List<Integer>> permutations(int[] arr) {
        BitSet bitSet = new BitSet(arr.length);
        return permutations(arr, bitSet);
    }

    static List<List<Integer>> permutations(int[] arr, BitSet subsetBitSet) {
        List<List<Integer>> perms = new LinkedList<>();
        int cardinality = subsetBitSet.cardinality();
        int length = arr.length;
        if (cardinality == length - 1) {
            int i = subsetBitSet.nextClearBit(0);
            LinkedList<Integer> linkedList = new LinkedList();
            linkedList.add(arr[i]);
            perms.add(linkedList);
            return perms;
        }

        for (int i = 0; i < arr.length; ++i) {
            if (!subsetBitSet.get(i)) {
                int a = arr[i];
                subsetBitSet.set(i);
                for (List<Integer> list : permutations(arr, subsetBitSet)) {
                    list.add(0, a);
                    perms.add(list);
                }
                subsetBitSet.clear(i);
            }
        }
        return perms;
    }

    public static List<List<Integer>> permutationsIterative(int[] arr) {
        List<List<Integer>> perms = new LinkedList<>();
        if (arr.length == 0) {
            return perms;
        }

        List<Integer> perm = new LinkedList<>();
        perm.add(arr[0]);
        perms.add(perm);
        for (int i = 1; i < arr.length; ++i) {
            perms = createPermutations(perms, arr[i]);
        }

        return perms;
    }

    /**
     * Copy the set of permutations, inserting a into an incrementing index on each
     * copy of the set.
     * @param perms
     * @param a
     * @return
     */
    static List<List<Integer>> createPermutations(List<List<Integer>> perms, int a) {
        int extraCopiesToMake = perms.get(0).size();
        List<List<Integer>> newPerms = new LinkedList<>();
        for (int i = 0; i < extraCopiesToMake; ++i) {
            for (List<Integer> perm : perms) {
                LinkedList<Integer> newPerm = new LinkedList<>();
                int j = 0;
                for (Integer i2 : perm) {
                    if (j == i) {
                        newPerm.add(a);
                    }
                    newPerm.add(i2);
                    ++j;
                }
                newPerms.add(newPerm);
            }
        }

        for (List<Integer> perm : perms) {
            perm.add(perm.size(), a);
            newPerms.add(perm);
        }

        return newPerms;
    }
}
