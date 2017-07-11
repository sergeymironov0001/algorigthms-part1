import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final String ARGUMENTS_ERROR_MESSAGE = "You must set two integer values: n and t!";
    private final Double mean;
    private final Double stddev;
    private final Double confidenceLo;
    private final Double confidenceHi;

    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException(String.format("Incorrect value %d! n should be >= 1", n));
        }
        if (trials <= 0) {
            throw new IllegalArgumentException(String.format("Incorrect value %d! trials should be >= 1", trials));
        }
        double[] experimentResults = new double[trials];
        for (int t = 0; t < trials; t++) {
            experimentResults[t] = runExperiment(n);
        }
        mean = StdStats.mean(experimentResults);
        stddev = StdStats.stddev(experimentResults);
        confidenceLo = mean - ((1.96 * stddev) / Math.sqrt(trials));
        confidenceHi = mean + ((1.96 * stddev) / Math.sqrt(trials));
    }

    private double runExperiment(int n) {
        Percolation percolation = new Percolation(n);
        int openSitesCount = 0;
        int row;
        int col;
        while (!percolation.percolates()) {
            do {
                row = StdRandom.uniform(n) + 1;
                col = StdRandom.uniform(n) + 1;
            } while (percolation.isOpen(row, col));
            percolation.open(row, col);
            openSitesCount++;
        }
        return (double) openSitesCount / (double) (n * n);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return confidenceLo;
    }

    public double confidenceHi() {
        return confidenceHi;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException(ARGUMENTS_ERROR_MESSAGE);
        }
        try {
            int n = Integer.parseInt(args[0]);
            int t = Integer.parseInt(args[1]);
            PercolationStats percolationStats = new PercolationStats(n, t);

            System.out.printf("%-23s = %.17f\n", "mean", percolationStats.mean());
            System.out.printf("%-23s = %.17f\n", "stddev", percolationStats.stddev());
            System.out.printf("%-23s = %.17f, %.17f\n", "95% confidence interval", percolationStats.confidenceLo(), percolationStats.confidenceHi());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(ARGUMENTS_ERROR_MESSAGE, exception);
        }
    }
}
