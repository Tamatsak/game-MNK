package game;

import java.util.Arrays;

public class MNKBoard implements Board {


    private Cell[][] cells;
    private boolean[][] invalidCells;
    private Cell turn;
    private int k, rows, cols, moveCount;
    private MNKPosition p, pCopy;
    private boolean duble = false;

    public MNKBoard(int r, int k) {
        makeBoard(2 * r, 2 * r, k);
        for (int y = 0; y < rows; y++) {
            int a = getStart(y);
            for (int x = 0; x < a; x++) {
                invalidCells[y][x] = true;
                invalidCells[y][rows - 1 - x] = true;
            }
        }
    }

    private int getStart(int y) {
        int t = rows - 1 - y * 2;
        return (int) Math.ceil(rows - Math.sqrt(rows * rows - t * t)) / 2;
    }

    public MNKBoard(int m, int n, int k) {
        makeBoard(m, n, k);
    }

    private void makeBoard(int m, int n, int k) {
        this.cells = new Cell[m][n];
        this.k = k;
        this.rows = m;
        this.cols = n;
        this.moveCount = m * n;
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        this.invalidCells = new boolean[m][n];
        for (boolean[] row : invalidCells) {
            Arrays.fill(row, false);
        }
        turn = Cell.X;
        this.p = new MNKPosition(cells, turn, invalidCells);
        this.pCopy = new MNKPosition(cells, turn, invalidCells);
    }

    @Override
    public Position getPosition() {
        return pCopy;
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!p.isValid(move)) {
            return Result.LOSE;
        }
        moveCount--;
        cells[move.getRow()][move.getColumn()] = move.getValue();
        p.newPosition(move.getRow(), move.getColumn(), turn);
        pCopy.newPosition(move.getRow(), move.getColumn(), turn);
        if (moveCount == 0) {
            return Result.DRAW;
        }

        int inDiag1 = 0;
        int inDiag2 = 0;
        int inCol = 0;
        int inRow = 0;
        int leftSideCount = 0, rightSideCount = 0, rightDiagCount = 0, leftDiagCount = 0;

        for (int u = move.getRow() < k ? 0 : move.getRow() - k + 1;
             u < (Math.min(move.getRow() + k, rows)); u++) {
            if (!invalidCells[u][move.getRow()]) {
                inCol = cells[u][move.getColumn()] == turn ? inCol + 1 : 0;

                if (move.getColumn() - (move.getRow() - u) > -1
                        && move.getColumn() - (move.getRow() - u) < cols) {
                    inDiag1 = cells[u][move.getColumn() - (move.getRow() - u)] == turn ? inDiag1 + 1 : 0;
                }

                if (inCol == k || inDiag1 == k) {
                    return Result.WIN;
                }
                if (u == move.getRow()) {
                    leftSideCount = inCol - 1;
                    leftDiagCount = inDiag1 - 1;
                }
                if (u <= move.getRow() + 4 && u + 1 <= (Math.min(move.getRow() + k, rows))
                        && u > move.getRow()) {
                    if (cells[u][move.getColumn()] == turn &&
                            u - rightSideCount - 1 == move.getRow()) {
                        rightSideCount++;
                    }
                    if (move.getColumn() - (move.getRow() - u) > -1
                            && move.getColumn() - (move.getRow() - u) < cols &&
                            cells[u][move.getColumn() - (move.getRow() - u)] == turn &&
                            u - rightDiagCount - 1 == move.getRow()) {
                        rightDiagCount++;
                    }

                }
            }
        }
        if (leftSideCount < 4 && leftDiagCount < 4 &&
                rightSideCount < 4 && rightDiagCount < 4 && (
                leftDiagCount + rightDiagCount >= 3 || leftSideCount + rightSideCount >= 3)) {
            duble = true;
        }
        rightSideCount = 0;
        rightDiagCount = 0;


        for (int v = move.getColumn() < k ? 0 : move.getColumn() - k + 1;
             v < (Math.min(move.getColumn() + k, cols)); v++) {
            if (!invalidCells[move.getRow()][v]) {
                inRow = cells[move.getRow()][v] == turn ? inRow + 1 : 0;
                if (move.getColumn() + move.getRow() - v > -1
                        && move.getColumn() + move.getRow() - v < rows) {
                    inDiag2 += cells[move.getRow() + move.getColumn() - v][v] == turn ? inDiag2 + 1: 0;
                }

                if (inRow == k || inDiag2 == k) {
                    return Result.WIN;
                }

                if (v == move.getColumn()) {
                    leftSideCount = inRow - 1;
                    leftDiagCount = inDiag2 - 1;
                }
                if (v <= move.getColumn() + 4 && v + 1 <= (Math.min(move.getColumn() + k, cols))
                        && v > move.getColumn()) {
                    if (cells[move.getRow()][v] == turn &&
                            v - rightSideCount - 1 == move.getColumn()) {
                        rightSideCount++;
                    }
                    if (move.getColumn() + move.getRow() - v > -1
                            && move.getColumn() + move.getRow() - v < rows &&
                            cells[move.getRow() + move.getColumn() - v][v] == turn &&
                            v - rightDiagCount - 1 == move.getColumn()) {
                        rightDiagCount++;
                    }

                }
            }
        }
        if (leftSideCount < 4 && leftDiagCount < 4 &&
                rightSideCount < 4 && rightDiagCount < 4 && (
                leftDiagCount + rightDiagCount >= 3 || leftSideCount + rightSideCount >= 3)) {
            duble = true;
        }

        turn = turn == Cell.X && !duble ? Cell.O : Cell.X;
        duble = false;
        p.newPosition(move.getRow(), move.getColumn(), turn);
        pCopy.newPosition(move.getRow(), move.getColumn(), turn);
        return Result.UNKNOWN;
    }

    public Cell getCell(final int r, final int c) {
        return cells[r][c];
    }

}
