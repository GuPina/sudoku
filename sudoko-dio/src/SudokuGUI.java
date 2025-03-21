import java.awt.*;
import javax.swing.*;

public class SudokuGUI {
    private SudokuBoard board;
    private JFrame frame;
    private JTextField[][] fields = new JTextField[9][9];
    private SudokuSolver solver;
    private Timer timer;
    private int elapsedTime = 0; // Tempo decorrido em segundos
    private JLabel timerLabel;

    public SudokuGUI(SudokuBoard board) {
        this.board = board;
        this.solver = new SudokuSolver(board);
        createGUI();
        startTimer();
    }

    private void createGUI() {
        frame = new JFrame("Sudoku Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 450);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(9, 9));

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                fields[i][j] = new JTextField();
                if (board.getValue(i, j) != 0) {
                    fields[i][j].setText(String.valueOf(board.getValue(i, j)));
                    fields[i][j].setEditable(false);
                }
                fields[i][j].setHorizontalAlignment(JTextField.CENTER);
                panel.add(fields[i][j]);
            }
        }

        JButton checkButton = new JButton("Verificar");
        checkButton.addActionListener(e -> {
            SudokuValidator validator = new SudokuValidator(board);
            if (validator.isGameFinished()) {
                timer.stop();
                JOptionPane.showMessageDialog(frame, "Parabéns! Sudoku completo! Tempo: " + elapsedTime + " segundos.");
            } else {
                JOptionPane.showMessageDialog(frame, "Ainda há erros ou espaços vazios.");
            }
        });

        // Botão de Dica
        JButton hintButton = new JButton("Dica");
        hintButton.addActionListener(e -> provideHint());

        // Label do Cronômetro
        timerLabel = new JLabel("Tempo: 0 segundos");
        timerLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(checkButton);
        buttonPanel.add(hintButton);

        frame.add(timerLabel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void startTimer() {
        timer = new Timer(1000, e -> {
            elapsedTime++;
            timerLabel.setText("Tempo: " + elapsedTime + " segundos");
        });
        timer.start();
    }

    private void provideHint() {
        int[][] grid = board.getBoard();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] == 0) {
                    int correctValue = solver.getCorrectValue(i, j);
                    if (correctValue != 0) {
                        board.setValue(i, j, correctValue);
                        fields[i][j].setText(String.valueOf(correctValue));
                        fields[i][j].setEditable(false);
                        return;
                    }
                }
            }
        }
        JOptionPane.showMessageDialog(frame, "Não há mais dicas disponíveis!");
    }
}