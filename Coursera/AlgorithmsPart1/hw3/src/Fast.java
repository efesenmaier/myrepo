import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by Eric on 3/3/2015.
 */
public class Fast {

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


        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];
        Point[] sortedPoints = new Point[N];
        for (int i = 0; i < N; i++)
        {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            p.draw();

            points[i] = p;
            sortedPoints[i] = p;
        }

        Arrays.sort(points);

        HashSet<CollinearPoints> set = new HashSet<CollinearPoints>();
        ArrayList<CollinearPoints> collinearPoints = new ArrayList<CollinearPoints>();
        for (int i = 0; i < N; ++i)
        {
            Point p = points[i];
            Arrays.sort(sortedPoints, 0, N, p.SLOPE_ORDER);
            for (int j = 0; j < N; ++j)
            {
                if (p == sortedPoints[j]) continue;

                ArrayList<Point> collinearPtsArr = new ArrayList<Point>();
                collinearPtsArr.add(p);
                double slope = Math.abs(p.slopeTo(sortedPoints[j]));
                collinearPtsArr.add(sortedPoints[j]);
                while (j + 1 < N && slope == Math.abs(p.slopeTo(sortedPoints[j + 1])))
                {
                    if (sortedPoints[j + 1] == p)
                    {
                        ++j;
                        continue;
                    }

                    collinearPtsArr.add(sortedPoints[j + 1]);
                    ++j;
                }

                if (collinearPtsArr.size() > 3)
                {
                    Point[] collinearPtsPrimArr = new Point[collinearPtsArr.size()];
                    CollinearPoints collinearPts = new CollinearPoints((Point[])collinearPtsArr.toArray(collinearPtsPrimArr));
                    if (!set.contains(collinearPts))
                    {
                        set.add(collinearPts);
                        collinearPoints.add(collinearPts);
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
