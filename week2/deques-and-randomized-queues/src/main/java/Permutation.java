import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("You should define k - of elements for output from randomized queue!");
        }
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String element = StdIn.readString();
            queue.enqueue(element);
        }

        for (int i = 0; i < k && !queue.isEmpty(); i++) {
            StdOut.println(queue.dequeue());
        }
    }
}
