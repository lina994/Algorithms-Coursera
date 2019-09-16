/* *****************************************************************************
 *  Name: lina
 *  Date: 15.9.19
 *  Description: Randomized queue. A randomized queue is similar to a stack
 *  or queue, except that the item removed is chosen uniformly at random among
 *  items in the data structure.
 **************************************************************************** */
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;


public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int capacity;
    private int currSize;

    // construct an empty randomized queue
    public RandomizedQueue() {
        capacity = 2;
        queue = (Item[]) new Object[capacity];
        currSize = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return currSize == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return currSize;
    }

    private void resize(int newSize) {
        Item[] copy = (Item[]) new Object[newSize];
        for (int i = 0; i < currSize; i++)
            copy[i] = queue[i];
        queue = copy;
        capacity = newSize;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        if (currSize == capacity)
            resize(2 * capacity);

        queue[currSize++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();

        int randomIndex = StdRandom.uniform(currSize);
        Item item = queue[randomIndex];
        queue[randomIndex] = queue[--currSize];
        queue[currSize] = null;

        if (currSize > 0 && currSize == capacity / 4)
            resize(capacity / 2);

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();

        int randomIndex = StdRandom.uniform(currSize);
        return queue[randomIndex];
    }

    // Each iterator must return the items in uniformly random order. The order
    // of two or more iterators to the same randomized queue must be mutually
    // independent; each iterator must maintain its own random order.
    private class RandomArrayIterator implements Iterator<Item> {

        private Item[] copy;
        private int copySize;

        public RandomArrayIterator() {
            copy = (Item[]) new Object[currSize];
            copySize = currSize;
            for (int i = 0; i < currSize; i++) {
                copy[i] = queue[i];
            }
        }


        @Override
        public boolean hasNext() {
            return copySize > 0;
        }

        @Override
        public Item next() {
            if (copySize == 0)
                throw new java.util.NoSuchElementException();

            int randomIndex = StdRandom.uniform(copySize);
            Item item = copy[randomIndex];
            copy[randomIndex] = copy[--copySize];
            copy[copySize] = null;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomArrayIterator();

    }

    // unit testing (required)
    public static void main(String[] args) {
        // Unit testing.
        // Your main() method must call directly every public constructor
        // and method to help verify that they work as prescribed
        // (e.g., by printing results to standard output).

        int num;
        Iterator<Integer> iter;
        int val;


        RandomizedQueue<Integer> randQueue = new RandomizedQueue<Integer>();
        StdOut.printf("%s\n", randQueue.isEmpty() ? "pass" : "fail");
        StdOut.printf("%s\n", (randQueue.size() == 0) ? "pass" : "fail");

        // create [1]
        randQueue.enqueue(1);
        StdOut.printf("%s\n", (!randQueue.isEmpty()) ? "pass" : "fail");
        StdOut.printf("%s\n", (randQueue.size() == 1) ? "pass" : "fail");
        iter = randQueue.iterator();
        StdOut.print("expexted: \"1, \"");
        StdOut.print(", got: \"");
        while (iter.hasNext()) {
            num = iter.next();
            StdOut.print(num + ", ");
        }
        StdOut.print("\"\n");

        // create [1, 5]
        randQueue.enqueue(5);
        StdOut.printf("%s\n", (!randQueue.isEmpty()) ? "pass" : "fail");
        StdOut.printf("%s\n", (randQueue.size() == 2) ? "pass" : "fail");
        iter = randQueue.iterator();
        StdOut.print("expexted numbers: \"5, 1, \"");
        StdOut.print(", got: \"");
        while (iter.hasNext()) {
            num = iter.next();
            StdOut.print(num + ", ");
        }
        StdOut.print("\"\n");

        // sample
        val = randQueue.sample();
        StdOut.printf("%s\n", (val == 1 || val == 5) ? "pass" : "fail");
        iter = randQueue.iterator();
        StdOut.print("expexted numbers: \"5, 1, \"");
        StdOut.print(", got: \"");
        while (iter.hasNext()) {
            num = iter.next();
            StdOut.print(num + ", ");
        }
        StdOut.print("\"\n");

        // array with size == 1
        val = randQueue.dequeue();
        StdOut.printf("%s\n", (!randQueue.isEmpty()) ? "pass" : "fail");
        StdOut.printf("%s\n", (randQueue.size() == 1) ? "pass" : "fail");
        StdOut.printf("%s\n", (val == 1 || val == 5) ? "pass" : "fail");
        iter = randQueue.iterator();
        StdOut.print("expexted: \"1, \" or \"5, \"");
        StdOut.print(", got: \"");
        while (iter.hasNext()) {
            num = iter.next();
            StdOut.print(num + ", ");
        }
        StdOut.print("\"\n");

        // array with size == 0
        val = randQueue.dequeue();
        StdOut.printf("%s\n", (randQueue.isEmpty()) ? "pass" : "fail");
        StdOut.printf("%s\n", (randQueue.size() == 0) ? "pass" : "fail");
        StdOut.printf("%s\n", (val == 1 || val == 5) ? "pass" : "fail");
        iter = randQueue.iterator();
        StdOut.print("expexted: \"\" ");
        StdOut.print(", got: \"");
        while (iter.hasNext()) {
            num = iter.next();
            StdOut.print(num + ", ");
        }
        StdOut.print("\"\n");






    }

}
