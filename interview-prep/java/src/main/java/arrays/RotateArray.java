package arrays;

public class RotateArray {
    public static int[] rotate(int[] nums, int k) {
        if (k == 0) return nums;
        int n = nums.length;
        // Any rotation on array of size 0 or 1 does nothing
        if (n <= 1) return nums;

        // Ensure -n < k < n
        k = k % n;

        // A rotation that is a multiple of n does nothing
        if (k == 0) return nums;

        // Ensure k is 0 < k < n
        if (k < 0) k += n;

        int rotated[] = new int[n];

        System.arraycopy(nums, 0, rotated, k, n - k);
        System.arraycopy(nums, n-k, rotated, 0, k);
        return rotated;
    }

    public static void rotateInPlace(int[] nums, int k) {
        int n = nums.length;
        // Any rotation on array of size 0 or 1 does nothing
        if (n <= 1) return;

        // Ensure -n < k < n
        k = k % n;

        // A rotation that is a multiple of n does nothing
        if (k == 0) return;

        // Ensure k is 0 < k < n
        if (k < 0) k += n;


        int start = 0;
        int count = 0;
        while (count < n) {
            int cur = start;
            int curVal = nums[start];

            do {
                int next = (cur + k) % n;
                int temp = nums[next];
                nums[next] = curVal;
                curVal = temp;
                cur = next;
                ++count;
            } while (cur != start);
            ++start;
        }
    }
}
