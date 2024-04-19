package game;

import java.util.Map;

public class MNKPosition implements Position {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.'
    );
    private Cell[][] f;
    private Cell turn;
    private final int rows;
    private final int cols;
    private final boolean[][] invalidCells;

    MNKPosition(Cell[][] f, Cell turn, boolean[][] invalidCells) {
        this.invalidCells = invalidCells;
        this.f = f;
        this.turn = turn;
        this.rows = f.length;
        this.cols = f[0].length;
    }

    public void newPosition(int r, int c, Cell turn) {
        this.f[r][c] = this.turn;
        this.turn = turn;
    }

    @Override
    public boolean isValid(final Move move) {
//        System.err.println(0 <= move.getRow() );
//        System.err.println(move.getRow() < this.rows);
//        System.err.println(0 <= move.getColumn());
//        System.err.println(move.getColumn() < this.cols);
//        System.err.println(f[move.getRow()][move.getColumn()] == Cell.E);
//        System.err.println((turn == move.getValue()) + " " + turn + " get val: " + move.getValue());
        return 0 <= move.getRow() && move.getRow() < this.rows
                && 0 <= move.getColumn() && move.getColumn() < this.cols
                && f[move.getRow()][move.getColumn()] == Cell.E
                && turn == move.getValue() && !invalidCells[move.getRow()][move.getColumn()];
    }

    @Override
    public Cell getCell(int r, int c) {
        return f[r][c];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("   ");
        for (int c = 0; c < this.cols; c++) {
            sb.append(c);
            if (c < 10) {
                sb.append("  ");
            } else {
                sb.append(" ");
            }
        }
        for (int r = 0; r < this.rows; r++) {
            sb.append("\n");
            sb.append(r).append(r < 10 ? "  " : " ");
            for (int c = 0; c < this.cols; c++) {

                sb.append(this.invalidCells[r][c] ? "/" : SYMBOLS.get(f[r][c])).append("  ");
            }
        }
        return sb.toString();
    }
}
