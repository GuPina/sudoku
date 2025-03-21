import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuGame {
    public static void main(String[] args) {
        // Cria o menu inicial
        String[] options = {"Iniciar", "Sair"};
        int choice = JOptionPane.showOptionDialog(null, "Bem-vindo ao Sudoku!", "Menu Inicial",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) { // Se o usuário escolher "Iniciar"
            // Cria o menu de seleção de dificuldade
            String[] levels = {"Fácil", "Médio", "Difícil"};
            int levelChoice = JOptionPane.showOptionDialog(null, "Escolha o nível de dificuldade:", "Nível de Dificuldade",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, levels, levels[0]);

            int removeCount = 0;
            switch (levelChoice) {
                case 0:
                    removeCount = 30; // Fácil: remove 30 números
                    break;
                case 1:
                    removeCount = 40; // Médio: remove 40 números
                    break;
                case 2:
                    removeCount = 50; // Difícil: remove 50 números
                    break;
                default:
                    System.exit(0); // Sai se o usuário fechar a janela
            }

            // Inicializa o jogo com o nível de dificuldade escolhido
            SudokuBoard board = new SudokuBoard();
            SudokuGenerator generator = new SudokuGenerator(board);
            generator.generate(removeCount);

            new SudokuGUI(board);
        } else {
            System.exit(0); // Sai se o usuário escolher "Sair"
        }
    }
}