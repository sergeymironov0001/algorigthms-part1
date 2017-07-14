import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] items;

    public RandomizedQueue() {
        items = (Item[]) new Object[2];
    }

    /**
     * Is the queue empty?
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Return the number of items on the queue
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * Add the item
     *
     * @param item
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size == items.length) resize(2 * items.length);
        items[size] = item;
        size++;
    }

    /**
     * Remove and return a random item
     *
     * @return
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int itemIndexToDelete = StdRandom.uniform(size);
        Item item = items[itemIndexToDelete];
        items[itemIndexToDelete] = items[size - 1];
        items[size - 1] = null;
        size--;
        return item;
    }

    /**
     * Return (but do not remove) a random item
     *
     * @return
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return items[StdRandom.uniform(size)];
    }

    /**
     * Return an independent iterator over items in random order
     *
     * @return
     */
    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private void resize(int capacity) {
        assert capacity >= size;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = items[i];
        }
        items = temp;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final RandomizedQueue<Item> queue = new RandomizedQueue<>();

        public RandomizedQueueIterator() {
            for (int i = 0; i < size; i++) {
                queue.enqueue(items[i]);
            }
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return queue.dequeue();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();

        rq.enqueue(4);
        rq.enqueue(3);
        rq.dequeue();
        rq.dequeue();

    }
}
