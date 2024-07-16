package cloud.englert.schach.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Processes user inputs.
 *
 * @author Linus Englert
 */
public class Input {
    /**
     * Reads an input.
     *
     * @return the input string
     */
    public static String input() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            do {
                System.out.println("Queen (Q) / Rook (R) / Bishop (B) / Knight (N)");
                switch(br.readLine()) {
                    case "Q":
                        return "Dame";
                    case "R":
                        return "Turm";
                    case "B":
                        return "Läufer";
                    case "N":
                        return "Springer";
                    default:
                        System.out.println("Inkorrekte Eingabe, bitte noch einmal versuchen...");
                }
            }
            while (true);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
