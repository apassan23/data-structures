import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> {
    private ArrayList<T> heap;
    private Comparator<T> comparator;

    public Heap() {
    }

    public Heap(ArrayList<T> data, Comparator<T> comparator) {
        this.heap = data;
        this.comparator = comparator;
        buildHeap();
    }

    public void setHeapData(ArrayList<T> data, Comparator<T> comparator) {
        this.heap = data;
        this.comparator = comparator;
        buildHeap();
    }

    public boolean isEmpty() {
        return this.heap.isEmpty();
    }

    // maintains the min heap property
    private void heapify(int index) {
        // get left and right child indices of an element with index
        int leftIndex = left(index) >= heap.size() ? index : left(index);
        int rightIndex = right(index) >= heap.size() ? index : right(index);

        // if the parent is more than its children
        while (comparator.compare(heap.get(index), heap.get(leftIndex)) > 0
                || comparator.compare(heap.get(index), heap.get(rightIndex)) > 0) {
            int minIndex = comparator.compare(heap.get(leftIndex), heap.get(rightIndex)) < 0 ? leftIndex : rightIndex;
            // swap the parent with the child with min(left, right)
            swap(index, minIndex);
            index = minIndex;
            leftIndex = left(index) >= heap.size() ? index : left(index);
            rightIndex = right(index) >= heap.size() ? index : right(index);
        }
    }

    private int left(int index) {
        return (2 * index) + 1;
    }

    private int right(int index) {
        return 2 * (index + 1);
    }

    // private int parent(int index) {
    // return (index + 1) / 2;
    // }

    private void swap(int index1, int index2) {
        T temp = heap.get(index1);
        heap.set(index1, heap.get(index2));
        heap.set(index2, temp);
    }

    private void buildHeap() {
        // start from the second last level
        for (int i = (heap.size() / 2) - 1; i >= 0; i--) {
            heapify(i);
        }
    }

    public T deleteMin() {
        // swap the top element with the last element
        swap(0, heap.size() - 1);
        // remove the last element (swapped)
        T element = heap.remove(heap.size() - 1);
        // heapify the new top element
        if (!this.isEmpty())
            heapify(0);

        return element;
    }

    public String toString() {
        return heap.toString();
    }
}
