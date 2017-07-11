import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private boolean[] sites;
    private int openSitesNumber = 0;
    private final WeightedQuickUnionUF unionFindToDetectPercolation;
    private final WeightedQuickUnionUF unionFindToFullness;

    /**
     * Create n-by-n grid, with all sites blocked
     *
     * @param n
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should be >= 1");
        }
        this.n = n;
        sites = new boolean[n * n + 2];
        sites[0] = true;
        sites[n * n + 1] = true;

        unionFindToDetectPercolation = new WeightedQuickUnionUF(n * n + 2);
        unionFindToFullness = new WeightedQuickUnionUF(n * n + 2);
    }

    /**
     * Open site (row, col) if it is not open already
     *
     * @param row
     * @param col
     */
    public void open(int row, int col) {
        validateRowAndCol(row, col);
        if (isOpen(row, col)) {
            return;
        }
        openSitesNumber++;

        int siteIndex = getIndex(row, col);
        sites[siteIndex] = true;

        if (row == 1) {
            unionFindToDetectPercolation.union(0, siteIndex);
            unionFindToFullness.union(0, siteIndex);
        }

        if (row == n) {
            unionFindToDetectPercolation.union(n * n + 1, siteIndex);
        }

        connectSiteWithNeighbors(row, col);
    }

    /**
     * Is site (row, col) open?
     *
     * @param row
     * @param col
     * @return
     */
    public boolean isOpen(int row, int col) {
        validateRowAndCol(row, col);
        return sites[getIndex(row, col)];
    }

    /**
     * Is site (row, col) full?
     *
     * @param row
     * @param col
     * @return
     */
    public boolean isFull(int row, int col) {
        validateRowAndCol(row, col);
        if (isOpen(row, col)) {
            return unionFindToFullness.connected(0, getIndex(row, col));
        }
        return false;
    }

    /**
     * number of open sites
     *
     * @return
     */
    public int numberOfOpenSites() {
        return openSitesNumber;
    }

    /**
     * Does the system percolate?
     *
     * @return
     */
    public boolean percolates() {
        return unionFindToDetectPercolation.connected(0, n * n + 1);
    }

    private void validateRowAndCol(int row, int col) {
        if (row < 1 || row > n) {
            throw new IllegalArgumentException(String.format("Incorrect value %d! Row should be 1 <= row <= n", row));
        }

        if (col < 1 || col > n) {
            throw new IllegalArgumentException(String.format("Incorrect value %d! Col should be 1 <= col <= n", col));
        }
    }

    private int getIndex(int row, int col) {
        return (row - 1) * n + col;
    }

    private void connectSiteWithNeighbors(int row, int col) {
        int index = getIndex(row, col);
        int[] neighbors = new int[4];
        neighbors[0] = col > 1 ? neighbors[0] = getIndex(row, col - 1) : -1;
        neighbors[1] = col < n ? neighbors[1] = getIndex(row, col + 1) : -1;
        neighbors[2] = row > 1 ? neighbors[2] = getIndex(row - 1, col) : -1;
        neighbors[3] = row < n ? neighbors[3] = getIndex(row + 1, col) : -1;

        for (int i = 0; i < neighbors.length; i++) {
            if (neighbors[i] > 0 && sites[neighbors[i]]) {
                unionFindToDetectPercolation.union(index, neighbors[i]);
                unionFindToFullness.union(index, neighbors[i]);
            }
        }
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        percolation.open(1, 1);
        System.out.println(percolation.percolates());

        percolation.open(2, 1);
        System.out.println(percolation.percolates());

        percolation.open(2, 2);
        System.out.println(percolation.percolates());

        percolation.open(2, 3);
        System.out.println(percolation.percolates());

        percolation.open(3, 1);
        System.out.println(percolation.percolates());
    }
}
