public class SudokuSolver {
    private SudokuBoard board;

    public SudokuSolver(SudokuBoard board) {
        this.board = board;
    }

    public boolean solve() {
        return solveBoard(0, 0);
    }

    private boolean solveBoard(int row, int col) {
        if (row == 9) return true;
        if (col == 9) return solveBoard(row + 1, 0);
        if (board.getValue(row, col) != 0) return solveBoard(row, col + 1);

        for (int num = 1; num <= 9; num++) {
            if (isValidMove(row, col, num)) {
                board.setValue(row, col, num);
                if (solveBoard(row, col + 1)) return true;
                board.setValue(row, col, 0);
            }
        }
        return false;
    }

    private boolean isValidMove(int row, int col, int value) {
        int[][] grid = board.getBoard();
        
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == value || grid[i][col] == value) return false;
        }

        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[startRow + i][startCol + j] == value) return false;
            }
        }

        return true;
    }
}
