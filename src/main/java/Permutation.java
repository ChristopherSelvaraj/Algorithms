import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(final String[] args) {

        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();

        int k = Integer.parseInt(args[0]);

        int count = 0;
        while (!StdIn.isEmpty()) {
            String string = StdIn.readString();
//            This code is for passing extra memory test
            if (count == k && count != 0) {
                randomizedQueue.dequeue();
            } else {
                count++;
            }
            randomizedQueue.enqueue(string);
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(randomizedQueue.dequeue());
        }

    }
}
