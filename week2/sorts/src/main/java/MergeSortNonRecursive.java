public class MergeSortNonRecursive<T extends Comparable<T>> extends Sort<T> {

    private void merge(T[] src, T[] dst, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++) {
            src[k] = dst[k];
        }
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) dst[k] = src[j++];
            else if (j > hi) dst[k] = src[i++];
            else if (src[i].compareTo(src[j]) <= 0) dst[k] = src[i++];
            else dst[k] = src[j++];
        }
    }

    private void sort(T[] src, T[] dst) {
        for (int sz = 1; sz < src.length; sz *= 2) {
            for (int lo = 0; lo < src.length - sz; lo += sz + sz) {
                int mid = lo + sz - 1;
                int hi = Math.min(lo + sz + sz - 1, src.length - 1);
                merge(src, dst, lo, mid, hi);
            }
        }
    }

    @Override
    public void sort(T[] values) {
        T[] src = values.clone();
        sort(src, values);
    }
}
