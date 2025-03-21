public class SudokuBoard {
    private int[][] board = new int[9][9];
    private boolean[][] fixed = new boolean[9][9];

    public int getValue(int row, int col) {
        return board[row][col];
    }

    public void setValue(int row, int col, int value) {
        if (!fixed[row][col]) {
            board[row][col] = value;
        }
    }

    public boolean isFixed(int row, int col) {
        return fixed[row][col];
    }

    public int[][] getBoard() {
        return board;
    }

    public void setFixed(int row, int col, boolean value) {
        fixed[row][col] = value;
    }
}
