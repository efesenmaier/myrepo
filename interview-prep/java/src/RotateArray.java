public class RotateArray {

    public static void rotate(int[] nums, int k) {
        if (k == 0) return;
        int n = nums.length;
        // Any rotation on array of size 0 or 1 does nothing
        if (n <= 1) return;

        // Ensure -n < k < n
        k = k % n;

        // A rotation that is a multiple of n does nothing
        if (k == 0) return;

        // Ensure k is 0 < k < n
        if (k < 0) k += n;

        int rotated[] = new int[n];

        System.arraycopy(nums, 0, rotated, k, n - k);
        System.arraycopy(nums, n-k, rotated, 0, k);
        //for (int i = 0; i < n; ++i) {
        //    rotated[(i+k)%n] = nums[i];
        //}

        System.arraycopy(rotated, 0, nums, 0, n);
        //for (int i = 0; i < n; ++i) {
        //    nums[i] = rotated[i];
        //}
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
