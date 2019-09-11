/**
 * To perform a series of computational experiments
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final int numOfTrials;
    private double[] fractions;
    private double meanValue = -1;
    private double deviation  = -1;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        numOfTrials = trials;
        fractions = new double[trials];
        performsExperiments(n, trials);
    }

    // sample mean of percolation threshold
    public double mean() {
        if (meanValue == -1)
            meanValue = StdStats.mean(fractions);
        return meanValue;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (deviation == -1)
            deviation = StdStats.stddev(fractions);
        return deviation;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = mean();
        double deviationValue = stddev();
        return mean - (CONFIDENCE_95 * deviationValue / Math.sqrt(numOfTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double deviationValue = stddev();
        return mean + (CONFIDENCE_95 * deviationValue / Math.sqrt(numOfTrials));
    }

    private void tryOpenRandomSite(int n, Percolation p) {
        // Choose a site uniformly at random among all blocked sites.
        int row = StdRandom.uniform(1, n + 1); // Returns a random integer uniformly in [a, b).
        int col = StdRandom.uniform(1, n + 1); // Returns a random integer uniformly in [a, b).
        // Open the site.
        p.open(row, col);
    }

    private void performsExperiments(int n, int trials) {
        // Performs T independent computational experiments on an n-by-n grid
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                tryOpenRandomSite(n, p);
            }
            // The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold.
            double frac = p.numberOfOpenSites();
            frac /= n * n;
            fractions[i] = frac;
        }
    }

    private void printsResults() {
        // prints the sample mean, sample standard deviation, and the 95% confidence interval for the percolation threshold.
        StdOut.printf("mean                    = %f\n", mean());
        StdOut.printf("stddev                  = %f\n", stddev());
        StdOut.printf("95%% confidence interval = [%f, %f]\n", confidenceLo(), confidenceHi());
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, trials);
        ps.printsResults();
    }

}
