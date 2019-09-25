import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/******************************************************************************
 *  Brute force.
 *  Write a program BruteCollinearPoints.java that examines 4 points at a time
 *  and checks whether they all lie on the same line segment, returning all
 *  such line segments.
 *  To check whether the 4 points p, q, r, and s are collinear, check whether
 *  the three slopes between p and q, between p and r, and between p and s are
 *  all equal.
 *
 * The method segments() should include each line segment containing
 * 4 points exactly once. If 4 points appear on a line segment in the
 * order p→q→r→s, then you should include either the line segment
 * p→s or s→p (but not both) and you should not include subsegments
 * such as p→r or q→r. For simplicity, we will not supply any input
 * to BruteCollinearPoints that has 5 or more collinear points.
 *
 * Corner cases. Throw an IllegalArgumentException if the argument to
 * the constructor is null, if any point in the array is null, or if
 * the argument to the constructor contains a repeated point.
 *
 * Performance requirement. The order of growth of the running time
 * of your program should be n^4 in the worst case and it should use
 * space proportional to n plus the number of line segments returned.
 *
 *****************************************************************************/

public class BruteCollinearPoints {
    private final Point[] pointsArr;
    private final ArrayList<LineSegment> segments;
    private int numberOfSegments;


    public BruteCollinearPoints(Point[] points) { // finds all line segments containing 4 points
        checkForNullArgument(points);
        pointsArr = processArgument(points);
        segments = new ArrayList<LineSegment>();
        numberOfSegments = 0;
        findAllColliniarPoints();
    }

    private void checkForNullArgument(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
    }

    private Point[] processArgument(Point[] points) {

        Point[] copyArr = new Point[points.length];

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            copyArr[i] = points[i];
        }

        Arrays.sort(copyArr);
        checkNoRepeatedPoints(copyArr);
        return copyArr;
    }

    private void checkNoRepeatedPoints(Point[] points) {
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i-1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void findAllColliniarPoints() {
        for (int i = 0; i < pointsArr.length; i++) {
            for (int j = i + 1; j < pointsArr.length; j++) {
                for (int k = j + 1; k < pointsArr.length; k++) {
                    for (int h = k + 1; h < pointsArr.length; h++) {
                        if (isPointsCollinear(pointsArr[i], pointsArr[j], pointsArr[k], pointsArr[h])) {
                            segments.add(new LineSegment(pointsArr[i], pointsArr[h]));
                            numberOfSegments++;
                        }
                    }
                }
            }
        }
    }

    /* ****************************************************************************
     *  To check whether the 4 points p, q, r, and s are collinear, check whether
     *  the three slopes between p and q, between p and r, and between p and s are
     *  all equal.
     **************************************************************************** */
    private boolean isPointsCollinear(Point p, Point q, Point r, Point s) {
        double slopePQ = p.slopeTo(q);
        double slopePR = p.slopeTo(r);
        double slopePS = p.slopeTo(s);
        return (Double.compare(slopePQ, slopePR) == 0 && Double.compare(slopePQ, slopePS) == 0);
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
