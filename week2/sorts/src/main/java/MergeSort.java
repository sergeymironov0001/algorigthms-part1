public class MergeSort<T extends Comparable<T>> extends Sort<T> {

    private void merge(T[] src, T[] dst, int lo, int mid, int hi) {
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) dst[k] = src[j++];
            else if (j > hi) dst[k] = src[i++];
            else if (src[i].compareTo(src[j]) <= 0) dst[k] = src[i++];
            else dst[k] = src[j++];
        }
    }

    private void sort(T[] src, T[] dst, int lo, int hi) {
        if (lo >= hi) return;

        int mid = lo + (hi - lo) / 2;

        sort(dst, src, lo, mid);
        sort(dst, src, mid + 1, hi);

        merge(src, dst, lo, mid, hi);
    }

    @Override
    public void sort(T[] values) {
        T[] src = values.clone();
        sort(src, values, 0, values.length - 1);
    }
}
