import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private static class Move implements Comparable<Move> {
        private final Move previous;
        private final Board board;
        private int numMoves = 0;
        private final int manhattan;

        public Move(Board board) {
            this.board = board;
            this.previous = null;
            this.manhattan = board.manhattan();
        }

        public Move(Board board, Move previous) {
            this.board = board;
            this.previous = previous;
            this.numMoves = previous.numMoves + 1;
            this.manhattan = board.manhattan();
        }

        public int compareTo(Move move) {
            return (this.manhattan - move.manhattan) + (this.numMoves - move.numMoves);
        }
    }

    private final Move lastMove;

    public Solver(Board initial) {

        if (initial == null) {
            throw new IllegalArgumentException();
        }

        MinPQ<Move> moves = new MinPQ<Move>();
        moves.insert(new Move(initial));

        MinPQ<Move> twinMoves = new MinPQ<Move>();
        twinMoves.insert(new Move(initial.twin()));

        Move goal = null;
        do {
            goal = expand(moves);
        } while (goal == null && expand(twinMoves) == null);
        lastMove = goal;
    }

    private Move expand(MinPQ<Move> moves) {
        if (moves.isEmpty()) return null;
        Move bestMove = moves.delMin();
        if (bestMove.board.isGoal()) return bestMove;

        for (Board neighbor : bestMove.board.neighbors()) {
            if (bestMove.previous == null
                    || !neighbor.equals(bestMove.previous.board)) {
                moves.insert(new Move(neighbor, bestMove));
            }
        }
        return null;
    }

    public boolean isSolvable() {
        return (lastMove != null);
    }

    public int moves() {
        return isSolvable() ? lastMove.numMoves : -1;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) return null;

        Move path = lastMove;
        Stack<Board> moves = new Stack<Board>();
        while (path != null) {
            moves.push(path.board);
            path = path.previous;
        }

        return moves;
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}