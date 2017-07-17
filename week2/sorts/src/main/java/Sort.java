
public abstract class Sort<T extends Comparable<T>> implements ISort<T> {

    protected void swap(T[] values, int i, int j) {
        T tmp = values[j];
        values[j] = values[i];
        values[i] = tmp;
    }
}
