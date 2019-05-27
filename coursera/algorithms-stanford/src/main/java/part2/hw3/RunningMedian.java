package part2.hw3;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Median maintenance algorithm.
 */
public class RunningMedian<T extends Comparable<T>> {
    private PriorityQueue<T> lowValuesMaxHeap = new PriorityQueue<>(Comparator.reverseOrder());
    private PriorityQueue<T> highValuesMinHeap = new PriorityQueue<>();

    public void add(T value) {
        if (!lowValuesMaxHeap.isEmpty()) {
            T lowMedian = lowValuesMaxHeap.peek();
            int compareToSmallerMedian = value.compareTo(lowMedian);
            if (compareToSmallerMedian < 0) {
                // Case 1: If less than lowMedian, must be added to lowValues heap
                lowValuesMaxHeap.add(value);
            } else if (compareToSmallerMedian == 0) {
                // Case 2: If equal to lowMedian, then add to smaller heap (can go in either)
                addToSmallerHeap(value);
            } else {
                // Case 3: Greater than lowMedian
                if (!highValuesMinHeap.isEmpty()) {
                    T highMedian = highValuesMinHeap.peek();
                    int compareToBiggerMedian = value.compareTo(highMedian);

                    if (compareToBiggerMedian > 0) {
                        // Case 3a: If greater than lowMedian and highMedian, add to the highValues heap
                        highValuesMinHeap.add(value);
                    } else {
                        // Case 3b: If greater than lowMedian but lessThan or equal to highMedian, add to smaller heap (can go in either)
                        addToSmallerHeap(value);
                    }
                } else {
                    // Case 3c: If greater than lowMedian and highValues heap is empty, just add it there
                    highValuesMinHeap.add(value);
                }
            }
        } else {
            // Case 4: First element - lowValues heap is empty - just add it there
            lowValuesMaxHeap.add(value);
        }

        assert Math.abs(lowValuesMaxHeap.size() - highValuesMinHeap.size()) <= 2;

        // Re-balance the heaps
        if (lowValuesMaxHeap.size() - highValuesMinHeap.size() > 1) {
            highValuesMinHeap.add(lowValuesMaxHeap.remove());
        } else if (highValuesMinHeap.size() - lowValuesMaxHeap.size() > 1) {
            lowValuesMaxHeap.add(highValuesMinHeap.remove());
        }

        assert Math.abs(lowValuesMaxHeap.size() - highValuesMinHeap.size()) <= 1;
    }

    private void addToSmallerHeap(T newValue) {
        if (lowValuesMaxHeap.size() <= highValuesMinHeap.size()) {
            lowValuesMaxHeap.add(newValue);
        } else {
            highValuesMinHeap.add(newValue);
        }
    }

    public T median() {
        if (lowValuesMaxHeap.isEmpty() && highValuesMinHeap.isEmpty()) {
            throw new UnsupportedOperationException("Empty heaps");
        }

        if (lowValuesMaxHeap.size() < highValuesMinHeap.size()) {
            return highValuesMinHeap.peek();
        } else if (highValuesMinHeap.size() < lowValuesMaxHeap.size()) {
            return lowValuesMaxHeap.peek();
        } else {
            return lowValuesMaxHeap.peek();
        }
    }
}
