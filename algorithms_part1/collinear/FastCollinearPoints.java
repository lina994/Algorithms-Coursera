import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/******************************************************************************
 *  A faster, sorting-based solution.
 *  Remarkably, it is possible to solve the problem much faster than the
 *  brute-force solution described above. Given a point p, the following method
 *  determines whether p participates in a set of 4 or more collinear points.
 *
 *  - Think of p as the origin.
 *  - For each other point q, determine the slope it makes with p.
 *  - Sort the points according to the slopes they makes with p.
 *  - Check if any 3 (or more) adjacent points in the sorted order have equal
 *    slopes with respect to p. If so, these points, together with p, are collinear.
 *
 * Applying this method for each of the n points in turn yields an efficient
 * algorithm to the problem. The algorithm solves the problem because points
 * that have equal slopes with respect to p are collinear, and sorting brings
 * such points together. The algorithm is fast because the bottleneck operation
 * is sorting.
 *
 * The method segments() should include each maximal line segment containing
 * 4 (or more) points exactly once. For example, if 5 points appear on a line
 * segment in the order p→q→r→s→t, then do not include the subsegments
 * p→s or q→t.
 *
 * Corner cases. Throw an IllegalArgumentException if the argument to the
 * constructor is null, if any point in the array is null, or if the argument
 * to the constructor contains a repeated point.
 *
 * Performance requirement. The order of growth of the running time of your
 * program should be n^2 log n in the worst case and it should use space
 * proportional to n plus the number of line segments returned.
 * FastCollinearPoints should work properly even if the input has 5 or more
 * collinear points.
 *****************************************************************************/

public class FastCollinearPoints {

    private final Point[] pointsArr;
    private final ArrayList<LineSegment> segments;
    private int numberOfSegments;

    public FastCollinearPoints(Point[] points) { // finds all line segments containing 4 or more points
        checkForNullArgument(points);
        pointsArr = new Point[points.length];
        segments = new ArrayList<LineSegment>();
        numberOfSegments = 0;
        copyPointArray(points);
        checkNoRepeatedPoints(pointsArr);
        findAllColliniarPoints();
    }

    private void checkForNullArgument(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
    }

    private void copyPointArray(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            pointsArr[i] = points[i];
        }
    }

    private void checkNoRepeatedPoints(Point[] points) {
        Arrays.sort(pointsArr);
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i-1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void findAllColliniarPoints() {
        for (int i = 0; i < pointsArr.length; i++) {
            Arrays.sort(pointsArr);
            Point origin = pointsArr[i];
            Arrays.sort(pointsArr, origin.slopeOrder());
            findSegmentsIncludedOrigin(origin);
        }
    }

    private void findSegmentsIncludedOrigin(Point origin) {
        ArrayList<Point> segmentPoints = new ArrayList<Point>();
        int countOfColliniarPoints = 0;
        double lastValue = Double.NEGATIVE_INFINITY;

        for (int j = 1; j < pointsArr.length; j++) {
            double slop = origin.slopeTo(pointsArr[j]);
            if (Double.compare(slop, lastValue) == 0) {

                segmentPoints.add(pointsArr[j]);
                countOfColliniarPoints++;

                if (j == pointsArr.length - 1 && countOfColliniarPoints >= 4) {
                    tryAddSegment(segmentPoints);
                }
            }
            else {
                if (countOfColliniarPoints >= 4) {
                    tryAddSegment(segmentPoints);
                }

                segmentPoints.clear();
                lastValue = slop;
                segmentPoints.add(origin);
                segmentPoints.add(pointsArr[j]);
                countOfColliniarPoints = 2;

            }
        }
    }

    private void tryAddSegment(ArrayList<Point> segmentPoints) {
        Point[] pArr = new Point[segmentPoints.size()];
        segmentPoints.toArray(pArr);

        if (pArr[0].compareTo(pArr[1]) < 0) {
            LineSegment segment = new LineSegment(pArr[0], pArr[pArr.length - 1]);
            segments.add(segment);
            numberOfSegments++;
        }
    }

    public int numberOfSegments() { // the number of line segments
        return numberOfSegments;
    }

    public LineSegment[] segments() { // the line segments
        LineSegment[] segmentsArr = new LineSegment[segments.size()];
        return segments.toArray(segmentsArr);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
