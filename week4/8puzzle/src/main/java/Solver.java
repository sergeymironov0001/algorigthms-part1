import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

public class Solver {
    private final Collection<Board> solution;
    private final int moves;

    /**
     * Find a solution to the initial node (using the A* algorithm)
     *
     * @param initial
     */
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Initial node should be not null");
        solution = solve(initial);
        moves = solution != null ? solution.size() - 1 : -1;
    }

    private static Collection<Board> solve(Board initial) {
        MinPQ<MinPQItem> originalMinPQ = new MinPQ<>(new ManhattanComparator());
        originalMinPQ.insert(new MinPQItem(0, initial, null));

        Board initialTwin = initial.twin();
        MinPQ<MinPQItem> twinMinPQ = new MinPQ<>(new ManhattanComparator());
        twinMinPQ.insert(new MinPQItem(0, initialTwin, null));

        MinPQ<MinPQItem> minPQ;
        Board previousNode;
        MinPQItem item;
        for (int i = 0; !originalMinPQ.isEmpty() && !twinMinPQ.isEmpty(); i++) {
            minPQ = originalMinPQ;
            if (i % 2 != 0) minPQ = twinMinPQ;

            item = minPQ.delMin();
            Board node = item.getNode();
            previousNode = item.getPreviousItem() != null ? item.getPreviousItem().getNode() : node;
            if (node.isGoal()) {
                if (i % 2 != 0) return null;

                List<Board> solution = new ArrayList<>();
                while (item != null) {
                    solution.add(item.getNode());
                    item = item.getPreviousItem();
                }
                Collections.reverse(solution);
                return solution;
            }

            for (Board neighborNode : node.neighbors()) {
                if (!previousNode.equals(neighborNode)) {
                    minPQ.insert(new MinPQItem(item.getMoves() + 1, neighborNode, item));
                }
            }
        }
        return null;
    }

    /**
     * Is the initial node solvable?
     *
     * @return
     */
    public boolean isSolvable() {
        return solution != null;
    }

    /**
     * Min number of moves to solve initial node; -1 if unsolvable
     *
     * @return
     */
    public int moves() {
        return moves;
    }

    /**
     * Sequence of boards in a shortest solution; null if unsolvable
     *
     * @return
     */
    public Iterable<Board> solution() {
        return solution;
    }

//    private static class HammingComparator implements Comparator<MinPQItem> {
//        @Override
//        public int compare(MinPQItem o1, MinPQItem o2) {
//            return o1.getHammingPriority() - o2.getHammingPriority();
//        }
//    }

    private static class ManhattanComparator implements Comparator<MinPQItem> {
        @Override
        public int compare(MinPQItem o1, MinPQItem o2) {
            return o1.getManhattanPriority() - o2.getManhattanPriority();
        }
    }

    private static class MinPQItem {
        private final int moves;
        private final Board node;
//        private final int hammingPriority;
        private final int manhattanPriority;
        private final MinPQItem previousItem;

        public MinPQItem(int moves, Board node, MinPQItem previousItem) {
            this.moves = moves;
            this.node = node;
//            this.hammingPriority = moves + node.hamming();
            this.manhattanPriority = moves + node.manhattan();
            this.previousItem = previousItem;
        }

        public int getMoves() {
            return moves;
        }

        public Board getNode() {
            return node;
        }

//        public int getHammingPriority() {
//            return hammingPriority;
//        }

        public int getManhattanPriority() {
            return manhattanPriority;
        }

        public MinPQItem getPreviousItem() {
            return previousItem;
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

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