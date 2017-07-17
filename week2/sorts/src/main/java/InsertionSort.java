public class InsertionSort<T extends Comparable<T>> extends Sort<T> {

    @Override
    public void sort(T[] values) {
        for (int i = 0; i < values.length; i++) {
            for (int j = i; j > 0; j--) {
                while (values[j].compareTo(values[j - 1]) < 0) {
                    swap(values, j, j - 1);
                }
            }
        }
    }
}
