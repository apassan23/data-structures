import java.util.ArrayList;
import java.util.Comparator;

public class HeapSort<T> extends Heap<T> implements Sort<T> {
    public HeapSort() {
    }

    @Override
    public void sort(ArrayList<T> sequence, Comparator<T> comparator) {
        ArrayList<T> resultSequence = new ArrayList<>();
        setHeapData(sequence, comparator);
        while (!this.isEmpty())
            resultSequence.add(this.deleteMin());

        sequence.addAll(resultSequence);
    }

}
