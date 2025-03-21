import java.awt.*;
import javax.swing.*;

public class SudokuGUI {
    private SudokuBoard board;
    private JFrame frame;
    private JTextField[][] fields = new JTextField[9][9];

    public SudokuGUI(SudokuBoard board) {
        this.board = board;
        createGUI();
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
                JOptionPane.showMessageDialog(frame, "Parabéns! Sudoku completo!");
            } else {
                JOptionPane.showMessageDialog(frame, "Ainda há erros ou espaços vazios.");
            }
        });

        frame.add(panel, BorderLayout.CENTER);
        frame.add(checkButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
