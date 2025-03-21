import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Sudoku {
    private final int[][] board = new int[9][9];
    private final boolean[][] fixed = new boolean[9][9];
    private static final Random random = new Random();
    private JFrame frame;
    private JTextField[][] fields = new JTextField[9][9];

    public Sudoku(String[] args) {
        if (args.length > 0) {
            loadFromArgs(args);
        } else {
            generateSudoku();
        }
        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("Sudoku Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 450);
        frame.setLayout(new BorderLayout());
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 9));
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                fields[i][j] = new JTextField();
                if (board[i][j] != 0) {
                    fields[i][j].setText(String.valueOf(board[i][j]));
                    fields[i][j].setEditable(false);
                }
                fields[i][j].setHorizontalAlignment(JTextField.CENTER);
                panel.add(fields[i][j]);
            }
        }
        
        JButton checkButton = new JButton("Verificar");
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isGameFinished()) {
                    JOptionPane.showMessageDialog(frame, "Parabéns! Você completou o Sudoku corretamente!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Ainda há erros ou espaços vazios!");
                }
            }
        });
        
        frame.add(panel, BorderLayout.CENTER);
        frame.add(checkButton, BorderLayout.SOUTH);
        frame.setVisible(true);
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
        removeNumbers(40);
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

    public boolean isBoardFull() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) return false;
            }
        }
        return true;
    }

    public boolean isSudokuValid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int num = board[i][j];
                if (num == 0) continue;
                board[i][j] = 0;
                if (!isValidMove(i, j, num)) {
                    board[i][j] = num;
                    return false;
                }
                board[i][j] = num;
            }
        }
        return true;
    }

    public boolean isGameFinished() {
        return isBoardFull() && isSudokuValid();
    }

    public static void main(String[] args) {
        new Sudoku(args);
    }
}
