/* *****************************************************************************
 *  Name: lina
 *  Date: 15.9.19
 *  Description: Takes an integer k as a command-line argument;
 *  reads a sequence of strings from standard input using StdIn.readString();
 *  and prints exactly k of them, uniformly at random.
 *  Print each item from the sequence at most once.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        String str;


        while (!StdIn.isEmpty()) {
            str = StdIn.readString();
            queue.enqueue(str);

        }

        for (int i = 0; i < k; i++) {
            str = queue.dequeue();
            StdOut.println(str);
        }
    }
}
