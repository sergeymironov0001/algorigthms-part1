import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import static org.junit.Assert.assertTrue;


public class ISortTest {
    private static final int CHECKS_COUNT = 50;
    private static final Random random = new Random();
    private static final int ARRAY_LENGTH = 10;
    private static final int MIN_VALUE = -100;
    private static final int MAX_VALUE = 100;
    private ISort<Integer> sort;

    @Before
    public void init() {
//        sort = new SelectionSort<>();
//        sort = new InsertionSort<>();
//        sort = new ShellSort<>();
//        sort = new MergeSort<>();
        sort = new MergeSortNonRecursive<>();
    }

    @Test
    public void sortMethodSortsRandomArrayCorrectly() throws Exception {
        Integer[] array;
        for (int i = 0; i < CHECKS_COUNT; i++) {
            array = generateRandomArray(ARRAY_LENGTH, MIN_VALUE, MAX_VALUE);
            sort.sort(array);
            assertTrue(String.format("Arrays is not sorted: %s", Arrays.toString(array)), isArraySorted(array));
        }
    }

    @Test
    public void sortMethodSortsAlreadySortedArrayCorrectly() throws Exception {
        Integer[] array;
        for (int i = 0; i < CHECKS_COUNT; i++) {
            array = generateRandomArray(ARRAY_LENGTH, MIN_VALUE, MAX_VALUE);
            Arrays.sort(array);
            sort.sort(array);
            assertTrue(isArraySorted(array));
        }
    }

    @Test
    public void sortMethodSortsReverseSortedArrayCorrectly() throws Exception {
        Integer[] array;
        Comparator<Integer> reverseComparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        };
        for (int i = 0; i < CHECKS_COUNT; i++) {
            array = generateRandomArray(ARRAY_LENGTH, MIN_VALUE, MAX_VALUE);
            Arrays.sort(array, reverseComparator);
            sort.sort(array);
            assertTrue(isArraySorted(array));
        }
    }

    private static Integer[] generateRandomArray(int length, int minValue, int maxValue) {
        assert length > 0 : "Array length should be > 0";
        assert minValue < maxValue : "Min value should be < max value";

        Integer[] array = new Integer[length];

        for (int i = 0; i < length; i++) {
            int randValue = random.nextInt(maxValue - minValue) + minValue;
            array[i] = randValue;
        }
        return array;
    }

    private static <T extends Comparable<T>> boolean isArraySorted(T[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i].compareTo(array[i + 1]) > 0) {
                return false;
            }
        }
        return true;
    }
}