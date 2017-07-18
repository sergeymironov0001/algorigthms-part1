public class ShellSort<T extends Comparable<T>> extends Sort<T> {

    @Override
    public void sort(T[] values) {
        int h = 1;

        while (h > values.length / 3) h = 3 * h + 1;

        while (h >= 1) {
            for (int i = h; i < values.length; i++) {
                for (int j = i; j >= h && values[j].compareTo(values[j - h]) < 0; j -= h) {
                    swap(values, j, j - h);
                }
            }
            h = h / 3;
        }
    }
}
