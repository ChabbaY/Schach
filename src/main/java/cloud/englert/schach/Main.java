package cloud.englert.schach;

import cloud.englert.schach.figures.Bishop;
import cloud.englert.schach.figures.Figure;
import cloud.englert.schach.figures.FigureNames;
import cloud.englert.schach.figures.King;
import cloud.englert.schach.figures.Knight;
import cloud.englert.schach.figures.Pawn;
import cloud.englert.schach.figures.Queen;
import cloud.englert.schach.figures.Rook;
import cloud.englert.schach.io.Input;
import cloud.englert.schach.io.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Chess game.
 *
 * @author Linus Englert
 */
public final class Main {
    private static final Figure[][] PLACEMENT = new Figure[8][8];
    private static Window window;

    private static boolean whiteTurn = true;
    private static boolean playing = true;
    private static boolean figureSelected = false;
    private static int selectedRow = 0;
    private static int selectedColumn = 0;

    private static final List<int[]> MOVES = new ArrayList<>();

    private static final Logger LOGGER = Logger.getLogger(Main.class.getSimpleName());

    private Main() { }

    /**
     * Entry point of the program.
     *
     * @param args ignored optional arguments
     */
    public static void main(final String[] args) {
        startPlacement();
        window = new Window();
        window.paint(PLACEMENT);

        LOGGER.info("Weiß am Zug:");
        LOGGER.info(countPossibleMoves() + " Züge möglich");
    }

    public static boolean isWhiteTurn() {
        return whiteTurn;
    }

    /**
     * Places the figures to start a game.
     */
    public static void startPlacement() {
        PLACEMENT[0][0] = new Rook(false);
        PLACEMENT[0][1] = new Knight(false);
        PLACEMENT[0][2] = new Bishop(false);
        PLACEMENT[0][3] = new Queen(false);
        PLACEMENT[0][4] = new King(false);
        PLACEMENT[0][5] = new Bishop(false);
        PLACEMENT[0][6] = new Knight(false);
        PLACEMENT[0][7] = new Rook(false);

        PLACEMENT[1][0] = new Pawn(false);
        PLACEMENT[1][1] = new Pawn(false);
        PLACEMENT[1][2] = new Pawn(false);
        PLACEMENT[1][3] = new Pawn(false);
        PLACEMENT[1][4] = new Pawn(false);
        PLACEMENT[1][5] = new Pawn(false);
        PLACEMENT[1][6] = new Pawn(false);
        PLACEMENT[1][7] = new Pawn(false);

        PLACEMENT[6][0] = new Pawn(true);
        PLACEMENT[6][1] = new Pawn(true);
        PLACEMENT[6][2] = new Pawn(true);
        PLACEMENT[6][3] = new Pawn(true);
        PLACEMENT[6][4] = new Pawn(true);
        PLACEMENT[6][5] = new Pawn(true);
        PLACEMENT[6][6] = new Pawn(true);
        PLACEMENT[6][7] = new Pawn(true);

        PLACEMENT[7][0] = new Rook(true);
        PLACEMENT[7][1] = new Knight(true);
        PLACEMENT[7][2] = new Bishop(true);
        PLACEMENT[7][3] = new Queen(true);
        PLACEMENT[7][4] = new King(true);
        PLACEMENT[7][5] = new Bishop(true);
        PLACEMENT[7][6] = new Knight(true);
        PLACEMENT[7][7] = new Rook(true);
    }

    /**
     * Finishes a turn, if not already game over it's the other player's turn.
     */
    public static void finishTurn() {
        whiteTurn = !whiteTurn;
        if (whiteTurn) {
            LOGGER.info("\nWeiß am Zug:");
        }
        else {
            LOGGER.info("\nSchwarz am Zug:");
        }

        boolean schach = kingInChess();
        if (schach) {
            LOGGER.info("König im Schach!");
        }

        int nrMoves = countPossibleMoves();
        LOGGER.info(nrMoves + " Züge möglich");

        if ((nrMoves == 0) && schach) {
            LOGGER.info("Schachmatt!");
            LOGGER.info(whiteTurn ? "Schwarz gewinnt!" : "Weiß gewinnt!");

            playing = false;
        }
        else if (nrMoves == 0) {
            LOGGER.info("Patt!");

            playing = false;
        }
    }

    /**
     * Selects a figure and highlights possible moves of applicable.
     *
     * @param row selected row
     * @param column selected column
     */
    public static void click(final int row, final int column) {
        if (!playing) {
            return;
        }

        if (PLACEMENT[row][column] == null) {
            LOGGER.info("clicked on: " + row + " " + column);

            if (figureSelected) { //Zug auf leeres Feld
                move(selectedRow, selectedColumn, row, column);
                figureSelected = false;
            }
        }
        else {
            LOGGER.info("clicked on: " + row + " " + column + " " + PLACEMENT[row][column].getName());

            //Farbe am Zug
            if (whiteTurn == PLACEMENT[row][column].isWhite()) {
                if (countPossibleMoves(row, column) > 0) {
                    LOGGER.info("Ziel wählen");
                    figureSelected = true;
                    selectedRow = row;
                    selectedColumn = column;
                    window.colorBoard(PLACEMENT[row][column].getPossibleMoves(PLACEMENT, row, column));
                }
                else {
                    LOGGER.info("andere Figur wählen");
                    figureSelected = false;
                    window.colorBoard();
                }
            }
            else if (figureSelected) { //Angriff einer gegnerischen Figur
                move(selectedRow, selectedColumn, row, column);
                figureSelected = false;
            }
        }
    }

    /**
     * Moves the selected Figure if possible.
     *
     * @param fromRow source row
     * @param fromColumn source column
     * @param toRow target row
     * @param toColumn target column
     */
    public static void move(final int fromRow, final int fromColumn, final int toRow, final int toColumn) {
        int[][] moves = PLACEMENT[fromRow][fromColumn].getPossibleMoves(PLACEMENT, fromRow, fromColumn);
        for (int[] move : moves) {
            //Koordinaten in möglichen Zügen enthalten
            if ((move[0] == toRow) && (move[1] == toColumn)) {
                LOGGER.info("Zug von " + PLACEMENT[fromRow][fromColumn].getName() + " nach " + toRow + " " + toColumn);
                PLACEMENT[fromRow][fromColumn].incrementMovements();
                int[] zug = {fromRow, fromColumn, toRow, toColumn};
                MOVES.add(zug);

                PLACEMENT[toRow][toColumn] = PLACEMENT[fromRow][fromColumn];
                PLACEMENT[fromRow][fromColumn] = null;

                if (move.length > 2) {
                    if (move[2] == 1) { //en passant
                        LOGGER.info("en passant!");
                        PLACEMENT[fromRow][toColumn] = null;
                    }
                    else if (move[2] == 2) { //Rochade
                        LOGGER.info("Rochade!");
                        if (toColumn > fromColumn) { //kurz
                            PLACEMENT[toRow][5] = PLACEMENT[toRow][7];
                            PLACEMENT[toRow][7] = null;
                        }
                        else { //lang
                            PLACEMENT[toRow][3] = PLACEMENT[toRow][0];
                            PLACEMENT[toRow][0] = null;
                        }
                    }
                }

                //Umwandlung
                if (PLACEMENT[toRow][toColumn].getName().equals(FigureNames.PAWN.name()) && ((toRow == 0) || (toRow == 7))) {
                    LOGGER.info("Umwandlung!");

                    FigureNames figure;
                    do {
                        figure = Input.input();
                    }
                    while (figure == null);

                    switch (figure) {
                        case QUEEN:
                            PLACEMENT[toRow][toColumn] = new Queen(whiteTurn);
                            break;
                        case ROOK:
                            PLACEMENT[toRow][toColumn] = new Rook(whiteTurn);
                            break;
                        case BISHOP:
                            PLACEMENT[toRow][toColumn] = new Bishop(whiteTurn);
                            break;
                        case KNIGHT:
                            PLACEMENT[toRow][toColumn] = new Knight(whiteTurn);
                            break;
                    }
                }

                window.paint(PLACEMENT);

                finishTurn();
            }
        }
    }

    /**
     * Determines if a move is valid (king is not in chess afterward).
     *
     * @param fromRow source row
     * @param fromColumn source column
     * @param toRow target row
     * @param toColumn target column
     * @return true if valid
     */
    public static boolean moveValid(final int fromRow, final int fromColumn, final int toRow, final int toColumn) {
        Figure fromFigure = PLACEMENT[fromRow][fromColumn];
        Figure toFigure = PLACEMENT[toRow][toColumn];

        PLACEMENT[toRow][toColumn] = PLACEMENT[fromRow][fromColumn];
        PLACEMENT[fromRow][fromColumn] = null;

        if (kingInChess()) {
            PLACEMENT[fromRow][fromColumn] = fromFigure;
            PLACEMENT[toRow][toColumn] = toFigure;
            return false;
        }
        else {
            PLACEMENT[fromRow][fromColumn] = fromFigure;
            PLACEMENT[toRow][toColumn] = toFigure;
            return true;
        }
    }

    /**
     * True if the opponent can reach a field with its next move.
     *
     * @param rowX row to check
     * @param columnX column to check
     * @return true if it can be attacked
     */
    public static boolean isAttacked(final int rowX, final int columnX) {
        for (int zeile = 0; zeile < 8; zeile++) {
            for (int spalte = 0; spalte < 8; spalte++) {
                //ist gegnerischer Stein
                if ((PLACEMENT[zeile][spalte] != null) && (PLACEMENT[zeile][spalte].isWhite() != whiteTurn)) {
                    int[][] moves = PLACEMENT[zeile][spalte].getPossibleMoves(PLACEMENT, zeile, spalte);
                    for (int[] move : moves) {
                        //Koordinaten in möglichen Zügen enthalten
                        if ((move[0] == rowX) && (move[1] == columnX)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the own king is in chess.
     *
     * @return true if the own king is currently being attacked
     */
    public static boolean kingInChess() {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if ((PLACEMENT[row][column] != null) && (PLACEMENT[row][column].isWhite() == whiteTurn)
                        && PLACEMENT[row][column].getName().equals(FigureNames.KING.name())) {
                    return isAttacked(row, column);
                }
            }
        }
        return false;
    }

    /**
     * Calculates the number of possible moves of the current player.
     *
     * @return number of moves
     */
    public static int countPossibleMoves() {
        int anzahl = 0;
        for (int zeile = 0; zeile < 8; zeile++) {
            for (int spalte = 0; spalte < 8; spalte++) {
                if ((PLACEMENT[zeile][spalte] != null) && (PLACEMENT[zeile][spalte].isWhite() == whiteTurn)) {
                    anzahl += countPossibleMoves(zeile, spalte);
                }
            }
        }
        return anzahl;
    }

    /**
     * Calculates the number of possible moves of the current player for one specific figure.
     *
     * @param row selected row
     * @param column selected column
     * @return number of moves
     */
    public static int countPossibleMoves(final int row, final int column) {
        if (PLACEMENT[row][column] != null) {
            return PLACEMENT[row][column].getPossibleMoves(PLACEMENT, row, column).length;
        }
        else {
            return 0;
        }
    }

    //gibt den letzten Zug zurück

    /**
     * Gets the last move.
     *
     * @return the last move
     */
    public static int[] lastMove() {
        return MOVES.getLast();
    }
}
