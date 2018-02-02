public class RotateArray {
    public void rotate(int[] nums, int k) {
        if (k == 0) return;
        int n = nums.length;
        int rotated[] = new int[n];
        for (int i = 0; i < n; ++i) {
            rotated[(i+k)%n] = nums[i];
        }
        for (int i = 0; i < n; ++i) {
            nums[i] = rotated[i];
        }
    }

    public void rotateInPlace(int[] nums, int k) {
        int n = nums.length;
        if (n <= 1) return;
        k = k % n;
        if (k <= 0) return;

        int count = 0;
        for (int start = 0; count < nums.length; start++) {
            int current = start;
            int prev = nums[start];
            do {
                int next = (current + k) % nums.length;
                int temp = nums[next];
                nums[next] = prev;
                prev = temp;
                current = next;
                count++;
            } while (start != current);
        }
    }
}
