import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {


    private final int n;

    // Connect Component tracker
    private final int topRow;
    private final int bottomRow;
    private final WeightedQuickUnionUF percolationComponent;


    // two dimensional array tracker
    private final boolean[][] fieldStatus;
    private final WeightedQuickUnionUF fullStatus;
    private int numberOfOpenSites = 0;

    public Percolation(int n) {

        if (n <= 0) throw new IllegalArgumentException("n should be greater than Zero");

        this.n = n;

        fieldStatus = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                fieldStatus[i][j] = false;
            }
        }

        int numberOfField = n * n;
        topRow = numberOfField;
        bottomRow = numberOfField + 1;

        percolationComponent = new WeightedQuickUnionUF(numberOfField + 2);
        fullStatus = new WeightedQuickUnionUF(numberOfField + 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        if (isOpen(row, col)) return;

        int arrayRow = row - 1;
        int arrayCol = col - 1;

        if (arrayRow == 0) {
            if (!percolationComponent.connected(topRow, fieldNumber(arrayRow, arrayCol)))
                percolationComponent.union(topRow, fieldNumber(arrayRow, arrayCol));
            if (!fullStatus.connected(topRow, fieldNumber(arrayRow, arrayCol)))
                fullStatus.union(topRow, fieldNumber(arrayRow, arrayCol));
        }

        if (arrayRow == n - 1) {
            if (!percolationComponent.connected(bottomRow, fieldNumber(arrayRow, arrayCol)))
                percolationComponent.union(bottomRow, fieldNumber(arrayRow, arrayCol));
        }

        connectToNeighbour(arrayRow, arrayCol, arrayRow, arrayCol + 1);
        connectToNeighbour(arrayRow, arrayCol, arrayRow, arrayCol - 1);
        connectToNeighbour(arrayRow, arrayCol, arrayRow - 1, arrayCol);
        connectToNeighbour(arrayRow, arrayCol, arrayRow + 1, arrayCol);

        numberOfOpenSites++;
        fieldStatus[arrayRow][arrayCol] = true;
    }


    private int fieldNumber(int row, int col) {
        return (row * n) + col;
    }

    private void connectToNeighbour(int row1, int col1, int row2, int col2) {
        if (!validate(row2, col2)) {
            return;
        }

        if (isOpen(row2 + 1, col2 + 1)) {
            percolationComponent.union(fieldNumber(row1, col1), fieldNumber(row2, col2));
            fullStatus.union(fieldNumber(row1, col1), fieldNumber(row2, col2));
        }
    }

    private boolean validate(int row, int col) {
        return row >= 0 && row <= n - 1 && col >= 0 && col <= n - 1;
    }

    public boolean isOpen(int row, int col) {
        if (!validate(row - 1, col - 1)) throw new IllegalArgumentException();
        return fieldStatus[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        if (!validate(row - 1, col - 1)) throw new IllegalArgumentException();

        return fullStatus.connected(topRow, fieldNumber(row - 1, col - 1));
    }


    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        return percolationComponent.connected(topRow, bottomRow);
    }
}
