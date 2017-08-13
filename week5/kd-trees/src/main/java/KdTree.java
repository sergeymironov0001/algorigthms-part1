import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Collection;

public class KdTree {
    private Node root;
    private int size = 0;

    /**
     * Is the set empty?
     *
     * @return
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Number of points in the set
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * Add the point to the set (if it is not already in the set)
     *
     * @param p
     */
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point should be not null");

        if (!contains(p)) {
            root = insert(root, 0, p);
            size++;
        }
    }

    private Node insert(Node node, int level, Point2D p) {
        if (node == null) return new Node(p);

        boolean evenLevel = level % 2 == 0;
        double nodeValue = evenLevel ? node.point.x() : node.point.y();
        double pointValue = evenLevel ? p.x() : p.y();

        if (pointValue < nodeValue) node.left = insert(node.left, level + 1, p);
        else node.right = insert(node.right, level + 1, p);
        return node;
    }

    /**
     * Does the set contain point p?
     *
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point should be not null");
        return contains(root, 0, p);
    }

    private boolean contains(Node node, int level, Point2D p) {
        if (node == null) return false;
        else if (node.point.equals(p)) return true;

        boolean evenLevel = level % 2 == 0;
        double nodeValue = evenLevel ? node.point.x() : node.point.y();
        double pointValue = evenLevel ? p.x() : p.y();

        if (pointValue < nodeValue) return contains(node.left, level + 1, p);
        return contains(node.right, level + 1, p);
    }

    /**
     * Draw all points to standard draw
     */
    public void draw() {
        draw(root, 0, new RectHV(0.0, 0.0, 1.0, 1.0));
    }

    private void draw(Node node, int level, RectHV region) {
        if (node == null) return;
        StdDraw.setPenColor(0, 0, 0);
        node.point.draw();
        if (level % 2 == 0) {
            StdDraw.setPenColor(255, 0, 0);
            StdDraw.line(node.point.x(), region.ymin(), node.point.x(), region.ymax());
            draw(node.left, level + 1, new RectHV(region.xmin(), region.ymin(), node.point.x(), region.ymax()));
            draw(node.right, level + 1, new RectHV(node.point.x(), region.ymin(), region.xmax(), region.ymax()));
        } else {
            StdDraw.setPenColor(0, 0, 255);
            StdDraw.line(region.xmin(), node.point.y(), region.xmax(), node.point.y());
            draw(node.left, level + 1, new RectHV(region.xmin(), region.ymin(), region.xmax(), node.point.y()));
            draw(node.right, level + 1, new RectHV(region.xmin(), node.point.y(), region.xmax(), region.ymax()));
        }
    }

    /**
     * All points that are inside the rectangle (or on the boundary)
     *
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Rect should be not null");
        java.util.List<Point2D> points = new ArrayList<>();
        range(root, 0, new RectHV(0.0, 0.0, 1.0, 1.0), rect, points);
        return points;
    }

    private void range(Node node, int level, RectHV region, RectHV rect, Collection<Point2D> points) {
        if (node == null) return;
        if (rect.contains(node.point)) points.add(node.point);
        if (level % 2 == 0) {
            RectHV leftRegion = new RectHV(region.xmin(), region.ymin(), node.point.x(), region.ymax());
            RectHV rightRegion = new RectHV(node.point.x(), region.ymin(), region.xmax(), region.ymax());
            if (leftRegion.intersects(rect)) range(node.left, level + 1, leftRegion, rect, points);
            if (rightRegion.intersects(rect)) range(node.right, level + 1, rightRegion, rect, points);
        } else {
            RectHV bottomRegion = new RectHV(region.xmin(), region.ymin(), region.xmax(), node.point.y());
            RectHV topRegion = new RectHV(region.xmin(), node.point.y(), region.xmax(), region.ymax());
            if (bottomRegion.intersects(rect)) range(node.left, level + 1, bottomRegion, rect, points);
            if (topRegion.intersects(rect)) range(node.right, level + 1, topRegion, rect, points);
        }
    }

    /**
     * A nearest neighbor in the set to point p; null if the set is empty
     *
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point should be not null");
        return isEmpty() ? null : nearest(root, 0, new RectHV(0.0, 0.0, 1.0, 1.0), root.point, p);
    }

    private Point2D nearest(Node node, int level, RectHV region, Point2D nearestPoint, Point2D point) {
        if (node == null) return nearestPoint;
        if (point.equals(node.point)) return node.point;

        double nearestDistance = nearestPoint.distanceSquaredTo(point);
        if (nearestDistance < region.distanceSquaredTo(point)) {
            return nearestPoint;
        }
        if (nearestDistance > node.point.distanceSquaredTo(point)) {
            nearestPoint = node.point;
        }
        if (level % 2 == 0) {
            RectHV leftRegion = new RectHV(region.xmin(), region.ymin(), node.point.x(), region.ymax());
            RectHV rightRegion = new RectHV(node.point.x(), region.ymin(), region.xmax(), region.ymax());
            if (point.x() <= node.point.x()) {
                nearestPoint = nearest(node.left, level + 1, leftRegion, nearestPoint, point);
                nearestPoint = nearest(node.right, level + 1, rightRegion, nearestPoint, point);
            } else {
                nearestPoint = nearest(node.right, level + 1, rightRegion, nearestPoint, point);
                nearestPoint = nearest(node.left, level + 1, leftRegion, nearestPoint, point);
            }
        } else {
            RectHV bottomRegion = new RectHV(region.xmin(), region.ymin(), region.xmax(), node.point.y());
            RectHV topRegion = new RectHV(region.xmin(), node.point.y(), region.xmax(), region.ymax());
            if (point.y() <= node.point.y()) {
                nearestPoint = nearest(node.left, level + 1, bottomRegion, nearestPoint, point);
                nearestPoint = nearest(node.right, level + 1, topRegion, nearestPoint, point);
            } else {
                nearestPoint = nearest(node.right, level + 1, topRegion, nearestPoint, point);
                nearestPoint = nearest(node.left, level + 1, bottomRegion, nearestPoint, point);
            }
        }
        return nearestPoint;
    }

    private static class Node {
        private final Point2D point;
        private Node left;
        private Node right;

        public Node(Point2D point) {
            this.point = point;
        }
    }

    public static void main(String[] args) {
        KdTree set = new KdTree();

        set.insert(new Point2D(0.0, 1.0));
        set.insert(new Point2D(1.0, 0.0));
//        set.range(new RectHV(0.0, 1.0, 0.0, 1.0));
//        set.insert(new Point2D(1.0, 0.0));
        set.insert(new Point2D(0.0, 0.0));
//        set.range(new RectHV(0.0, 1.0,0.0, 1.0));
        System.out.println(set.nearest(new Point2D(0.0, 0.0)));
    }
}
