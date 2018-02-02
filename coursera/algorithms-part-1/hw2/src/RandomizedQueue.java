import java.util.Iterator;

/**
 * Created by Eric on 2/26/2015.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] m_array = (Item[])new Object[2];
    private int m_start = 0;
    private int m_size = 0;

    private class QueueIterator<Item> implements Iterator<Item>
    {
        private RandomizedQueue m_q;
        private int[] m_arrayEntries;
        private int m_i = 0;

        public QueueIterator(RandomizedQueue q)
        {
            m_q = q;
            m_arrayEntries = new int[q.size()];
            for (int i = 0; i < q.size(); ++i)
            {
                m_arrayEntries[i] = i;
            }
            StdRandom.shuffle(m_arrayEntries);
        }

        public boolean	hasNext() { return m_i < m_arrayEntries.length; }

        public Item next()
        {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            int randIndex = m_arrayEntries[m_i++];
            return (Item)m_q.m_array[m_q.getIndex(randIndex)];
        }

        public void remove() { throw new java.lang.UnsupportedOperationException(); }
    }

    public RandomizedQueue()                 // construct an empty randomized queue
    {
    }

    public boolean isEmpty()                 // is the queue empty?
    {
        return (m_size == 0);
    }

    public int size()                        // return the number of items on the queue
    {
        return m_size;
    }

    public void enqueue(Item item)           // add the item
    {
        if (null == item) throw new NullPointerException();

        if (size() == m_array.length)
        {
            Item[] newArray = (Item[]) new Object[m_array.length * 2];
            for (int i = 0; i < m_size; ++i) {
                newArray[i] = m_array[getIndex(i)];
            }
            m_start = 0;
            m_array = newArray;
        }

        m_array[getIndex(m_size)] = item;
        ++m_size;
    }

    public Item dequeue()                    // remove and return a random item
    {
        if (size() == 0)
        {
            throw new java.util.NoSuchElementException();
        }

        int randIndex = getIndex(StdRandom.uniform(size()));
        int lastIndex = getIndex(m_size - 1);
        Item item = m_array[randIndex];
        m_array[randIndex] = m_array[lastIndex];
        m_array[lastIndex] = null;
        --m_size;

        if (size() < m_array.length / 4)
        {
            Item[] newArray = (Item[]) new Object[m_array.length / 2];
            for (int i = 0; i < m_size; ++i) {
                newArray[i] = m_array[getIndex(i)];
            }
            m_start = 0;
            m_array = newArray;
        }
        return item;
    }

    private int getIndex(int index)
    {
        return (m_start + index) % m_array.length;
    }

    public Item sample()                     // return (but do not remove) a random item
    {
        if (size() == 0)
        {
            throw new java.util.NoSuchElementException();
        }

        int n = StdRandom.uniform(size());
        return m_array[getIndex(n)];
    }

    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return new QueueIterator<Item>(this);
    }

    public static void main(String[] args)   // unit testing
    {
        RandomizedQueue<String> q = new RandomizedQueue();

        for (int i = 0; i < 8; ++i)
        {
            q.enqueue("" + i);
        }

        while (!q.isEmpty())
        {
            System.out.println(q.dequeue());
        }

        for (int i = 0; i < 100; ++i)
        {
            q.enqueue("" + i);
        }

        for (int i = 0; i < 50; ++i)
        {
            System.out.println(q.dequeue());
        }

        for (int i = 0; i < 8; ++i)
        {
            q.enqueue("" + i);
        }

        while (q.size() > 8)
        {
            System.out.println(q.dequeue());
        }

        for(Iterator<String> i = q.iterator(); i.hasNext(); )
        {
            System.out.print(i.next() + ", ");
        }
        System.out.println("");
    }
}
