package schach;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fenster extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private JButton[][] buttons = new JButton[8][8];
	
	public Fenster() {
		super("Schach");
		this.setSize(1000, 1000);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(8, 8));
		this.add(panel);
		
		for (int zeile = 0; zeile < 8; zeile++) {
			for (int spalte = 0; spalte < 8; spalte++) {
				buttons[zeile][spalte] = new JButton();
				panel.add(buttons[zeile][spalte]);
				int zeile_x = zeile, spalte_x = spalte;
				buttons[zeile][spalte].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						MainSchach.click(zeile_x, spalte_x);
					}
				});
				
				buttons[zeile][spalte].setBorder(null);
				buttons[zeile][spalte].setFont(new Font(buttons[zeile][spalte].getFont().getName(), buttons[zeile][spalte].getFont().getStyle(), 50));
			}
		}
		
		colorBoard();
		
		this.setVisible(true);
	}
	
	public void paint(Figur[][] aufstellung) {
		for (int zeile = 0; zeile < 8; zeile++) {
			for (int spalte = 0; spalte < 8; spalte++) {
				if (aufstellung[zeile][spalte] == null) {
					buttons[zeile][spalte].setText("");
				} else {
					buttons[zeile][spalte].setText(aufstellung[zeile][spalte].getUnicode());
				}
			}
		}
		colorBoard();
	}
	
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
	public void colorBoard(int[][] highlights) {
		colorBoard();
		for (int i = 0; i < highlights.length; i++) {
			buttons[highlights[i][0]][highlights[i][1]].setBackground(new Color(0, 255, 0));
		}
	}
}
