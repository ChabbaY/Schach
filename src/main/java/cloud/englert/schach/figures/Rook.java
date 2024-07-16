package cloud.englert.schach.figures;

import cloud.englert.schach.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * The rook chess figure.
 *
 * @author Linus Englert
 */
public final class Rook extends Figure {
    private final List<int[]> moves = new ArrayList<>();

    /**
     * Creates a new rook chess figure.
     *
     * @param isWhite true if it is a white figure
     */
    public Rook(final boolean isWhite) {
        super(isWhite, FigureNames.ROOK.name());
    }

    @Override
    public String getUnicode() {
        if (isWhite()) {
            return "\u2656";
        }
        else {
            return "\u265c";
        }
    }

    @Override
    public int[][] getPossibleMoves(final Figure[][] placement, final int row, final int column) {
        moves.clear();
        if ((Main.isWhiteTurn() == isWhite()) && Main.kingInChess()) {
            return new int[0][];
        }

        for (int i = 1; i < 8; i++) {
            if (movePossible(placement, row, column, row + i, column)) {
                int[] a = {row + i, column};
                moves.add(a);
            }
            else {
                break;
            }
            if (placement[row + i][column] != null) {
                break;
            }
        }
        for (int i = 1; i < 8; i++) {
            if (movePossible(placement, row, column, row, column + i)) {
                int[] a = {row, column + i};
                moves.add(a);
            }
            else {
                break;
            }
            if (placement[row][column + i] != null) {
                break;
            }
        }
        for (int i = 1; i < 8; i++) {
            if (movePossible(placement, row, column, row - i, column)) {
                int[] a = {row - i, column};
                moves.add(a);
            }
            else {
                break;
            }
            if (placement[row - i][column] != null) {
                break;
            }
        }
        for (int i = 1; i < 8; i++) {
            if (movePossible(placement, row, column, row, column - i)) {
                int[] a = {row, column - i};
                moves.add(a);
            }
            else {
                break;
            }
            if (placement[row][column - i] != null) {
                break;
            }
        }

        int[][] erg = new int[moves.size()][2];
        for (int i = 0; i < erg.length; i++) {
            erg [i][0] = moves.get(i)[0];
            erg [i][1] = moves.get(i)[1];
        }
        return erg;
    }

    @Override
    public boolean movePossible(final Figure[][] placement, final int fromRow, final int fromColumn,
                                final int toRow, final int toColumn) {
        //außerhalb der Grenzen
        if ((toRow > 7) || (toRow < 0) || (toColumn > 7) || (toColumn < 0)) {
            return false;
        }
        //eigene Figur im Weg
        if ((placement[toRow][toColumn] != null) && (placement[toRow][toColumn].isWhite() == isWhite())) {
            return false;
        }

        return Main.isWhiteTurn() != isWhite() || Main.moveValid(fromRow, fromColumn, toRow, toColumn);
    }
}
