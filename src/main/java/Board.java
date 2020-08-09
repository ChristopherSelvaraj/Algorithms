import java.util.LinkedList;
import java.util.List;

public class Board {

    private final int[][] tiles;
    private final int n;
    private final int manhattan;
    private final int hamming;

    public Board(int[][] tiles) {

        if (tiles == null) {
            throw new IllegalArgumentException();
        }
        this.n = tiles.length;
        this.tiles = copy(tiles);
        this.hamming = calculateHamming();
        this.manhattan = calculateManhattan();
    }

    private int[][] copy(int[][] paraTiles) {
        int[][] newTile = new int[paraTiles.length][paraTiles.length];
        for (int i = 0; i < paraTiles.length; i++) {
            System.arraycopy(paraTiles[i], 0, newTile[i], 0, paraTiles.length);
        }
        return newTile;
    }

    // string representation of this board
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                stringBuilder.append(this.tiles[i][j]).append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        return hamming;
    }

    private int calculateHamming() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int position = (i * n) + j + 1;
                if (tiles[i][j] != 0 && tiles[i][j] != position) {
                    count++;
                }
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }

    private int calculateManhattan() {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int position = tiles[i][j];
                if (position == 0)
                    continue;

                sum = sum + Math.abs(i - (position - 1) / n) + Math.abs(j - (position - 1) % n);
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int position = (i * n) + j + 1;
                if (tiles[i][j] != 0 && tiles[i][j] != position) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {

        if (y == null)
            return false;

        if (y == this) return true;

        if (y.getClass() != this.getClass())
            return false;

        Board c = (Board) y;
        if (c.dimension() != this.dimension()) {
            return false;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (c.tiles[i][j] != this.tiles[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int[] spaceLocation = getSpaceLocation();

        List<Board> neighbors = new LinkedList<>();

        if (isValid(spaceLocation[0] - 1, spaceLocation[1])) {
            neighbors.add(new Board(swap(spaceLocation[0], spaceLocation[1], spaceLocation[0] - 1, spaceLocation[1])));
        }

        if (isValid(spaceLocation[0], spaceLocation[1] + 1)) {
            neighbors.add(new Board(swap(spaceLocation[0], spaceLocation[1], spaceLocation[0], spaceLocation[1] + 1)));
        }

        if (isValid(spaceLocation[0] + 1, spaceLocation[1])) {
            neighbors.add(new Board(swap(spaceLocation[0], spaceLocation[1], spaceLocation[0] + 1, spaceLocation[1])));
        }

        if (isValid(spaceLocation[0], spaceLocation[1] - 1)) {
            neighbors.add(new Board(swap(spaceLocation[0], spaceLocation[1], spaceLocation[0], spaceLocation[1] - 1)));
        }

        return neighbors;
    }

    private int[][] swap(int x, int y, int x1, int y1) {
        int[][] newtile = copy(tiles);
        int temp = newtile[x][y];
        newtile[x][y] = newtile[x1][y1];
        newtile[x1][y1] = temp;
        return newtile;
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && y >= 0 && x < n && y < n;
    }

    private int[] getSpaceLocation() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        throw new RuntimeException();
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        for (int i = 0; i < tiles.length; i++)
            for (int j = 0; j < tiles.length - 1; j++)
                if (tiles[i][j] != 0 && tiles[i][j + 1] != 0)
                    return new Board(swap(i, j, i, j + 1));
        throw new RuntimeException();

    }

}