package cloud.englert.schach.io;

import cloud.englert.schach.Main;
import cloud.englert.schach.figures.Figure;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.Serial;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The window to display the game.
 *
 * @author Linus Englert
 */
public class Window extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    private final JButton[][] buttons = new JButton[8][8];

    /**
     * Creates a new window.
     */
    public Window() {
        super("Chess");
        this.setSize(1000, 1000);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 8));
        this.add(panel);

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                buttons[row][column] = new JButton();
                panel.add(buttons[row][column]);
                int rowX = row;
                int columnX = column;
                buttons[row][column].addActionListener((ActionEvent e) -> Main.click(rowX, columnX));

                buttons[row][column].setBorder(null);
                buttons[row][column].setFont(new Font(buttons[row][column].getFont().getName(),
                        buttons[row][column].getFont().getStyle(), 50));
            }
        }

        colorBoard();

        this.setVisible(true);
    }

    /**
     * Takes the placement & sets the corresponding texts to the unicode values of the figures.
     *
     * @param placement figure matrix
     */
    public void paint(Figure[]... placement) {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (placement[row][column] == null) {
                    buttons[row][column].setText("");
                }
                else {
                    buttons[row][column].setText(placement[row][column].getUnicode());
                }
            }
        }
        colorBoard();
    }

    /**
     * Colors the board with the field colors.
     */
    public void colorBoard() {
        for (int zeile = 0; zeile < 8; zeile++) {
            for (int spalte = 0; spalte < 8; spalte++) {
                if (zeile % 2 == spalte % 2) {
                    buttons[zeile][spalte].setBackground(new Color(255, 206, 158));
                }
                else {
                    buttons[zeile][spalte].setBackground(new Color(209, 139, 71));
                }
            }
        }
    }

    /**
     * Colors the board with the field colors and highlights where the selected figure can move.
     *
     * @param highlights where the selected figure can move
     */
    public void colorBoard(int[]... highlights) {
        colorBoard();
        for (int[] highlight : highlights) {
            buttons[highlight[0]][highlight[1]].setBackground(new Color(0, 255, 0));
        }
    }
}
