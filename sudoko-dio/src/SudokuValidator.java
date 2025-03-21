public class SudokuValidator {
    private SudokuBoard board;

    public SudokuValidator(SudokuBoard board) {
        this.board = board;
    }

    public boolean isGameFinished() {
        return isBoardFull() && isSudokuValid();
    }

    private boolean isBoardFull() {
        int[][] grid = board.getBoard();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] == 0) return false;
            }
        }
        return true;
    }

    private boolean isSudokuValid() {
        int[][] grid = board.getBoard();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int num = grid[i][j];
                if (num == 0) continue;
                grid[i][j] = 0;
                SudokuSolver solver = new SudokuSolver(board);
                if (!solver.isValidMove(i, j, num)) {
                    grid[i][j] = num;
                    return false;
                }
                grid[i][j] = num;
            }
        }
        return true;
    }
}
