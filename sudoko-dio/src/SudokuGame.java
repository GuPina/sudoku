public class SudokuGame {
    public static void main(String[] args) {
        SudokuBoard board = new SudokuBoard();
        SudokuGenerator generator = new SudokuGenerator(board);
        generator.generate();
        
        new SudokuGUI(board);
    }
}