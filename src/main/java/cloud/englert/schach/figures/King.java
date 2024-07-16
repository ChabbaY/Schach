package cloud.englert.schach.figures;

import cloud.englert.schach.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * The king chess figure.
 *
 * @author Linus Englert
 */
public final class King extends Figure {
    private final List<int[]> moves = new ArrayList<>();

    /**
     * Creates a new king chess figure.
     *
     * @param isWhite true if it is a white figure
     */
    public King(final boolean isWhite) {
        super(isWhite, FigureNames.KING.name());
    }

    @Override
    public String getUnicode() {
        if (isWhite()) {
            return "\u2654";
        }
        else {
            return "\u265a";
        }
    }

    @Override
    public int[][] getPossibleMoves(final Figure[][] placement, final int row, final int column) {
        moves.clear();

        if (movePossible(placement, row, column, row + 1, column)) {
            int[] a = {row + 1, column, 0};
            moves.add(a);
        }
        if (movePossible(placement, row, column, row + 1, column + 1)) {
            int[] a = {row + 1, column + 1, 0};
            moves.add(a);
        }
        if (movePossible(placement, row, column, row + 1, column - 1)) {
            int[] a = {row + 1, column - 1, 0};
            moves.add(a);
        }
        if (movePossible(placement, row, column, row - 1, column)) {
            int[] a = {row - 1, column, 0};
            moves.add(a);
        }
        if (movePossible(placement, row, column, row - 1, column + 1)) {
            int[] a = {row - 1, column + 1, 0};
            moves.add(a);
        }
        if (movePossible(placement, row, column, row - 1, column - 1)) {
            int[] a = {row - 1, column - 1, 0};
            moves.add(a);
        }
        if (movePossible(placement, row, column, row, column + 1)) {
            int[] a = {row, column + 1, 0};
            moves.add(a);
        }
        if (movePossible(placement, row, column, row, column - 1)) {
            int[] a = {row, column - 1, 0};
            moves.add(a);
        }

        //Rochade (2)
        if (getMovements() == 0) {
            if ((placement[row][7] != null)
                    && (placement[row][7].getMovements() == 0)
                    && (placement[row][5] == null)
                    && (placement[row][6] == null)
                    && (!Main.isAttacked(row, 5))
                    && (!Main.isAttacked(row, 6))) { //kurz (Königsflügel)
                int[] a = {row, 6, 2};
                moves.add(a);
            }
            if ((placement[row][0] != null)
                    && (placement[row][0].getMovements() == 0)
                    && (placement[row][1] == null)
                    && (placement[row][2] == null)
                    && (placement[row][3] == null)
                    && (!Main.isAttacked(row, 2))
                    && (!Main.isAttacked(row, 3))) { //lang (Damenflügel)
                int[] a = {row, 2, 2};
                moves.add(a);
            }
        }

        int[][] erg = new int[moves.size()][3];
        for (int i = 0; i < erg.length; i++) {
            erg [i][0] = moves.get(i)[0];
            erg [i][1] = moves.get(i)[1];
            erg [i][2] = moves.get(i)[2];
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
