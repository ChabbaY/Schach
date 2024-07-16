package cloud.englert.schach.io;

import cloud.englert.schach.figures.FigureNames;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.logging.Logger;

/**
 * Processes user inputs.
 *
 * @author Linus Englert
 */
public final class Input {
    private static final Logger LOGGER = Logger.getLogger(Input.class.getSimpleName());

    private Input() { }

    /**
     * Reads an input.
     *
     * @return the input string
     */
    public static FigureNames input() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                LOGGER.info("Please enter your choice (Queen (Q) / Rook (R) / Bishop (B) / Knight (N)): ");
                switch(br.readLine()) {
                    case "Q":
                        return FigureNames.QUEEN;
                    case "R":
                        return FigureNames.ROOK;
                    case "B":
                        return FigureNames.BISHOP;
                    case "N":
                        return FigureNames.KNIGHT;
                    default:
                        LOGGER.warning("Inkorrekte Eingabe, bitte noch einmal versuchen...");
                }
            }
        }
        catch (IOException e) {
            LOGGER.severe("Could not process input: " + e.getMessage());
        }

        return null;
    }
}
