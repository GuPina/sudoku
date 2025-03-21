import java.util.Random;

public class SudokuGenerator {
    private SudokuBoard board;
    private static final Random random = new Random();

    public SudokuGenerator(SudokuBoard board) {
        this.board = board;
    }

    public void generate() {
        SudokuSolver solver = new SudokuSolver(board);
        solver.solve(); // Cria um tabuleiro solucionado
        removeNumbers(40); // Remove números para criar um desafio
    }

    private void removeNumbers(int count) {
        int[][] grid = board.getBoard();
        while (count > 0) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);
            if (grid[row][col] != 0) {
                board.setValue(row, col, 0);
                board.setFixed(row, col, false);
                count--;
            }
        }
    }
}
