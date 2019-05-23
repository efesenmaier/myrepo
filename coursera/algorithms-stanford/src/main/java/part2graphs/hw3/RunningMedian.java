package part2graphs.hw3;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Median maintenance algorithm.
 */
public class RunningMedian<T extends Comparable<T>> {
    private PriorityQueue<T> lowValuesMaxHeap = new PriorityQueue<>(Comparator.reverseOrder());
    private PriorityQueue<T> highValuesMinHeap = new PriorityQueue<>();

    public void add(T newValue) {
        if (!lowValuesMaxHeap.isEmpty()) {
            T lowMedian = lowValuesMaxHeap.peek();
            int compareToSmallerMedian = newValue.compareTo(lowMedian);
            if (compareToSmallerMedian < 0) {
                lowValuesMaxHeap.add(newValue);
            } else if (compareToSmallerMedian == 0) {
                addToSmallerHeap(newValue);
            } else {
                if (!highValuesMinHeap.isEmpty()) {
                    T highMedian = highValuesMinHeap.peek();
                    int compareToBiggerMedian = newValue.compareTo(highMedian);
                    if (compareToBiggerMedian > 0) {
                        highValuesMinHeap.add(newValue);
                    } else {
                        // New value is lowMedian < value <= highMedian
                        addToSmallerHeap(newValue);
                    }
                } else {
                    addToSmallerHeap(newValue);
                }
            }
        } else {
            addToSmallerHeap(newValue);
        }

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
