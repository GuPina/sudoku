public class SudokuSolver {
    private SudokuBoard board;

    public SudokuSolver(SudokuBoard board) {
        this.board = board;
    }

    /**
     * Resolve o tabuleiro de Sudoku.
     *
     * @return true se o tabuleiro foi resolvido com sucesso, false caso contrário.
     */
    public boolean solve() {
        return solveBoard(0, 0);
    }

    /**
     * Método recursivo para resolver o tabuleiro a partir de uma célula específica.
     *
     * @param row A linha atual.
     * @param col A coluna atual.
     * @return true se o tabuleiro foi resolvido a partir desta célula, false caso contrário.
     */
    private boolean solveBoard(int row, int col) {
        // Se chegarmos ao final do tabuleiro, retornamos true (tabuleiro resolvido).
        if (row == 9) {
            return true;
        }

        // Se chegarmos ao final de uma linha, passamos para a próxima linha.
        if (col == 9) {
            return solveBoard(row + 1, 0);
        }

        // Se a célula já estiver preenchida, passamos para a próxima célula.
        if (board.getValue(row, col) != 0) {
            return solveBoard(row, col + 1);
        }

        // Tentamos preencher a célula com números de 1 a 9.
        for (int num = 1; num <= 9; num++) {
            if (isValidMove(row, col, num)) {
                // Se o número for válido, preenchemos a célula.
                board.setValue(row, col, num);

                // Tentamos resolver o restante do tabuleiro a partir da próxima célula.
                if (solveBoard(row, col + 1)) {
                    return true;
                }

                // Se não for possível resolver com esse número, revertemos a célula para 0.
                board.setValue(row, col, 0);
            }
        }

        // Se nenhum número for válido, retornamos false (backtracking).
        return false;
    }

    /**
     * Verifica se um número é válido para uma célula específica.
     *
     * @param row   A linha da célula.
     * @param col   A coluna da célula.
     * @param value O número a ser verificado.
     * @return true se o número for válido, false caso contrário.
     */
    public boolean isValidMove(int row, int col, int value) {
        int[][] grid = board.getBoard();

        // Verifica se o número já existe na linha.
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == value) {
                return false;
            }
        }

        // Verifica se o número já existe na coluna.
        for (int i = 0; i < 9; i++) {
            if (grid[i][col] == value) {
                return false;
            }
        }

        // Verifica se o número já existe na região 3x3.
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[startRow + i][startCol + j] == value) {
                    return false;
                }
            }
        }

        // Se o número for válido em todas as verificações, retornamos true.
        return true;
    }

    /**
     * Obtém o valor correto para uma célula específica.
     *
     * @param row A linha da célula.
     * @param col A coluna da célula.
     * @return O número correto para a célula ou 0 se nenhum número for válido.
     */
    public int getCorrectValue(int row, int col) {
        // Verifica se a célula já está preenchida.
        if (board.getValue(row, col) != 0) {
            return 0;
        }

        // Tenta preencher a célula com números de 1 a 9.
        for (int num = 1; num <= 9; num++) {
            if (isValidMove(row, col, num)) {
                return num;
            }
        }

        // Se nenhum número for válido, retorna 0.
        return 0;
    }
}