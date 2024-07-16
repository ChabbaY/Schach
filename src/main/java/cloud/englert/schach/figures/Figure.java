package cloud.englert.schach.figures;

/**
 * A chess figure.
 *
 * @author Linus Englert
 */
public abstract class Figure {
    private final boolean white;
    private final String name;
    private int movements = 0;

    /**
     * Creates a new chess figure.
     *
     * @param white true for a white, false for a black figure
     * @param name name of the figure
     */
    public Figure(final boolean white, final String name) {
        this.white = white;
        this.name = name;
    }

    public boolean isWhite() {
        return white;
    }

    public String getName() {
        return name;
    }

    public int getMovements() {
        return movements;
    }

    /**
     * Increments the movements by 1.
     */
    public void incrementMovements() {
        movements++;
    }

    /**
     * Gets the unicode image of a figure.
     *
     * @return the unicode
     */
    public abstract String getUnicode();

    /**
     * Gets all possible moves of the figure.
     *
     * @param placement figure placements
     * @param row row
     * @param column column
     * @return all possible moves
     */
    public abstract int[][] getPossibleMoves(Figure[][] placement, int row, int column);

    /**
     * True if the figure can move as specified.
     *
     * @param placement figure placements
     * @param fromRow source row
     * @param fromColumn source column
     * @param toRow target row
     * @param toColumn target column
     * @return true if move possible
     */
    public abstract boolean movePossible(Figure[][] placement, int fromRow, int fromColumn, int toRow, int toColumn);
}
