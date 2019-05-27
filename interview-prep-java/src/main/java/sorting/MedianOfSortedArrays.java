package sorting;

public class MedianOfSortedArrays {

    public static double find(int[] nums1, int[] nums2) {
        int length = nums1.length + nums2.length;
        boolean even = length % 2 == 0;
        int index = length / 2;

        int a = -1;
        int b = -1;
        boolean medianIsInA = false;
        boolean secondIsInA = false;
        for (int i = 0; i <= index; ++i) {
            secondIsInA = medianIsInA;
            if (a == nums1.length - 1) {
                ++b;
                medianIsInA = false;
            } else if (b == nums2.length - 1) {
                ++a;
                medianIsInA = true;
            } else {
                if (nums1[a + 1] < nums2[b+1]) {
                    ++a;
                    medianIsInA = true;
                } else {
                    ++b;
                    medianIsInA = false;
                }
            }
        }

        double median;
        if (medianIsInA) {
            median = nums1[a];
        } else {
            median = nums2[b];
        }
        if (even) {
            if (secondIsInA) {
                if (medianIsInA) {
                    median += nums1[a-1];
                } else {
                    median += nums1[a];
                }
            } else {
                if (medianIsInA) {
                    median += nums2[b];
                } else {
                    median += nums2[b-1];
                }
            }
            median /= 2.0;
        }
        return median;
    }

    public double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        int length = nums1.length + nums2.length;
        boolean even = length % 2 == 0;
        int index = (length-1) / 2;

        int a = -1;
        int b = -1;
        boolean medianIsInA = false;
        for (int i = 0; i <= index; ++i) {
            if (a == nums1.length - 1) {
                ++b;
                medianIsInA = false;
            } else if (b == nums2.length - 1) {
                ++a;
                medianIsInA = true;
            } else {
                if (nums1[a + 1] < nums2[b+1]) {
                    ++a;
                    medianIsInA = true;
                } else {
                    ++b;
                    medianIsInA = false;
                }
            }
        }

        double median;
        if (medianIsInA) {
            median = nums1[a];
        } else {
            median = nums2[b];
        }
        if (even) {
            int a2 = a + 1;
            int b2 = b + 1;
            if (a == nums1.length - 1) {
                median += nums2[b2];
            } else if (b == nums2.length - 1) {
                median += nums1[a2];
            } else {
                if (nums1[a2] < nums2[b2]) {
                    median += nums1[a2];
                } else {
                    median += nums2[b2];
                }
            }
            median /= 2.0;
        }
        return median;
    }
}
