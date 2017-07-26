import java.util.Comparator;

public class Point implements Comparable<Point> {

    /**
     * Constructs the point (x, y)
     *
     * @param x
     * @param y
     */
    public Point(int x, int y) {
    }

    /**
     * Draws this point
     */
    public void draw() {
    }

    /**
     * Draws the line segment from this point to that point
     *
     * @param that
     */
    public void drawTo(Point that) {
    }

    /**
     * String representation
     *
     * @return
     */
    public String toString() {
        return null;
    }

    /**
     * Compare two points by y-coordinates, breaking ties by x-coordinates
     *
     * @param that
     * @return
     */
    public int compareTo(Point that) {
        return 0;
    }

    /**
     * The slope between this point and that point
     *
     * @param that
     * @return
     */
    public double slopeTo(Point that) {
        return 0.0;
    }

    /**
     * Compare two points by slopes they make with this point
     *
     * @return
     */
    public Comparator<Point> slopeOrder() {
        return null;
    }
}