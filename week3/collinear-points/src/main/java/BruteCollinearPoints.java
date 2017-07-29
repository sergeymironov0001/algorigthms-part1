import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final LineSegment[] lineSegments;

    /**
     * Finds all line segments containing 4 points
     *
     * @param points
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Points array should be not null");
        for (Point point : points) {
            if (point == null) throw new IllegalArgumentException("Points in the array should be not null");
        }
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        checkDuplicatePoints(pointsCopy);
        lineSegments = findSegments(pointsCopy);
    }

    /**
     * The number of line segments
     *
     * @return
     */
    public int numberOfSegments() {
        return lineSegments.length;
    }

    /**
     * The line segments
     *
     * @return
     */
    public LineSegment[] segments() {
        return Arrays.copyOf(lineSegments, lineSegments.length);
    }

    private static LineSegment[] findSegments(Point[] points) {
        List<LineSegment> segments = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double slope1 = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < points.length; k++) {
                    double slope2 = points[i].slopeTo(points[k]);
                    if (slope2 != slope1) continue;

                    for (int t = k + 1; t < points.length; t++) {
                        double slope3 = points[i].slopeTo(points[t]);
                        if (slope3 != slope2) continue;

                        segments.add(new LineSegment(points[i], points[t]));
                    }
                }
            }
        }
        return segments.toArray(new LineSegment[0]);
    }

    private static void checkDuplicatePoints(Point[] points) {
        Arrays.sort(points);
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException("Array should not contain duplicate points");
            }
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        System.out.printf("%d points:\n", points.length);
        System.out.println(Arrays.toString(points));
//        for (Point p : points) {
//            p.draw();
//        }
//        StdDraw.show();

        // print and draw the line segments
        System.out.println("Segments:");
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
