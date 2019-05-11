package part1divideandconquer.hw2;

import org.testng.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FindInversions {

    public static long find(int[] a) {
        Result result = sortAndCount(a);
        Arrays.sort(a);
        Assert.assertEquals(toList(a), toList(result.a));
        return result.count;
    }

    private static Result sortAndCount(int[] a) {
        if (a.length == 1) {
            return new Result(a, 0);
        }

        int leftLength = a.length / 2;
        int[] leftA = new int[leftLength];
        System.arraycopy(a, 0, leftA, 0, leftLength);
        Result left = sortAndCount(leftA);

        int rightLength = a.length - leftLength;
        int[] rightA = new int[rightLength];
        System.arraycopy(a, leftLength, rightA, 0, rightLength);
        Result right = sortAndCount(rightA);

        return combineAndCount(left, right);
    }

    private static Result combineAndCount(Result left, Result right) {
        int[] a = new int[left.a.length + right.a.length];

        int i = 0;
        int j = 0;
        int k = 0;
        long count = 0;
        while (i < a.length) {
            if (k >= right.a.length ||
                    (j < left.a.length && left.a[j] <= right.a[k])) {
                a[i] = left.a[j];
                ++i;
                ++j;
            } else {
                a[i] = right.a[k];
                ++i;
                ++k;
                count += (left.a.length - j);
            }
        }

        return new Result(a, count + left.count + right.count);
    }

    private static List<Integer> toList(int[] array) {
        return Arrays.stream(array).boxed()
                .collect(Collectors.toList());
    }

    public static class Result {
        public int[] a;
        public long count;
        public Result(int [] a, long count) {
            this.a = a;
            this.count = count;
        }
    }
}
