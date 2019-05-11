package part1divideandconquer.hw3;

import java.util.Arrays;

public class QuickSort {

    public enum PivotStrategy {
        LEFT,
        RIGHT,
        MEDIAN_OF_THREE
    }

    public static int sortAndCountComparisons(int[] a, PivotStrategy s) {
        return sortAndCountComparisons(a, 0, a.length-1, s);
    }

    static int sortAndCountComparisons(int[] a, int l, int r, PivotStrategy s) {
        if (r - l < 1) {
            return 0;
        }

        if (s == PivotStrategy.RIGHT) {
            swap(a, l, r);
        } else if (s == PivotStrategy.MEDIAN_OF_THREE) {
            int pMedian = chooseMedianPivot(a, l, r);
            swap(a, l, pMedian);
        }

        int p = partition(a, l, r);
        int comparisons = r - l;
        comparisons += sortAndCountComparisons(a, l, p-1, s);
        comparisons += sortAndCountComparisons(a, p+1, r, s);
        return comparisons;
    }

    static int partition(int[] a, int l, int r) {
        int p = l;
        int i = l+1;
        for (int j = i; j <= r; ++j) {
            if (a[j] < a[p]) {
                swap(a, i, j);
                ++i;
            }
        }

        int pNew = i-1;
        swap(a, p, pNew);
        return pNew;
    }

    static class ListElement<T extends Comparable<T>> implements Comparable<ListElement<T>> {
        private int index;
        private T value;
        ListElement(int index, T value) {
            this.index = index;
            this.value = value;
        }

        @Override
        public int compareTo(ListElement<T> o) {
            return value.compareTo(o.value);
        }
    }

    static int chooseMedianPivot(int[] a, int l, int r) {
        ListElement[] medianArr = new ListElement[3];
        medianArr[0] = new ListElement(l, a[l]);
        medianArr[1] = new ListElement(r, a[r]);
        int middle = l + (r-l)/2;
        medianArr[2] = new ListElement(middle, a[middle]);
        Arrays.sort(medianArr);
        return medianArr[1].index;
    }

    static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
