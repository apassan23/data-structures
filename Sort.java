import java.util.ArrayList;
import java.util.Comparator;

public interface Sort<T> {
    public void sort(ArrayList<T> sequence, Comparator<T> comparator);
}
