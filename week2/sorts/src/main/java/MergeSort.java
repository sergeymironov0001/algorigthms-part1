public class MergeSort<T extends Comparable<T>> extends Sort<T> {


    private void merge(T[] src, T[] dst, int low, int mid, int hi) {
        int i = low;
        int j = mid + 1;
        for (int k = low; k <= hi; k++) {
            if (i > mid) dst[k] = src[j++];
            else if (j > hi) dst[k] = src[i++];
            else if (src[j].compareTo(src[i]) <= 0) dst[k] = src[j++];
            else dst[k] = src[i++];
        }
    }

    private void sort(T[] src, T[] dst, int low, int hi) {
        if (low >= hi) return;
        int mid = low + (hi - low) / 2;

        sort(dst, src, low, mid);
        sort(dst, src, mid + 1, hi);

        merge(src, dst, low, mid, hi);
    }

    @Override
    public void sort(T[] values) {
        T[] tmp = values.clone();
        sort(tmp, values, 0, values.length - 1);
    }
}
