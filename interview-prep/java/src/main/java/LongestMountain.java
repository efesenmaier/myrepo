public class LongestMountain {

    public static int longestMountain(int[] A) {
        int maxLength = 0;
        int length = 0;
        boolean climbing = true;

        for (int i = 0; i < A.length; ++i) {
            int a = A[i];
            int b;
            if (i == A.length - 1) {
                // The mountain ends at the end of the array
                b = a+1;
            } else {
                b = A[i+1];
            }
            if (climbing) {
                if (a < b) {
                    ++length;
                } else if (a > b) {
                    if (length > 0) {
                        ++length;
                        climbing = false;
                    }
                } else {
                    length = 0;
                }
            } else {
                if (a <= b) {
                    ++length;
                    if (length > maxLength) {
                        maxLength = length;
                    }
                    if (a == b) {
                        length = 0;
                    } else {
                        length = 1;
                    }
                    climbing = true;
                } else {
                    ++length;
                }
            }
        }

        return maxLength;
    }
}
