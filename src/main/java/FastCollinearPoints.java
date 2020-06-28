import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> lineSegments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        checkForNull(points);

        lineSegments = new ArrayList<LineSegment>();
        findLineSegments(points);
    }

    private void findLineSegments(Point[] points) {

        Point[] sortedOrder = Arrays.copyOf(points, points.length);
        Arrays.sort(sortedOrder);

        duplicateCheck(sortedOrder);

        for (int i = 0; i < sortedOrder.length; i++) {

            Point p = sortedOrder[i];
            Arrays.sort(points, p.slopeOrder());

            int count = 0;
            Point minPoint = p;
            Point maxPoint = p;
            for (int j = 1; j < points.length; j++) {
                if (p.slopeTo(points[j - 1]) == p.slopeTo(points[j])) {
                    count++;
                    if (minPoint.compareTo(points[j - 1]) > 0) minPoint = points[j - 1];
                    if (maxPoint.compareTo(points[j - 1]) < 0) maxPoint = points[j - 1];

                    if (count >= 2 && j == points.length - 1) {
                        if (minPoint.compareTo(points[j]) > 0) minPoint = points[j];
                        if (maxPoint.compareTo(points[j]) < 0) maxPoint = points[j];
                        addLineSegment(new LineSegment(minPoint, maxPoint));
                    }

                } else {
                    if (count >= 2) {
                        if (minPoint.compareTo(points[j - 1]) > 0) minPoint = points[j - 1];
                        if (maxPoint.compareTo(points[j - 1]) < 0) maxPoint = points[j - 1];

                        addLineSegment(new LineSegment(minPoint, maxPoint));
                    }
                    count = 0;
                    minPoint = p;
                    maxPoint = p;
                }
            }
        }
    }

    private void addLineSegment(LineSegment lineSegment) {
        for (LineSegment lineSegment1 : lineSegments) {
            if (lineSegment1.toString().equals(lineSegment.toString())) {
                return;
            }
        }
        lineSegments.add(lineSegment);
    }


    private void checkForNull(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
        }
    }

    private void duplicateCheck(Point[] sortedOrder) {
        for (int i = 0; i < sortedOrder.length - 1; i++) {
            if (sortedOrder[i].compareTo(sortedOrder[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[]{});
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