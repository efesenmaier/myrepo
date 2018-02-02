import java.util.Arrays;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new BySlope(this);       // YOUR DEFINITION

    private static class BySlope implements Comparator<Point>
    {
        private Point p;

        public BySlope(Point p)
        {
            this.p = p;
        }

        public int compare(Point a, Point b)
        {
            if (null == a || null == b) throw new NullPointerException();

            double slope1 = p.slopeTo(a);
            double slope2 = p.slopeTo(b);
            if (slope1 < slope2) return -1;
            else if (slope1 > slope2) return 1;
            return 0;
        }
    }

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y)
    {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw()
    {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that)
    {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that)
    {
        if (null == that) throw new NullPointerException();
        
        // Check for vertical lines (positive infinity) or equal points (negative infinity)
        //
        if (x == that.x)
        {
            if (y == that.y)
            {
                return Double.NEGATIVE_INFINITY;
            }

            return Double.POSITIVE_INFINITY;
        }

        // Check for horizontal lines
        // Ensure positive zero (0.0) is returned in order to meet API spec
        //
        if (y == that.y)
        {
            return 0.0;
        }

        return ((double)(that.y - y))/((double)(that.x - x));
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that)
    {
        if (null == that) throw new NullPointerException();

        if (x < that.x) return -1;
        else if (x > that.x) return 1;
        else if (y < that.y) return -1;
        else if (y > that.y) return 1;
        else return 0;
    }

    // return string representation of this point
    public String toString()
    {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args)
    {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            p.draw();
        }

        // display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius();
    }
}
