import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] elements;
    private int count;

    private class ArrayIterator implements Iterator<Item> {

        private final Item[] iterationArray;
        private int i;

        ArrayIterator(Item[] array, int count) {
            this.iterationArray = Arrays.copyOf(array, count);
            StdRandom.shuffle(this.iterationArray);
            this.i = 0;
        }


        @Override
        public boolean hasNext() {
            return i < iterationArray.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = iterationArray[i];
            i++;
            return item;
        }
    }

    public RandomizedQueue() {
        elements = (Item[]) new Object[1];
        count = 0;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return count;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (count == elements.length) {
            resizeArray(2 * elements.length);
        }

        elements[count] = item;
        count++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();

        int randomInt = StdRandom.uniform(0, count);
        Item item = elements[randomInt];

        if (randomInt != count - 1)
            elements[randomInt] = elements[count - 1];
        elements[count - 1] = null;
        count--;

        if (count == elements.length / 4 && count != 0) {
            resizeArray(elements.length / 2);
        }
        return item;
    }

    private void resizeArray(int newSize) {
        elements = Arrays.copyOf(elements, newSize);
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();

        int randomNumber = StdRandom.uniform(0, count);

        return elements[randomNumber];
    }

    @Override
    public Iterator<Item> iterator() {
        return new ArrayIterator(elements, count);
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<Integer>();

        randomizedQueue.enqueue(1);
        randomizedQueue.enqueue(2);
        randomizedQueue.enqueue(3);
        randomizedQueue.enqueue(4);
        randomizedQueue.enqueue(5);
        randomizedQueue.enqueue(6);

        StdOut.println(randomizedQueue.dequeue());
        StdOut.println(randomizedQueue.dequeue());


        randomizedQueue.enqueue(7);
        randomizedQueue.enqueue(8);
        randomizedQueue.enqueue(9);
        randomizedQueue.enqueue(10);

        randomizedQueue.enqueue(11);
        randomizedQueue.enqueue(12);

        StdOut.println(randomizedQueue.size());
        StdOut.println(randomizedQueue.isEmpty());

        randomizedQueue.iterator();

        for (Integer i : randomizedQueue) {
            StdOut.println(i);
        }


    }

}
