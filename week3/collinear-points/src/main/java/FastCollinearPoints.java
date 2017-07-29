import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastCollinearPoints {
    private static final int MINIMAL_SEGMENTS_COUNT = 3;
    private final LineSegment[] lineSegments;

    /**
     * Finds all line segments containing 4 or more points
     *
     * @param points
     */
    public FastCollinearPoints(Point[] points) {
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
        List<LineSegmentWithSlope> segments = new ArrayList<>();
        // Finding segments
        for (int i = 0; i < points.length - 1; i++) {
            Arrays.sort(points, i, points.length);
            Arrays.sort(points, i + 1, points.length, points[i].slopeOrder());
            int segmentSize = 0;
            for (int j = i + 1; j < points.length; j++) {
                double slope = points[i].slopeTo(points[j]);
                double previousSlope = points[i].slopeTo(points[j - 1]);
                if (slope == previousSlope) segmentSize++;
                else {
                    if (segmentSize >= MINIMAL_SEGMENTS_COUNT) {
                        segments.add(new LineSegmentWithSlope(previousSlope, points[i], points[j - 1]));
                    }
                    segmentSize = 1;
                }
            }
            double lastSlope = points[i].slopeTo(points[points.length - 1]);
            if (segmentSize >= MINIMAL_SEGMENTS_COUNT) {
                segments.add(new LineSegmentWithSlope(lastSlope, points[i], points[points.length - 1]));
            }
        }
        // Because in the task we have a note not to use equals and hashcode methods
        // we have to use ugly removeSubSegments function and LineSegmentWithSlope internal class
        removeSubSegments(segments);
        return segmentWithSlopeToLineSegments(segments);
    }

    private static void checkDuplicatePoints(Point[] points) {
        Arrays.sort(points);
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i - 1]) == 0)
                throw new IllegalArgumentException("Array should not contain duplicate points");
        }
    }

    private static void removeSubSegments(List<LineSegmentWithSlope> segments) {
        Collections.sort(segments);
        for (int i = segments.size() - 1; i > 0; i--) {
            if (segments.get(i).slope == segments.get(i - 1).slope
                    && segments.get(i).endPoint.compareTo(segments.get(i - 1).endPoint) == 0) {
                segments.remove(i);
            }
        }
    }

    private static LineSegment[] segmentWithSlopeToLineSegments(List<LineSegmentWithSlope> segments) {
        LineSegment[] lineSegments = new LineSegment[segments.size()];
        for (int i = 0; i < segments.size(); i++) {
            LineSegmentWithSlope lineSegmentWithSlope = segments.get(i);
            lineSegments[i] = new LineSegment(lineSegmentWithSlope.startPoint, lineSegmentWithSlope.endPoint);
        }
        return lineSegments;
    }

    private static class LineSegmentWithSlope implements Comparable<LineSegmentWithSlope> {
        private final double slope;
        private final Point startPoint;
        private final Point endPoint;

        public LineSegmentWithSlope(double slope, Point startPoint, Point endPoint) {
            this.slope = slope;
            this.startPoint = startPoint;
            this.endPoint = endPoint;
        }

        @Override
        public int compareTo(LineSegmentWithSlope lineSegmentWithSlope) {
            if (slope < lineSegmentWithSlope.slope) {
                return -1;
            } else if (slope > lineSegmentWithSlope.slope) {
                return 1;
            }
            int endPointsCompare = endPoint.compareTo(lineSegmentWithSlope.endPoint);
            if (endPointsCompare != 0) {
                return endPointsCompare;
            }
            return startPoint.compareTo(lineSegmentWithSlope.startPoint);
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

        FastCollinearPoints collinear = new FastCollinearPoints(points);
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
