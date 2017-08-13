import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PointSET {
    private final Set<Point2D> pointsTree = new TreeSet<>();

    /**
     * Is the set empty?
     *
     * @return
     */
    public boolean isEmpty() {
        return pointsTree.isEmpty();
    }

    /**
     * Number of points in the set
     *
     * @return
     */
    public int size() {
        return pointsTree.size();
    }

    /**
     * Add the point to the set (if it is not already in the set)
     *
     * @param p
     */
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point should be not null");

        if (!pointsTree.contains(p)) pointsTree.add(p);
    }

    /**
     * Does the set contain point p?
     *
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point should be not null");

        return pointsTree.contains(p);
    }

    /**
     * Draw all points to standard draw
     */
    public void draw() {
        for (Point2D point : pointsTree) point.draw();
    }

    /**
     * All points that are inside the rectangle (or on the boundary)
     *
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Rect should be not null");

        List<Point2D> pointsInRange = new ArrayList<>();
        for (Point2D point : pointsTree) if (rect.contains(point)) pointsInRange.add(point);

        return pointsInRange;
    }

    /**
     * A nearest neighbor in the set to point p; null if the set is empty
     *
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point should be not null");

        Point2D nearestPoint = null;
        double minDistance = Double.MAX_VALUE;
        for (Point2D point : pointsTree) {
            double distance = p.distanceTo(point);
            if (minDistance > distance) {
                minDistance = distance;
                nearestPoint = point;
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args) {
    }
}
