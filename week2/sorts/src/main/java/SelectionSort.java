
public class SelectionSort<T extends Comparable<T>> extends Sort<T> {

    public void sort(T[] values) {
        for (int i = 0; i < values.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < values.length; j++) {
                if (values[min].compareTo(values[j]) > 0) min = j;
            }
            swap(values, i, min);
        }
    }
}
