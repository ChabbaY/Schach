package cloud.englert.schach.figures;

import cloud.englert.schach.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * The king chess figure.
 *
 * @author Linus Englert
 */
public class King extends Figure {
    private final List<int[]> moves = new ArrayList<>();

    /**
     * Creates a new king chess figure.
     *
     * @param isWhite true if it is a white figure
     */
    public King(boolean isWhite) {
        super(isWhite, "König");
    }

    @Override
    public String getUnicode() {
        if (isWhite) {
            return "\u2654";
        }
        else {
            return "\u265a";
        }
    }

    @Override
    public int[][] getPossibleMoves(Figure[][] placement, int row, int column) {
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
        if (movements == 0) {
            if ((placement[row][7] != null) && (placement[row][7].movements == 0) && (placement[row][5] == null)
                    && (placement[row][6] == null)) { //kurz (Königsflügel)
                if ((!Main.isAttacked(row, 5)) && (!Main.isAttacked(row, 6))) {
                    int[] a = {row, 6, 2};
                    moves.add(a);
                }
            }
            if ((placement[row][0] != null) && (placement[row][0].movements == 0) && (placement[row][1] == null)
                    && (placement[row][2] == null) && (placement[row][3] == null)) { //lang (Damenflügel)
                if ((!Main.isAttacked(row, 2)) && (!Main.isAttacked(row, 3))) {
                    int[] a = {row, 2, 2};
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
    public boolean movePossible(Figure[][] placement, int fromRow, int fromColumn, int toRow, int toColumn) {
        //außerhalb der Grenzen
        if ((toRow > 7) || (toRow < 0) || (toColumn > 7) || (toColumn < 0)) {
            return false;
        }
        //eigene Figur im Weg
        if ((placement[toRow][toColumn] != null) && (placement[toRow][toColumn].isWhite == isWhite)) {
            return false;
        }

        if (Main.whiteTurn == isWhite) {
            return Main.moveValid(fromRow, fromColumn, toRow, toColumn);
        }
        else {
            return true;
        }
    }
}
