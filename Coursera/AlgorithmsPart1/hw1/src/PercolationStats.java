/**
 * Created by Eric on 2/7/2015.
 */

public class PercolationStats {
    private int T;

    private double[] samples;
    private double mean;
    private double stddev;

    public PercolationStats(int N, int T)     // perform T independent experiments on an N-by-N grid
    {
        if (N <= 0 || T <= 0) throw new IllegalArgumentException("Invalid argument (N, T): (" + N + ", " + T + ")");

        this.T = T;
        this.samples = new double[T];

        for (int t = 0; t < T; ++t)
        {
            Percolation p = new Percolation(N);

            int n = 0;
            while (!p.percolates())
            {
                int i = StdRandom.uniform(N) + 1;
                int j = StdRandom.uniform(N) + 1;

                if (!p.isOpen(i, j))
                {
                    p.open(i, j);
                    ++n;
                }
            }
            samples[t] = (double) n / (double) (N * N);
        }

        mean = StdStats.mean(samples);
        stddev = StdStats.stddev(samples);
    }

    public double mean()                      // sample mean of percolation threshold
    {
        return mean;
    }

    public double stddev()                    // sample standard deviation of percolation threshold
    {
        return stddev;
    }

    public double confidenceLo()              // low  endpoint of 95% confidence interval
    {
        return mean - ((1.96d * stddev) / Math.sqrt(T));
    }

    public double confidenceHi()              // high endpoint of 95% confidence interval
    {
        return mean + ((1.96d * stddev) / Math.sqrt(T));
    }

    public static void main(String[] args)    // test client (described below)
    {
        int N = StdIn.readInt();
        int T = StdIn.readInt();

        PercolationStats stats = new PercolationStats(N, T);

        StdOut.printf("mean                     =   %f\n", stats.mean());
        StdOut.printf("stddev                   =   %f\n", stats.stddev());
        StdOut.printf("95%% confidence interval  =   %f, %f\n", stats.confidenceLo(), stats.confidenceHi());
    }
}