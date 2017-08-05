import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private final int[][] blocks;
    private final int dimension;
    private final int hamming;
    private final int manhattan;

    /**
     * Construct a board from an n-by-n array of blocks (where blocks[i][j] = block in row i, column j)
     *
     * @param blocks
     */
    public Board(int[][] blocks) {
        if (blocks == null) throw new IllegalArgumentException("Blocks should be not null");
        this.blocks = blocks;
        this.dimension = blocks.length;
        this.hamming = hamming(blocks);
        this.manhattan = manhattan(blocks);
    }
    //

    /**
     * Board dimension n
     *
     * @return
     */
    public int dimension() {
        return dimension;
    }

    /**
     * Number of blocks out of place
     *
     * @return
     */
    public int hamming() {
        return hamming;
    }

    private static int hamming(int[][] board) {
        int dimension = board.length;
        int outOfPlaceBlocksCount = 0;
        for (int i = 0; i < dimension; i++) {
            int rowMultiplier = i * dimension;
            for (int j = 0; j < dimension; j++) {
                int value = board[i][j];
                if (value != 0 && value != rowMultiplier + j + 1) outOfPlaceBlocksCount++;
            }
        }
        return outOfPlaceBlocksCount;
    }

    /**
     * Sum of Manhattan distances between blocks and goal
     *
     * @return
     */
    public int manhattan() {
        return manhattan;
    }

    private static int manhattan(int[][] board) {
        int dimension = board.length;
        int distance = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int value = board[i][j];
                if (value != 0) {
                    distance += Math.abs(((value - 1) / dimension) - i);
                    distance += Math.abs(((value - 1) % dimension) - j);
                }
            }
        }

        return distance;
    }

    /**
     * Is this board the goal board?
     *
     * @return
     */
    public boolean isGoal() {
        return hamming == 0;
    }

    /**
     * A board that is obtained by exchanging any pair of blocks
     *
     * @return
     */
    public Board twin() {
        int[][] twinBlocks = copyMatrix(blocks);
        if (twinBlocks[0][0] != 0) {
            if (twinBlocks[0][1] != 0) swap(twinBlocks, 0, 0, 0, 1);
            else if (twinBlocks[1][0] != 0) swap(twinBlocks, 0, 0, 1, 0);
            else if (twinBlocks[1][1] != 0) swap(twinBlocks, 0, 0, 1, 1);
        } else swap(twinBlocks, 0, 1, 1, 0);

        return new Board(twinBlocks);
    }

    private static boolean swap(int[][] blocks, int i, int j, int x, int y) {
        int tmp = blocks[i][j];
        blocks[i][j] = blocks[x][y];
        blocks[x][y] = tmp;
        return true;
    }

    private static int[][] copyMatrix(int[][] matrix) {
        int dimension = matrix.length;
        int[][] copy = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            copy[i] = Arrays.copyOf(matrix[i], dimension);
        }
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;

        Board board = (Board) o;

        if (dimension != board.dimension) return false;
        for (int i = 0; i < dimension; i++) {
            if (!Arrays.equals(blocks[i], board.blocks[i])) return false;
        }
        return true;
    }

    /**
     * All neighboring boards
     *
     * @return
     */
    public Iterable<Board> neighbors() {
        List<Board> boards = new ArrayList<>();
        int x = -1;
        int y = -1;
        for (int i = 0; i < dimension && x == -1; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] == 0) {
                    x = i;
                    y = j;
                    break;
                }
            }
        }
        if (x != 0) {
            int[][] neighborBlocks = copyMatrix(blocks);
            swap(neighborBlocks, x, y, x - 1, y);
            boards.add(new Board(neighborBlocks));
        }
        if (x != dimension - 1) {
            int[][] neighborBlocks = copyMatrix(blocks);
            swap(neighborBlocks, x, y, x + 1, y);
            boards.add(new Board(neighborBlocks));
        }
        if (y != 0) {
            int[][] neighborBlocks = copyMatrix(blocks);
            swap(neighborBlocks, x, y, x, y - 1);
            boards.add(new Board(neighborBlocks));
        }
        if (y != dimension - 1) {
            int[][] neighborBlocks = copyMatrix(blocks);
            swap(neighborBlocks, x, y, x, y + 1);
            boards.add(new Board(neighborBlocks));
        }

        return boards;
    }

    /**
     * String representation of this board
     *
     * @return
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dimension).append(System.lineSeparator());

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                stringBuilder.append(blocks[i][j]).append(" ");
            }
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        int[][] blocks = {
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}};

        Board board = new Board(blocks);
        System.out.println(board);
        System.out.printf("Hamming: %d\n", board.hamming());
        System.out.printf("Manhattan: %d\n", board.manhattan());
        System.out.printf("Twin:\n%s\n", board.twin());

        System.out.println(board.neighbors());
    }
}