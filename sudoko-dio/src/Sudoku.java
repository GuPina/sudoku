import java.util.Random;
import java.util.Scanner;

public class Sudoku {
    private final int[][] board = new int[9][9];
    private final boolean[][] fixed = new boolean[9][9];
    private static final Random random = new Random();

    public Sudoku(String[] args) {
        if (args.length > 0) {
            loadFromArgs(args);
        } else {
            generateSudoku();
        }
    }

    private void loadFromArgs(String[] args) {
        for (String arg : args) {
            String[] parts = arg.split(";");
            String[] pos = parts[0].split(",");
            int row = Integer.parseInt(pos[1]);
            int col = Integer.parseInt(pos[0]);
            int value = Integer.parseInt(parts[1]);
            boolean isFixed = Boolean.parseBoolean(parts[2]);
            
            board[row][col] = value;
            fixed[row][col] = isFixed;
        }
    }

    private void generateSudoku() {
        fillDiagonal();
        solveBoard(0, 0);
        removeNumbers(40); // Define a dificuldade (40 removidos)
    }

    private void fillDiagonal() {
        for (int i = 0; i < 9; i += 3) {
            fillBox(i, i);
        }
    }

    private void fillBox(int row, int col) {
        boolean[] used = new boolean[10];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int num;
                do {
                    num = random.nextInt(9) + 1;
                } while (used[num]);
                used[num] = true;
                board[row + i][col + j] = num;
            }
        }
    }

    private boolean solveBoard(int row, int col) {
        if (row == 9) return true;
        if (col == 9) return solveBoard(row + 1, 0);
        if (board[row][col] != 0) return solveBoard(row, col + 1);
        
        for (int num = 1; num <= 9; num++) {
            if (isValidMove(row, col, num)) {
                board[row][col] = num;
                if (solveBoard(row, col + 1)) return true;
                board[row][col] = 0;
            }
        }
        return false;
    }

    private void removeNumbers(int count) {
        while (count > 0) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);
            if (board[row][col] != 0) {
                board[row][col] = 0;
                fixed[row][col] = false;
                count--;
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0) System.out.println("---------------------");
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0 && j != 0) System.out.print(" | ");
                System.out.print((board[i][j] == 0 ? "." : board[i][j]) + " ");
            }
            System.out.println();
        }
    }

    public boolean isValidMove(int row, int col, int value) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == value || board[i][col] == value) return false;
        }
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[startRow + i][startCol + j] == value) return false;
            }
        }
        return true;
    }

    public boolean setNumber(int row, int col, int value) {
        if (fixed[row][col]) {
            System.out.println("Não é possível alterar este número fixo!");
            return false;
        }
        if (!isValidMove(row, col, value)) {
            System.out.println("Movimento inválido! O número já existe na linha, coluna ou subgrade.");
            return false;
        }
        board[row][col] = value;
        return true;
    }

    public static void main(String[] args) {
        Sudoku game = new Sudoku(args);
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            game.printBoard();
            System.out.println("Digite a linha, coluna e valor (ex: 2 3 5) ou -1 para sair:");
            int row = scanner.nextInt();
            if (row == -1) break;
            int col = scanner.nextInt();
            int value = scanner.nextInt();
            game.setNumber(row, col, value);
        }
        scanner.close();
    }
}
