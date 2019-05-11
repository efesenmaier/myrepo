package part1divideandconquer.hw1;

import org.testng.Assert;

import java.util.ArrayDeque;
import java.util.Deque;

public class KaratsubaMultiplication {
    public static boolean KARATSUBA_OPTIMIZATION = true;

    static int[] multiply(int[] x, int[] y) {
        if (x.length == 1 && y.length == 1) {
            return toArray(toInteger(x) * toInteger(y));
        }

        int maxLength = Math.max(x.length, y.length);
        if (maxLength % 2 == 1) {
            maxLength++;
        }
        x = padZeroes(x, maxLength);
        y = padZeroes(y, maxLength);
        int n = x.length;
        int leftLength = (n + 1) / 2;
        int[] a = new int[leftLength];
        int[] c = new int[leftLength];
        System.arraycopy(x, 0, a, 0, leftLength);
        System.arraycopy(y, 0, c, 0, leftLength);

        int rightLength = n / 2;
        int[] b = new int[rightLength];
        int[] d = new int[rightLength];
        System.arraycopy(x, leftLength, b, 0, rightLength);
        System.arraycopy(y, leftLength, d, 0, rightLength);

        int[] ac = multiply(a, c);
        int[] bd = multiply(b, d);
        int[] adPlusBc = null;
        if (KARATSUBA_OPTIMIZATION) {
            // Karatsuba optimization is to only perform 1 recursive call here (instead of 2 below)
            int[] ab = add(a, b);
            int[] cd = add(c, d);
            int[] abTimesCd = multiply(ab, cd);
            adPlusBc = subtract(subtract(abTimesCd, ac), bd);
        } else {
            int[] ad = multiply(a, d);
            int[] bc = multiply(b, c);
            adPlusBc = add(ad, bc);
        }
        return add(add(shiftLeft(ac, n), shiftLeft(adPlusBc, n/2)), bd);
    }

    static int[] add(int[] a, int []b) {
        int iA = a.length - 1;
        int iB = b.length - 1;
        ArrayDeque<Integer> result = new ArrayDeque<>(Math.max(a.length, b.length) + 1);
        boolean carry = false;

        while (iA >= 0 || iB >= 0 || carry) {
            int ai = iA >= 0 ? a[iA] : 0;
            int bi = iB >= 0 ? b[iB] : 0;
            int sum = ai+bi;
            if (carry) {
                ++sum;
            }

            result.addFirst(sum % 10);
            carry = sum >= 10;
            --iA;
            --iB;
        }

        return toArray(result);
    }

    static int[] subtract(int[] a, int []b) {
        // This method only works for a >= b, but that is expected here
        //Assert.assertTrue(greaterThanEqual(a, b));
        int iA = a.length-1;
        int iB = b.length-1;
        ArrayDeque<Integer> result = new ArrayDeque<>(a.length);

        boolean borrow = false;
        while (iA >= 0) {
            int ai = iA >= 0 ? a[iA] : 0;
            int bi = iB >= 0 ? b[iB] : 0;
            if (borrow) {
                ai = ai - 1;
            }

            if (ai < bi) {
                Assert.assertTrue(iA > 0);
                ai += 10;
                borrow = true;
            } else {
                borrow = false;
            }
            Integer temp = ai - bi;
            if (temp < 0) {
                Assert.fail();
            }
            result.addFirst(temp);
            --iA;
            --iB;
        }

        return toArray(result);
    }

    static int[] toArray(int n) {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        int copy = n;
        while (copy > 0) {
            deque.addFirst(copy % 10);
            copy = copy / 10;
        }
        return toArray(deque);
    }

    static int[] toArray(Deque<Integer> deque) {
        if (deque.isEmpty()) {
            return new int[1];
        }
        int[] resultArr = new int[deque.size()];
        int i = 0;
        while (!deque.isEmpty()) {
            resultArr[i++] = deque.removeFirst();
        }
        return trimZeroes(resultArr);
    }

    static int[] shiftLeft(int[] arr, int n) {
        int[] newArr = new int[arr.length + n];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        return newArr;
    }

    static Integer toInteger(int[] arr) {
        String value = "";
        for (int i : arr) {
            value += i;
        }
        if (value.isEmpty()) {
            return 0;
        }
        return Integer.valueOf(value);
    }

    static int[] padZeroes(int[] arr, int n) {
        Assert.assertTrue(arr.length <= n);
        if (arr.length == n) {
            return arr;
        }
        int[] newArr = new int[n];
        System.arraycopy(arr, 0, newArr, n-arr.length, arr.length);
        return newArr;
    }

    static int[] trimZeroes(int[] arr) {
        int numZeroes = 0;
        for (int i = 0; i < arr.length; ++i) {
            if (arr[i] == 0) {
                numZeroes++;
            } else {
                break;
            }
        }
        // If all zeroes, or empty array return single zero
        if (numZeroes == arr.length) {
            return new int[1];
        }
        if (numZeroes == 0) {
            return arr;
        }
        int newLength = arr.length-numZeroes;
        int[] newArr = new int[newLength];
        System.arraycopy(arr, numZeroes, newArr, 0, newArr.length);
        return newArr;
    }
}
