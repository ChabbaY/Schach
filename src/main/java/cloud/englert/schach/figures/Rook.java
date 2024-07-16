package cloud.englert.schach.figures;

import cloud.englert.schach.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * The rook chess figure.
 *
 * @author Linus Englert
 */
public class Rook extends Figure {
    private List<int[]> moves = new ArrayList<>();

    /**
     * Creates a new rook chess figure.
     *
     * @param isWhite true if it is a white figure
     */
    public Rook(boolean isWhite) {
        super(isWhite, "Turm");
    }

    @Override
    public String getUnicode() {
        if (isWhite) {
            return "\u2656";
        }
        else {
            return "\u265c";
        }
    }

    @Override
    public int[][] getPossibleMoves(Figure[][] placement, int row, int column) {
        moves.clear();
        if ((Main.whiteTurn == isWhite) && Main.kingInChess()) {
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
