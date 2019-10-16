import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first;
    private Node<Item> last;
    private int count;

    private class Node<T> {
        T object;
        Node<T> next;
        Node<T> previous;
    }

    private class ListIterator implements Iterator<Item> {

        private Node<Item> current;

        ListIterator(Node<Item> first) {
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            Item item = current.object;
            current = current.next;

            return item;
        }
    }

    public Deque() {
        first = null;
        last = null;
        count = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return count;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node<Item> previousFirst = first;
        first = new Node<Item>();
        first.object = item;
        first.next = previousFirst;
        first.previous = null;

        if (previousFirst == null)
            last = first;
        else
            previousFirst.previous = first;

        count++;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node<Item> previousLast = last;
        last = new Node<Item>();
        last.object = item;
        last.next = null;
        last.previous = previousLast;

        if (previousLast == null)
            first = last;
        else
            previousLast.next = last;

        count++;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = first.object;
        first = first.next;

        if (first == null)
            last = first;
        else
            first.previous = null;

        count--;

        return item;
    }

    public Item removeLast() {

        if (isEmpty()) throw new NoSuchElementException();

        Item item = last.object;
        last = last.previous;

        if (last == null)
            first = last;
        else
            last.next = null;

        count--;

        return item;
    }


    @Override
    public Iterator<Item> iterator() {
        return new ListIterator(first);
    }

    public static void main(String[] args) {

        Deque<Integer> deque = new Deque<Integer>();

        // insert in first
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addFirst(4);
        deque.addFirst(5);
        deque.addFirst(6);

        StdOut.println(deque.removeLast());
        StdOut.println(deque.removeFirst());

        // insert in last
        deque.addLast(7);
        deque.addLast(8);
        deque.addLast(9);
        deque.addLast(0);
        deque.addLast(1);

        StdOut.println(deque.removeLast());
        StdOut.println(deque.removeFirst());

        StdOut.println(deque.size());

        StdOut.println(deque.isEmpty());

        Iterator<Integer> iterator = deque.iterator();

        while (iterator.hasNext()) {
            StdOut.println(iterator.next());
        }

        for (Integer a : deque) {
            StdOut.println(a);
        }

        while (!deque.isEmpty()) {
            StdOut.println(deque.removeLast());
        }
    }

}
