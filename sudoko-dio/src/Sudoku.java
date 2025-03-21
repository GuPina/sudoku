import java.util.Scanner;

public class Sudoku {
    private final int[][] board = new int[9][9];
    private final boolean[][] fixed = new boolean[9][9];

    public Sudoku(String[] args) {
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
        // Verificar linha
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == value) return false;
        }
        
        // Verificar coluna
        for (int i = 0; i < 9; i++) {
            if (board[i][col] == value) return false;
        }
        
        // Verificar subgrade 3x3
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
