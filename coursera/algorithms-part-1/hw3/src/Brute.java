import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Eric on 3/1/2015.
 */
public class Brute {
    private static class CollinearPoints implements Comparable<CollinearPoints> {
        private Point[] points;

        public CollinearPoints (Point[] points)
        {
            this.points = points;

            Arrays.sort(points);
        }

        public CollinearPoints(Point p1, Point p2, Point p3, Point p4)
        {
            points = new Point[4];
            points[0] = p1;
            points[1] = p2;
            points[2] = p3;
            points[3] = p4;

            Arrays.sort(points);
        }

        @Override
        public boolean equals(Object obj)
        {
            if (!(obj instanceof CollinearPoints))
                return false;
            if (obj == this)
                return true;

            CollinearPoints rhs = (CollinearPoints) obj;
            return Arrays.equals(points, rhs.points);
        }

        public int hashCode()
        {
            int result = 17;
            for (int i = 0; i < points.length; ++i)
            {
                result = 31 * result + points[i].hashCode();
            }
            return result;
        }

        public int compareTo(CollinearPoints other)
        {
            for (int i = 0; i < points.length; ++i)
            {
                if (i < other.points.length)
                {
                    int result = points[i].compareTo(other.points[i]);
                    if (result != 0) return result;
                }
            }

            if (points.length < other.points.length)
            {
                return -1;
            }
            else if (other.points.length > points.length)
            {
                return 1;
            }

            return 0;
        }

        public void draw()
        {
            points[0].drawTo(points[points.length - 1]);
        }

        public String toString()
        {
            String str = "";
            for (int i = 0; i < points.length; ++i)
            {
                str += points[i];
                if (i != points.length - 1)
                {
                    str += " -> ";
                }
            }
            return str;
        }
    }
    // unit test
    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger

        ArrayList<Point> arrayList = new ArrayList<Point>();

        {
            Point p = new Point(1062, 18347);
            Point q = new Point(18094, 22562);
            Point r = new Point(13249, 3521);

            int compare = p.SLOPE_ORDER.compare(q, r);

            //student   p.compare(q, r) = -1
            //reference p.compare(q, r) = 1
            double slopeq = p.slopeTo(q); //    = 0.24747534053546266
            double sloper = p.slopeTo(r); //    = -1.2165422171165996

            int a = 1;
            try {
                p.compareTo(null);
            }
            catch(NullPointerException npe)
            {
                int b = 2;
            }

            int c = 3;
        }

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        arrayList.ensureCapacity(N);
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            p.draw();

            arrayList.add(p);
        }

        ArrayList<CollinearPoints> collinearPoints = new ArrayList<CollinearPoints>();
        for (int i = 0; i < arrayList.size() - 3; ++i)
        {
            for (int j = i + 1; j < arrayList.size() - 2; ++j)
            {
                for (int k = j + 1; k < arrayList.size() - 1; ++k)
                {
                    for (int l = k + 1; l < arrayList.size(); ++l)
                    {
                        Point p1 = arrayList.get(i);
                        Point p2 = arrayList.get(j);
                        Point p3 = arrayList.get(k);
                        Point p4 = arrayList.get(l);

                        double slope1 = p1.slopeTo(p2);
                        double slope2 = p1.slopeTo(p3);
                        double slope3 = p1.slopeTo(p4);

                        if (slope1 == slope2 && slope1 == slope3)
                        {
                            collinearPoints.add(new CollinearPoints(p1, p2, p3, p4));
                        }
                    }
                }
            }
        }

        Collections.sort(collinearPoints);

        for (CollinearPoints pts : collinearPoints)
        {
            StdOut.println(pts);
            pts.draw();
        }

        // display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius();
    }
}
