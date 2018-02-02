import java.util.Iterator;

/**
 * Created by Eric on 2/25/2015.
 */
public class Deque<Item> implements Iterable<Item>
{
    private class Node<Item>
    {
        private Item m_value;
        private Node<Item> m_prev = null;
        private Node<Item> m_next = null;

        public Node(Item val)
        {
            m_value = val;
        }

        public Item getItem() { return m_value; }

        public void setNext(Node<Item> next) { m_next = next; }
        public Node<Item> getNext() { return m_next; }

        public void setPrev(Node<Item> prev) { m_prev = prev; }
        public Node<Item> getPrev() { return m_prev; }
    }

    private class DequeIterator<Item> implements Iterator<Item>
    {
        private Node<Item> m_current;

        public DequeIterator(Node<Item> first)
        {
            m_current = first;
        }

        public boolean	hasNext() { return m_current != null; }

        public Item next()
        {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            Item next = m_current.getItem();
            m_current = m_current.getNext();
            return next;
        }

        public void remove() { throw new java.lang.UnsupportedOperationException(); }
    }

    private Node<Item> m_first = null;
    private Node<Item> m_last = null;
    private int m_size = 0;

    public Deque()                           // construct an empty deque
    {

    }

    public boolean isEmpty()                 // is the deque empty?
    {
        return (m_size == 0);
    }

    public int size()                        // return the number of items on the deque
    {
        return m_size;
    }

    public void addFirst(Item item)          // add the item to the front
    {
        if (null == item) throw new NullPointerException();

        Node<Item> newNode = new Node<Item>(item);

        if (m_size == 0)
        {
            m_first = m_last = newNode;
        }
        else
        {
            newNode.setNext(m_first);
            m_first.setPrev(newNode);
            m_first = newNode;
        }

        ++m_size;
    }

    public void addLast(Item item)           // add the item to the end
    {
        if (null == item) throw new NullPointerException();

        Node<Item> newNode = new Node<Item>(item);

        if (m_size == 0)
        {
            m_first = m_last = newNode;
        }
        else
        {
            newNode.setPrev(m_last);
            m_last.setNext(newNode);
            m_last = newNode;
        }

        ++m_size;
    }
    public Item removeFirst()                // remove and return the item from the front
    {
        if (m_size == 0) throw new java.util.NoSuchElementException();

        Node<Item> oldFirst = m_first;
        assert(oldFirst.getPrev() == null);

        if (m_size == 1)
        {
            assert (m_first == m_last);

            m_first = m_last = null;
        }
        else
        {
            assert (m_first != m_last);

            // Update the first pointer
            //
            m_first = oldFirst.getNext();
            assert (m_first != null);

            // Disconnect the old first node from the new first node
            //
            oldFirst.setNext(null);

            // Disconnect the new first node from the old first node
            //
            m_first.setPrev(null);
        }

        --m_size;

        return oldFirst.getItem();
    }

    public Item removeLast()                 // remove and return the item from the end
    {
        if (m_size == 0) throw new java.util.NoSuchElementException();

        Node<Item> oldLast = m_last;
        assert(oldLast.getNext() == null);

        if (m_size == 1)
        {
            assert (m_first == m_last);

            m_first = m_last = null;
        }
        else
        {
            assert (m_first != m_last);

            // Update the first pointer
            //
            m_last = oldLast.getPrev();
            assert (m_last != null);

            // Disconnect the old last node from the new last node
            //
            oldLast.setPrev(null);

            // Disconnect the new last node from the old last node
            //
            m_last.setNext(null);
        }

        --m_size;

        return oldLast.getItem();
    }

    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new DequeIterator<Item>(m_first);
    }

    static private void printDeque(Deque deque)
    {
        for(Iterator<String> i = deque.iterator(); i.hasNext(); ) {
            String item = i.next();
            System.out.print(item);
            if (i.hasNext())
            {
                System.out.print(", ");
            }
        }
        System.out.println("");
    }

    public static void main(String[] args)   // unit testing
    {
        Deque<String> deque = new Deque<String>();

        deque.addFirst("Hi");
        printDeque(deque);

        deque.addLast("I");
        printDeque(deque);

        deque.addFirst("Bye");
        printDeque(deque);

        while (!deque.isEmpty())
        {
            deque.removeFirst();
            printDeque(deque);
        }
    }
}
