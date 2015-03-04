/**
 * Created by Eric on 2/26/2015.
 */
public class Subset {
    public static void main(String[] args)
    {
        RandomizedQueue<String> q = new RandomizedQueue<String>();

        while (!StdIn.isEmpty())
        {
            String s = StdIn.readString();
            q.enqueue(s);
        }

        int k = Integer.valueOf(args[0]).intValue();

        for (int i = 0; i < k; ++i)
        {
            StdOut.println(q.dequeue());
        }
    }
}
