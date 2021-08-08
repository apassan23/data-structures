import java.util.ArrayList;
import java.util.Comparator;

public class MergeSort<T> implements Sort<T> {

    @Override
    public void sort(ArrayList<T> sequence, Comparator<T> comparator) {
        // trivial case when sequence has only 1 element
        // or no element
        if (sequence.size() < 2)
            return;

        // divide into 2 subsequences
        ArrayList<T> sequence1 = new ArrayList<>(sequence.subList(0, (sequence.size() + 1) / 2));
        ArrayList<T> sequence2 = new ArrayList<>(sequence.subList(sequence1.size(), sequence.size()));
        // clearing the original sequence to accomodate
        // the merged sequence
        sequence.clear();
        // call sort on the first half
        sort(sequence1, comparator);
        // call sort on the second half
        sort(sequence2, comparator);
        // merge the sorted sequences
        merge(sequence1, sequence2, comparator, sequence);
    }

    private void merge(ArrayList<T> sequence1, ArrayList<T> sequence2, Comparator<T> comparator,
            ArrayList<T> resultSequence) {
        while (!sequence1.isEmpty() && !sequence2.isEmpty()) {
            // first element of sequence2 is smaller
            if (comparator.compare(sequence1.get(0), sequence2.get(0)) > 0) {
                resultSequence.add(sequence2.remove(0));
            }
            // first element of sequence1 is smaller
            else {
                resultSequence.add(sequence1.remove(0));
            }
        }

        // sequence2 exhausted, copy the rest of sequence1
        if (!sequence1.isEmpty()) {
            resultSequence.addAll(sequence1);
        }
        // sequence1 exhausted, copy the rest of sequence2
        if (!sequence2.isEmpty()) {
            resultSequence.addAll(sequence2);
        }
    }
}
