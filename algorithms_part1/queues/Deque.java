/* *****************************************************************************
 *  Name: lina
 *  Date: 15.9.19
 *  Description: Dequeue: A double-ended queue or deque (pronounced “deck”) is
 *  a generalization of a stack and a queue that supports adding and removing
 *  items from either the front or the back of the data structure.
 **************************************************************************** */


import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;


public class Deque<Item> implements Iterable<Item> {

    private class Node {
        private Item value;
        private Node prev;
        private Node next;
    }

    private Node head;
    private Node tail;
    private int size;

    // construct an empty deque
    public Deque() {
        size = 0;
        head = null;
        tail = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node prevHeader = head;
        head = new Node();
        head.value = item;
        head.next = prevHeader;
        if (size != 0) prevHeader.prev = head;
        else tail = head;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node prevTail = tail;
        tail = new Node();
        tail.value = item;
        tail.prev = prevTail;
        if (size != 0) prevTail.next = tail;
        else head = tail;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();

        Item item = head.value;
        head = head.next;
        if (size != 1) head.prev = null;
        else tail = null;
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();

        Item item = tail.value;
        tail = tail.prev;
        if (size != 1) tail.next = null;
        else head = null;
        size--;
        return item;
    }

    // over items in order from front to back
    private class ListIterator implements Iterator<Item> {

        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();

            Item item = current.value;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
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

        // create empty Deque
        Deque<Integer> deque = new Deque<>();
        StdOut.printf("%s\n", deque.isEmpty() ? "pass" : "fail");
        StdOut.printf("%s\n", (deque.size() == 0) ? "pass" : "fail");

        // create [1] Deque
        deque.addFirst(1);
        StdOut.printf("%s\n", (!deque.isEmpty()) ? "pass" : "fail");
        StdOut.printf("%s\n", (deque.size() == 1) ? "pass" : "fail");
        iter = deque.iterator();
        StdOut.print("expexted: \"1, \"");
        StdOut.print(", got: \"");
        while (iter.hasNext()) {
            num = iter.next();
            StdOut.print(num + ", ");
        }
        StdOut.print("\"\n");

        // create [5, 1] Deque
        deque.addFirst(5);
        StdOut.printf("%s\n", (!deque.isEmpty()) ? "pass" : "fail");
        StdOut.printf("%s\n", (deque.size() == 2) ? "pass" : "fail");
        iter = deque.iterator();
        StdOut.print("expexted: \"5, 1, \"");
        StdOut.print(", got: \"");
        while (iter.hasNext()) {
            num = iter.next();
            StdOut.print(num + ", ");
        }
        StdOut.print("\"\n");

        // create [5, 1, 7] Deque
        deque.addLast(7);
        StdOut.printf("%s\n", (!deque.isEmpty()) ? "pass" : "fail");
        StdOut.printf("%s\n", (deque.size() == 3) ? "pass" : "fail");
        iter = deque.iterator();
        StdOut.print("expexted: \"5, 1, 7, \"");
        StdOut.print(", got: \"");
        while (iter.hasNext()) {
            num = iter.next();
            StdOut.print(num + ", ");
        }
        StdOut.print("\"\n");

        // create [1, 7] Deque
        val = deque.removeFirst();
        StdOut.printf("%s\n", (!deque.isEmpty()) ? "pass" : "fail");
        StdOut.printf("%s\n", (deque.size() == 2) ? "pass" : "fail");
        StdOut.printf("%s\n", (val == 5) ? "pass" : "fail");
        iter = deque.iterator();
        StdOut.print("expexted: \"1, 7, \"");
        StdOut.print(", got: \"");
        while (iter.hasNext()) {
            num = iter.next();
            StdOut.print(num + ", ");
        }
        StdOut.print("\"\n");

        // create [1] Deque
        val = deque.removeLast();
        StdOut.printf("%s\n", (!deque.isEmpty()) ? "pass" : "fail");
        StdOut.printf("%s\n", (deque.size() == 1) ? "pass" : "fail");
        StdOut.printf("%s\n", (val == 7) ? "pass" : "fail");
        iter = deque.iterator();
        StdOut.print("expexted: \"1, \"");
        StdOut.print(", got: \"");
        while (iter.hasNext()) {
            num = iter.next();
            StdOut.print(num + ", ");
        }
        StdOut.print("\"\n");

        // create [] Deque
        val = deque.removeFirst();
        StdOut.printf("%s\n", (deque.isEmpty()) ? "pass" : "fail");
        StdOut.printf("%s\n", (deque.size() == 0) ? "pass" : "fail");
        StdOut.printf("%s\n", (val == 1) ? "pass" : "fail");
        iter = deque.iterator();
        StdOut.print("expexted: \"\"");
        StdOut.print(", got: \"");
        while (iter.hasNext()) {
            num = iter.next();
            StdOut.print(num + ", ");
        }
        StdOut.print("\"\n");

        // create [9] Deque
        deque.addLast(9);
        StdOut.printf("%s\n", (!deque.isEmpty()) ? "pass" : "fail");
        StdOut.printf("%s\n", (deque.size() == 1) ? "pass" : "fail");
        iter = deque.iterator();
        StdOut.print("expexted: \"9, \"");
        StdOut.print(", got: \"");
        while (iter.hasNext()) {
            num = iter.next();
            StdOut.print(num + ", ");
        }
        StdOut.print("\"\n");

        // create [] Deque
        val = deque.removeLast();
        StdOut.printf("%s\n", (deque.isEmpty()) ? "pass" : "fail");
        StdOut.printf("%s\n", (deque.size() == 0) ? "pass" : "fail");
        StdOut.printf("%s\n", (val == 9) ? "pass" : "fail");
        iter = deque.iterator();
        StdOut.print("expexted: \"\"");
        StdOut.print(", got: \"");
        while (iter.hasNext()) {
            num = iter.next();
            StdOut.print(num + ", ");
        }
        StdOut.print("\"\n");

    }
}