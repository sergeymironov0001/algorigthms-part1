import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Element<Item> head;
    private Element<Item> tail;
    private int size = 0;

    /**
     * Construct an empty deque
     */
    public Deque() {
    }

    /**
     * Is the deque empty?
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Return the number of items on the deque
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * Add the item to the front
     *
     * @param item
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item to add can not be null");
        }
        Element<Item> newHead = new Element<>(item, head, null);
        if (head != null) {
            head.setPrevious(newHead);
        } else {
            tail = newHead;
        }
        head = newHead;
        size++;
    }

    /**
     * Add the item to the end
     *
     * @param item
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item to add can not be null");
        }
        Element<Item> newTail = new Element<>(item, null, tail);
        if (tail != null) {
            tail.setNext(newTail);
        } else {
            head = newTail;
        }
        tail = newTail;
        size++;
    }

    /**
     * Remove and return the item from the front
     *
     * @return
     */
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        Element<Item> element = head;
        head = head.getNext();
        if (head != null) {
            head.setPrevious(null);
        } else {
            tail = null;
        }
        size--;
        return element.getItem();
    }

    /**
     * Remove and return the item from the end
     *
     * @return
     */
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        Element<Item> element = tail;
        tail = tail.getPrevious();
        if (tail != null) {
            tail.setNext(null);
        } else {
            head = null;
        }
        size--;
        return element.getItem();
    }

    /**
     * Return an iterator over items in order from front to end
     *
     * @return
     */
    @Override
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private static class Element<Item> {
        private final Item item;
        private Element<Item> next;
        private Element<Item> previous;

        public Element(Item item, Element<Item> next, Element<Item> previous) {
            this.item = item;
            this.next = next;
            this.previous = previous;
        }

        public Item getItem() {
            return item;
        }

        public Element<Item> getNext() {
            return next;
        }

        public void setNext(Element<Item> next) {
            this.next = next;
        }

        public Element<Item> getPrevious() {
            return previous;
        }

        public void setPrevious(Element<Item> previous) {
            this.previous = previous;
        }
    }

    private class QueueIterator implements Iterator<Item> {
        private Element<Item> pointer = head;

        @Override
        public boolean hasNext() {
            return pointer != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Element<Item> element = pointer;
            pointer = pointer.getNext();
            return element.getItem();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}