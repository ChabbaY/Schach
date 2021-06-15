package schach;

import java.util.ArrayList;
import java.util.List;

public class MainSchach {
	private static Figur[][] aufstellung = new Figur[8][8];
	private static Fenster fenster;
	
	static boolean weißAmZug = true;
	private static boolean playing = true;
	private static boolean figurGewaehlt = false;
	private static int gewaehltZeile = 0, gewaehltSpalte = 0;
	
	private static List<int[]> zuege = new ArrayList<>();
	
	public static void main(String[] args) {
		startaufstellung();
		fenster = new Fenster();
		fenster.paint(aufstellung);
		
		System.out.println("Weiß am Zug:");
		System.out.println(anzahlMoeglicherZuege() + " Züge möglich");
	}
	
	public static void startaufstellung() {
		aufstellung[0][0] = new Turm(false);
		aufstellung[0][1] = new Springer(false);
		aufstellung[0][2] = new Laeufer(false);
		aufstellung[0][3] = new Dame(false);
		aufstellung[0][4] = new Koenig(false);
		aufstellung[0][5] = new Laeufer(false);
		aufstellung[0][6] = new Springer(false);
		aufstellung[0][7] = new Turm(false);
		
		aufstellung[1][0] = new Bauer(false);
		aufstellung[1][1] = new Bauer(false);
		aufstellung[1][2] = new Bauer(false);
		aufstellung[1][3] = new Bauer(false);
		aufstellung[1][4] = new Bauer(false);
		aufstellung[1][5] = new Bauer(false);
		aufstellung[1][6] = new Bauer(false);
		aufstellung[1][7] = new Bauer(false);
		
		aufstellung[6][0] = new Bauer(true);
		aufstellung[6][1] = new Bauer(true);
		aufstellung[6][2] = new Bauer(true);
		aufstellung[6][3] = new Bauer(true);
		aufstellung[6][4] = new Bauer(true);
		aufstellung[6][5] = new Bauer(true);
		aufstellung[6][6] = new Bauer(true);
		aufstellung[6][7] = new Bauer(true);
		
		aufstellung[7][0] = new Turm(true);
		aufstellung[7][1] = new Springer(true);
		aufstellung[7][2] = new Laeufer(true);
		aufstellung[7][3] = new Dame(true);
		aufstellung[7][4] = new Koenig(true);
		aufstellung[7][5] = new Laeufer(true);
		aufstellung[7][6] = new Springer(true);
		aufstellung[7][7] = new Turm(true);
	}
	
	public static void zugwechsel() {
		weißAmZug = !weißAmZug;
		if (weißAmZug) {
			System.out.println("\nWeiß am Zug:");
		} else {
			System.out.println("\nSchwarz am Zug:");
		}
		
		boolean schach = koenigImSchach();
		if (schach) System.out.println("König im Schach!");
		
		int anzahlZuege = anzahlMoeglicherZuege();
		System.out.println(anzahlZuege + " Züge möglich");
		
		if ((anzahlZuege == 0) && schach) {
			System.out.println("Schachmatt!");
			System.out.println(weißAmZug ? "Schwarz gewinnt!" : "Weiß gewinnt!");
			
			playing = false;
		}
		else if (anzahlZuege == 0) {
			System.out.println("Patt!");
			
			playing = false;
		}
	}
	
	public static void click(int zeile, int spalte) {
		if (!playing) return;
		
		System.out.print("clicked on: " + zeile + " " + spalte + " ");
		if (aufstellung[zeile][spalte] == null) { 
			System.out.println();
			
			if (figurGewaehlt) {//Zug auf leeres Feld
				move(gewaehltZeile, gewaehltSpalte, zeile, spalte);
				figurGewaehlt = false;
			}
		} else {
			System.out.println(aufstellung[zeile][spalte].bezeichnung);
			//Farbe am Zug
			if (weißAmZug == aufstellung[zeile][spalte].istWeiß) {
				if (anzahlMoeglicherZuege(zeile, spalte) > 0) {
					System.out.println("Ziel wählen");
					figurGewaehlt = true;
					gewaehltZeile = zeile;
					gewaehltSpalte = spalte;
					fenster.colorBoard(aufstellung[zeile][spalte].getPossibleMoves(aufstellung, zeile, spalte));
				} else {
					System.out.println("andere Figur wählen");
					figurGewaehlt = false;
					fenster.colorBoard();
				}
			} else if (figurGewaehlt) {//Angriff einer gegnerischen Figur
				move(gewaehltZeile, gewaehltSpalte, zeile, spalte);
				figurGewaehlt = false;
			}
		}
	}
	//bewegt die gewählte Figur falls möglich
	public static void move(int vonZeile, int vonSpalte, int nachZeile, int nachSpalte) {
		int[][] moves = aufstellung[vonZeile][vonSpalte].getPossibleMoves(aufstellung, vonZeile, vonSpalte);
		for (int i = 0; i < moves.length; i++) {
			//Koordinaten in möglichen Zügen enthalten
			if ((moves[i][0] == nachZeile) && (moves[i][1] == nachSpalte)) {
				System.out.println("Zug von " + aufstellung[vonZeile][vonSpalte].bezeichnung + " nach " + nachZeile + " " + nachSpalte);
				aufstellung[vonZeile][vonSpalte].bewegungen++;
				int[] zug = {vonZeile, vonSpalte, nachZeile, nachSpalte};
				zuege.add(zug);
				
				aufstellung[nachZeile][nachSpalte] = aufstellung[vonZeile][vonSpalte];
				aufstellung[vonZeile][vonSpalte] = null;
				
				if (moves[i].length > 2) {
					switch (moves[i][2]) {
						case 1://en passant
							System.out.println("en passant!");
							aufstellung[vonZeile][nachSpalte] = null;
							break;
						case 2://Rochade
							System.out.println("Rochade!");
							if (nachSpalte > vonSpalte) {//kurz
								aufstellung[nachZeile][5] = aufstellung[nachZeile][7];
								aufstellung[nachZeile][7] = null;
							}
							else {//lang
								aufstellung[nachZeile][3] = aufstellung[nachZeile][0];
								aufstellung[nachZeile][0] = null;
							}
							break;
					}
				}
				
				//Umwandlung
				if ((aufstellung[nachZeile][nachSpalte].bezeichnung == "Bauer") && ((nachZeile == 0) || (nachZeile == 7))) {
					System.out.println("Umwandlung!");
					
					String figur = null;
					do {
						figur = Eingabe.eingabe();
					}
					while (figur == null);
					
					switch (figur) {
						case "Dame":
							aufstellung[nachZeile][nachSpalte] = new Dame(weißAmZug);
							break;
						case "Turm":
							aufstellung[nachZeile][nachSpalte] = new Turm(weißAmZug);
							break;
						case "Läufer":
							aufstellung[nachZeile][nachSpalte] = new Laeufer(weißAmZug);
							break;
						case "Springer":
							aufstellung[nachZeile][nachSpalte] = new Springer(weißAmZug);
							break;
					}
				}
				
				fenster.paint(aufstellung);
				
				zugwechsel();
			}
		}
	}
	//true wenn König nach Zug nicht im Schach
	public static boolean moveValid(int vonZeile, int vonSpalte, int nachZeile, int nachSpalte) {
		Figur von = aufstellung[vonZeile][vonSpalte];
		Figur nach = aufstellung[nachZeile][nachSpalte];
		
		aufstellung[nachZeile][nachSpalte] = aufstellung[vonZeile][vonSpalte];
		aufstellung[vonZeile][vonSpalte] = null;
		
		if (!koenigImSchach()) {
			aufstellung[vonZeile][vonSpalte] = von;
			aufstellung[nachZeile][nachSpalte] = nach;
			return true;
		}
		else {
			aufstellung[vonZeile][vonSpalte] = von;
			aufstellung[nachZeile][nachSpalte] = nach;
			return false;
		}
	}
	
	//true wenn Gegner (nicht am Zug) Feld erreichen kann
	public static boolean wirdAngegriffen(int zeile_x, int spalte_x) {
		for (int zeile = 0; zeile < 8; zeile++) {
			for (int spalte = 0; spalte < 8; spalte++) {
				//ist gegnerischer Stein
				if ((aufstellung[zeile][spalte] != null) && (aufstellung[zeile][spalte].istWeiß != weißAmZug)) {
					int[][] moves = aufstellung[zeile][spalte].getPossibleMoves(aufstellung, zeile, spalte);
					for (int i = 0; i < moves.length; i++) {
						//Koordinaten in möglichen Zügen enthalten
						if ((moves[i][0] == zeile_x) && (moves[i][1] == spalte_x)) return true;
					}
					
				}
			}
		}
		return false;
	}
	
	//true wenn König des aktuellen Spieler im Schach
	public static boolean koenigImSchach() {
		for (int zeile = 0; zeile < 8; zeile++) {
			for (int spalte = 0; spalte < 8; spalte++) {
				if ((aufstellung[zeile][spalte] != null) && (aufstellung[zeile][spalte].istWeiß == weißAmZug) && (aufstellung[zeile][spalte].bezeichnung.equals("König"))) {
					return wirdAngegriffen(zeile, spalte);
				}
			}
		}
		return false;
	}
	
	//liefert die Anzahl an möglichen Zügen des aktuellen Spielers
	public static int anzahlMoeglicherZuege() {
		int anzahl = 0;
		for (int zeile = 0; zeile < 8; zeile++) {
			for (int spalte = 0; spalte < 8; spalte++) {
				if ((aufstellung[zeile][spalte] != null) && (aufstellung[zeile][spalte].istWeiß == weißAmZug)) {
					anzahl += anzahlMoeglicherZuege(zeile, spalte);
				}
			}
		}
		return anzahl;
	}
	//liefert die Anzahl an möglichen Zügen des aktuellen Spielers für eine bestimmte Figur
	public static int anzahlMoeglicherZuege(int zeile, int spalte) {
		if (aufstellung[zeile][spalte] != null) {
			return aufstellung[zeile][spalte].getPossibleMoves(aufstellung, zeile, spalte).length;
		} else {
			return 0;
		}
	}
	
	//gibt den letzten Zug zurück
	public static int[] lastMove() {
		return zuege.get(zuege.size() - 1);
	}
}
