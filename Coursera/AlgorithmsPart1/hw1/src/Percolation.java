/**
 * Created by Eric on 2/7/2015.
 */
public class Percolation {
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufWithoutBottom;
    private int N;
    private int virtualTop;
    private int virtualBottom;
    private boolean [] open;

    public Percolation(int N)               // create N-by-N grid, with all sites blocked
    {
        if (N <= 0) throw new IllegalArgumentException("invalid N" + N);
        uf = new WeightedQuickUnionUF(N * N + 2);
        ufWithoutBottom = new WeightedQuickUnionUF(N * N + 1);
        this.N = N;
        open = new boolean[N * N];
        virtualTop = N * N;
        virtualBottom = N * N + 1;
    }

    public void open(int i, int j)          // open site (row i, column j) if it is not open already
    {
        if (i < 1 || i > N || j < 1 || j > N) throw new IndexOutOfBoundsException("invalid index (" + i + "," + j + ")");

        if (isOpen(i, j)) return;

        open[toIndex(i, j)] = true;

        if (i > 1 && isOpen(i - 1, j))
        {
            uf.union(toIndex(i, j), toIndex(i - 1, j));
            ufWithoutBottom.union(toIndex(i, j), toIndex(i - 1, j));
        }

        if (i < N && isOpen(i + 1, j))
        {
            uf.union(toIndex(i, j), toIndex(i + 1, j));
            ufWithoutBottom.union(toIndex(i, j), toIndex(i + 1, j));
        }

        if (j > 1 && isOpen(i, j - 1))
        {
            uf.union(toIndex(i, j), toIndex(i, j - 1));
            ufWithoutBottom.union(toIndex(i, j), toIndex(i, j - 1));
        }

        if (j < N && isOpen(i, j + 1))
        {
            uf.union(toIndex(i, j), toIndex(i, j + 1));
            ufWithoutBottom.union(toIndex(i, j), toIndex(i, j + 1));
        }

        if (i == 1)
        {
            uf.union(toIndex(i, j), virtualTop);
            ufWithoutBottom.union(toIndex(i, j), virtualTop);
        }

        if (i == N)
        {
            uf.union(toIndex(i, j), virtualBottom);
        }
    }

    public boolean isOpen(int i, int j)     // is site (row i, column j) open?
    {
        if (i < 1 || i > N || j < 1 || j > N) throw new IndexOutOfBoundsException("invalid index (" + i + "," + j + ")");

        return open[toIndex(i, j)];
    }

    public boolean isFull(int i, int j)     // is site (row i, column j) full?
    {
        return isOpen(i, j) && ufWithoutBottom.connected(toIndex(i, j), virtualTop);
    }

    public boolean percolates()             // does the system percolate?
    {
        return uf.connected(virtualBottom, virtualTop);
    }

    private int toIndex(int i, int j)
    {
        int index = (i - 1) * N + (j - 1);
        return index;
    }
}