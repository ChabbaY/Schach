package cloud.englert.schach;

import cloud.englert.schach.figures.Bishop;
import cloud.englert.schach.figures.Figure;
import cloud.englert.schach.figures.King;
import cloud.englert.schach.figures.Knight;
import cloud.englert.schach.figures.Pawn;
import cloud.englert.schach.figures.Queen;
import cloud.englert.schach.figures.Rook;
import cloud.englert.schach.io.Input;
import cloud.englert.schach.io.Window;

import java.util.ArrayList;
import java.util.List;

/**
 * Chess game.
 *
 * @author Linus Englert
 */
public class Main {
    private static final Figure[][] placement = new Figure[8][8];
    private static Window window;

    public static boolean whiteTurn = true;
    private static boolean playing = true;
    private static boolean figureSelected = false;
    private static int selectedRow = 0;
    private static int selectedColumn = 0;

    private static final List<int[]> moves = new ArrayList<>();

    /**
     * Entry point of the program.
     *
     * @param args ignored optional arguments
     */
    public static void main(String[] args) {
        startPlacement();
        window = new Window();
        window.paint(placement);

        System.out.println("Weiß am Zug:");
        System.out.println(countPossibleMoves() + " Züge möglich");
    }

    /**
     * Places the figures to start a game.
     */
    public static void startPlacement() {
        placement[0][0] = new Rook(false);
        placement[0][1] = new Knight(false);
        placement[0][2] = new Bishop(false);
        placement[0][3] = new Queen(false);
        placement[0][4] = new King(false);
        placement[0][5] = new Bishop(false);
        placement[0][6] = new Knight(false);
        placement[0][7] = new Rook(false);

        placement[1][0] = new Pawn(false);
        placement[1][1] = new Pawn(false);
        placement[1][2] = new Pawn(false);
        placement[1][3] = new Pawn(false);
        placement[1][4] = new Pawn(false);
        placement[1][5] = new Pawn(false);
        placement[1][6] = new Pawn(false);
        placement[1][7] = new Pawn(false);

        placement[6][0] = new Pawn(true);
        placement[6][1] = new Pawn(true);
        placement[6][2] = new Pawn(true);
        placement[6][3] = new Pawn(true);
        placement[6][4] = new Pawn(true);
        placement[6][5] = new Pawn(true);
        placement[6][6] = new Pawn(true);
        placement[6][7] = new Pawn(true);

        placement[7][0] = new Rook(true);
        placement[7][1] = new Knight(true);
        placement[7][2] = new Bishop(true);
        placement[7][3] = new Queen(true);
        placement[7][4] = new King(true);
        placement[7][5] = new Bishop(true);
        placement[7][6] = new Knight(true);
        placement[7][7] = new Rook(true);
    }

    /**
     * Finishes a turn, if not already game over it's the other player's turn.
     */
    public static void finishTurn() {
        whiteTurn = !whiteTurn;
        if (whiteTurn) {
            System.out.println("\nWeiß am Zug:");
        }
        else {
            System.out.println("\nSchwarz am Zug:");
        }

        boolean schach = kingInChess();
        if (schach) {
            System.out.println("König im Schach!");
        }

        int nrMoves = countPossibleMoves();
        System.out.println(nrMoves + " Züge möglich");

        if ((nrMoves == 0) && schach) {
            System.out.println("Schachmatt!");
            System.out.println(whiteTurn ? "Schwarz gewinnt!" : "Weiß gewinnt!");

            playing = false;
        }
        else if (nrMoves == 0) {
            System.out.println("Patt!");

            playing = false;
        }
    }

    /**
     * Selects a figure and highlights possible moves of applicable.
     *
     * @param row selected row
     * @param column selected column
     */
    public static void click(int row, int column) {
        if (!playing) {
            return;
        }

        System.out.print("clicked on: " + row + " " + column + " ");
        if (placement[row][column] == null) {
            System.out.println();

            if (figureSelected) { //Zug auf leeres Feld
                move(selectedRow, selectedColumn, row, column);
                figureSelected = false;
            }
        }
        else {
            System.out.println(placement[row][column].name);
            //Farbe am Zug
            if (whiteTurn == placement[row][column].isWhite) {
                if (countPossibleMoves(row, column) > 0) {
                    System.out.println("Ziel wählen");
                    figureSelected = true;
                    selectedRow = row;
                    selectedColumn = column;
                    window.colorBoard(placement[row][column].getPossibleMoves(placement, row, column));
                }
                else {
                    System.out.println("andere Figur wählen");
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
    public static void move(int fromRow, int fromColumn, int toRow, int toColumn) {
        int[][] moves = placement[fromRow][fromColumn].getPossibleMoves(placement, fromRow, fromColumn);
        for (int i = 0; i < moves.length; i++) {
            //Koordinaten in möglichen Zügen enthalten
            if ((moves[i][0] == toRow) && (moves[i][1] == toColumn)) {
                System.out.println("Zug von " + placement[fromRow][fromColumn].name + " nach " + toRow + " " + toColumn);
                placement[fromRow][fromColumn].movements++;
                int[] zug = {fromRow, fromColumn, toRow, toColumn};
                Main.moves.add(zug);

                placement[toRow][toColumn] = placement[fromRow][fromColumn];
                placement[fromRow][fromColumn] = null;

                if (moves[i].length > 2) {
                    switch (moves[i][2]) {
                        case 1://en passant
                            System.out.println("en passant!");
                            placement[fromRow][toColumn] = null;
                            break;
                        case 2://Rochade
                            System.out.println("Rochade!");
                            if (toColumn > fromColumn) { //kurz
                                placement[toRow][5] = placement[toRow][7];
                                placement[toRow][7] = null;
                            }
                            else { //lang
                                placement[toRow][3] = placement[toRow][0];
                                placement[toRow][0] = null;
                            }
                            break;
                    }
                }

                //Umwandlung
                if (placement[toRow][toColumn].name.equals("Bauer") && ((toRow == 0) || (toRow == 7))) {
                    System.out.println("Umwandlung!");

                    String figure;
                    do {
                        figure = Input.input();
                    }
                    while (figure == null);

                    switch (figure) {
                        case "Dame":
                            placement[toRow][toColumn] = new Queen(whiteTurn);
                            break;
                        case "Turm":
                            placement[toRow][toColumn] = new Rook(whiteTurn);
                            break;
                        case "Läufer":
                            placement[toRow][toColumn] = new Bishop(whiteTurn);
                            break;
                        case "Springer":
                            placement[toRow][toColumn] = new Knight(whiteTurn);
                            break;
                    }
                }

                window.paint(placement);

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
    public static boolean moveValid(int fromRow, int fromColumn, int toRow, int toColumn) {
        Figure fromFigure = placement[fromRow][fromColumn];
        Figure toFigure = placement[toRow][toColumn];

        placement[toRow][toColumn] = placement[fromRow][fromColumn];
        placement[fromRow][fromColumn] = null;

        if (!kingInChess()) {
            placement[fromRow][fromColumn] = fromFigure;
            placement[toRow][toColumn] = toFigure;
            return true;
        }
        else {
            placement[fromRow][fromColumn] = fromFigure;
            placement[toRow][toColumn] = toFigure;
            return false;
        }
    }

    /**
     * True if the opponent can reach a field with its next move.
     *
     * @param rowX row to check
     * @param columnX column to check
     * @return true if it can be attacked
     */
    public static boolean isAttacked(int rowX, int columnX) {
        for (int zeile = 0; zeile < 8; zeile++) {
            for (int spalte = 0; spalte < 8; spalte++) {
                //ist gegnerischer Stein
                if ((placement[zeile][spalte] != null) && (placement[zeile][spalte].isWhite != whiteTurn)) {
                    int[][] moves = placement[zeile][spalte].getPossibleMoves(placement, zeile, spalte);
                    for (int i = 0; i < moves.length; i++) {
                        //Koordinaten in möglichen Zügen enthalten
                        if ((moves[i][0] == rowX) && (moves[i][1] == columnX)) {
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
                if ((placement[row][column] != null) && (placement[row][column].isWhite == whiteTurn)
                        && placement[row][column].name.equals("König")) {
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
                if ((placement[zeile][spalte] != null) && (placement[zeile][spalte].isWhite == whiteTurn)) {
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
    public static int countPossibleMoves(int row, int column) {
        if (placement[row][column] != null) {
            return placement[row][column].getPossibleMoves(placement, row, column).length;
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
        return moves.getLast();
    }
}
