import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] fractionsOfOpenSite;
    private final int trials;
    private double mean;
    private double stddev;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("n and trails must be greater than Zero");

        fractionsOfOpenSite = new double[trials];
        this.trials = trials;
        runPercolation(n);

    }

    private void runPercolation(int n) {

        for (int i = 0; i < trials; i++) {

            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {
                int row = getRandomNumber(n);
                int col = getRandomNumber(n);
                percolation.open(row, col);
            }
            fractionsOfOpenSite[i] = ((double) percolation.numberOfOpenSites()) / ((double) (n * n));
        }

        this.mean = StdStats.mean(fractionsOfOpenSite);
        this.stddev = StdStats.stddev(fractionsOfOpenSite);
    }

    private int getRandomNumber(int n) {
        return StdRandom.uniform(1, n + 1);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return mean() - getConfidenceStdDev();
    }

    public double confidenceHi() {
        return mean() + getConfidenceStdDev();
    }

    private double getConfidenceStdDev() {
        double confidenceStdDev = 1.96 * stddev();
        double sqrtOfTrail = Math.sqrt(trials);

        return confidenceStdDev / sqrtOfTrail;
    }

    public static void main(String[] args) {

        if (args.length == 0) {
            throw new IllegalArgumentException("n and trail must be provided");
        }

        try {
            int n = Integer.parseInt(args[0]);
            int trails = Integer.parseInt(args[1]);

            PercolationStats percolationStats = new PercolationStats(n, trails);
            StdOut.println("mean                    = " + percolationStats.mean());
            StdOut.println("stddev                  = " + percolationStats.stddev());
            StdOut.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");

        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(ex);
        }


    }
}
