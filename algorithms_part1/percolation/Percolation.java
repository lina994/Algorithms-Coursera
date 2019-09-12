/*******************************************************************************
 * Percolation data type
 ******************************************************************************/


/*******************************************************************************
 * Union-find data type (API)
 * ---------------------------
 * UF(int N) - initialize union-find data structure with N objects (0 to N – 1)
 * void union(int p, int q) - add connection between p and q
 * boolean connected(int p, int q) - are p and q in the same component?
 * int find(int p) - component identifier for p (0 to N – 1)
 * int count() - number of components
 *****************************************************************************/


import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int gridLength;
    private int totalOpenSites;
    private boolean[] grid; // false - close, true - open

    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF fullUf;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        gridLength = n;
        totalOpenSites = 0;

        // Introduce 2 virtual sites (and connections to top and bottom).
        grid = new boolean[n * n + 2]; // Initialize all sites to be blocked.
        uf = new WeightedQuickUnionUF(n * n + 2);
        fullUf = new WeightedQuickUnionUF(n * n + 2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOutsideRange(row, col))
            throw new IllegalArgumentException();

        if (isOpen(row, col))
            return;

        int index = getIndex(row, col);
        grid[index] = true;
        totalOpenSites++;
        tryConnectToVirtualSites(row, index);
        tryConnectToNeighbors(row, col, index);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (isOutsideRange(row, col))
            throw new IllegalArgumentException();

        return grid[getIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (isOutsideRange(row, col))
            throw new IllegalArgumentException();

        return fullUf.connected(0, getIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return totalOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(0, gridLength * gridLength + 1);
    }

    private boolean isOutsideRange(int row, int col) {
        if (row < 1 || row > gridLength)
            return true;
        if (col < 1 || col > gridLength)
            return true;
        return false;
    }

    private int getIndex(int row, int col) {
        return (row - 1) * gridLength + col;
    }

    private int getLeftSiteIndex(int row, int col) {
        if (col == 1) {
            return -1;
        }
        return getIndex(row, col - 1);
    }

    private int getRightSiteIndex(int row, int col) {
        if (col == gridLength) {
            return -1;
        }
        return getIndex(row, col + 1);
    }

    private int getUpSiteIndex(int row, int col) {
        if (row == 1) {
            return -1;
        }
        return getIndex(row - 1, col);
    }

    private int getDownSiteIndex(int row, int col) {
        if (row == gridLength) {
            return -1;
        }
        return getIndex(row + 1, col);
    }

    private void tryConnectToNeighbors(int row, int col, int index) {
        tryConnectToNeighbor(index, getLeftSiteIndex(row, col));
        tryConnectToNeighbor(index, getRightSiteIndex(row, col));
        tryConnectToNeighbor(index, getUpSiteIndex(row, col));
        tryConnectToNeighbor(index, getDownSiteIndex(row, col));
    }

    private void tryConnectToNeighbor(int index, int neighborIndex) {
        if (neighborIndex != -1 && grid[neighborIndex]) {
            uf.union(index, neighborIndex);
            fullUf.union(index, neighborIndex);
        }
    }

    private void tryConnectToVirtualSites(int row, int index) {
        if (row == 1) {
            uf.union(index, 0);
            fullUf.union(index, 0);
        }
        if (row == gridLength) {
            uf.union(index, gridLength * gridLength + 1);
        }
    }

}
