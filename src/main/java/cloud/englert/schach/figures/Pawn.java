package cloud.englert.schach.figures;

import cloud.englert.schach.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * The pawn chess figure.
 *
 * @author Linus Englert
 */
public final class Pawn extends Figure {
    private final List<int[]> moves = new ArrayList<>();

    /**
     * Creates a new pawn chess figure.
     *
     * @param isWhite true if it is a white figure
     */
    public Pawn(final boolean isWhite) {
        super(isWhite, FigureNames.PAWN.name());
    }

    @Override
    public String getUnicode() {
        if (isWhite()) {
            return "\u2659";
        }
        else {
            return "\u265f";
        }
    }

    @Override
    public int[][] getPossibleMoves(final Figure[][] placement, final int row, final int column) {
        moves.clear();
        if ((Main.isWhiteTurn() == isWhite()) && Main.kingInChess()) {
            return new int[0][];
        }

        if (isWhite() && (row == 6)) { //Weiß Grundstellung
            if (movePossible(placement, row, column, row - 2, column) && (placement[row - 1][column] == null)) {
                int[] a = {row - 2, column, 0};
                moves.add(a);
            }
        }
        if (isWhite()) { //Weiß
            if (movePossible(placement, row, column, row - 1, column) && (placement[row - 1][column] == null)) {
                int[] a = {row - 1, column, 0};
                moves.add(a);
            }
            if ((row > 0) && (column > 0) && placement[row - 1][column - 1] != null) {
                if (movePossible(placement, row, column, row - 1, column - 1)) {
                    int[] a = {row - 1, column - 1, 0};
                    moves.add(a);
                }
            }
            if ((row > 0) && (column < 7) && placement[row - 1][column + 1] != null) {
                if (movePossible(placement, row, column, row - 1, column + 1)) {
                    int[] a = {row - 1, column + 1, 0};
                    moves.add(a);
                }
            }

            //en passant (1)
            if (row == 3) {
                int[] last = Main.lastMove();
                if ((last[0] == 1) && (last[2] == 3)
                        && placement[last[2]][last[3]].getName().equals(FigureNames.PAWN.name())) {
                    int[] a = {row - 1, last[3], 1};
                    moves.add(a);
                }
            }
        }
        if (!isWhite() && (row == 1)) { //Schwarz Grundstellung
            if (movePossible(placement, row, column, row + 2, column) && (placement[row + 1][column] == null)) {
                int[] a = {row + 2, column, 0};
                moves.add(a);
            }
        }
        if (!isWhite()) { //Schwarz
            if (movePossible(placement, row, column, row + 1, column) && (placement[row + 1][column] == null)) {
                int[] a = {row + 1, column, 0};
                moves.add(a);
            }
            if ((row < 7) && (column > 0) && placement[row + 1][column - 1] != null) {
                if (movePossible(placement, row, column, row + 1, column - 1)) {
                    int[] a = {row + 1, column - 1, 0};
                    moves.add(a);
                }
            }
            if ((row < 7) && (column < 7) && placement[row + 1][column + 1] != null) {
                if (movePossible(placement, row, column, row + 1, column + 1)) {
                    int[] a = {row + 1, column + 1, 0};
                    moves.add(a);
                }
            }

            //en passant (1)
            if (row == 4) {
                int[] last = Main.lastMove();
                if ((last[0] == 6) && (last[2] == 4)
                        && placement[last[2]][last[3]].getName().equals(FigureNames.PAWN.name())) {
                    int[] a = {row + 1, last[3], 1};
                    moves.add(a);
                }
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
