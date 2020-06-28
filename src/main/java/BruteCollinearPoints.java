import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private final ArrayList<LineSegment> lineSegments;

    public BruteCollinearPoints(Point[] points) {

        if (points == null) throw new IllegalArgumentException();
        checkForNull(points);

        lineSegments = new ArrayList<LineSegment>();
        findLineSegments(points);
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[]{});
    }

    private void findLineSegments(Point[] points) {

        Point[] sortedOrder = Arrays.copyOf(points, points.length);
        Arrays.sort(sortedOrder);

        duplicateCheck(sortedOrder);

        for (int i = 0; i < sortedOrder.length; i++) {
            Point p = sortedOrder[i];
            findLineSegments(sortedOrder, p, 0, 1);
        }

    }

    private Point findLineSegments(Point[] points, Point p, double slope, int count) {

        for (int i = 0; i < points.length; i++) {
            Point p1 = points[i];
            // Continuing if the Points are same.
            if (p == p1) continue;

            if (p.compareTo(p1) < 0) {
                Point result = null;
                if (count == 1) {
                    result = findLineSegments(points, p1, p.slopeTo(p1), count + 1);
                    if (result != null)
                        lineSegments.add(new LineSegment(p, result));
                    continue;
                }

                if (slope == p.slopeTo(p1)) {
                    if (count == 3) return p1;
                    result = findLineSegments(points, p1, slope, count + 1);
                    if (result == null) continue;
                    return result;
                }

            }
        }
        return null;
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
        StdDraw.setXscale(-100, 32768);
        StdDraw.setYscale(-100, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

//    public static void main(String[] args) {
//        String a = "";
//
//        switch (a) {
//            case "something":
//                System.out.println("asasas");
//                break;
//            default:
//                System.out.println("null");
//        }
//    }
}
