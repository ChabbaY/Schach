package schach;

import java.util.ArrayList;
import java.util.List;

public class Bauer extends Figur{
	private List<int[]> lst = new ArrayList<>();
	
	public Bauer(boolean istWeiß) {
		super(istWeiß, "Bauer");
	}
	
	public String getUnicode() {
		if (istWeiß ) {
			return "\u2659";
		}
		else {
			return "\u265f";
		}
	}
	
	public int[][] getPossibleMoves(Figur[][] aufstellung, int zeile, int spalte) {
		lst.clear();
		if ((MainSchach.weißAmZug == istWeiß) && MainSchach.koenigImSchach()) return new int[0][];
		
		if (istWeiß && (zeile == 6)) {//Weiß Grundstellung
			if (zugMoeglich(aufstellung, zeile, spalte, zeile - 2, spalte) && (aufstellung[zeile - 1][spalte] == null)) {
				int[] a = {zeile - 2, spalte, 0};
				lst.add(a);
			}
		}
		if (istWeiß) {//Weiß
			if (zugMoeglich(aufstellung, zeile, spalte, zeile - 1, spalte) && (aufstellung[zeile - 1][spalte] == null)) {
				int[] a = {zeile - 1, spalte, 0};
				lst.add(a);
			}
			if ((zeile > 0) && (spalte > 0) && aufstellung[zeile - 1][spalte - 1] != null) {
				if (zugMoeglich(aufstellung, zeile, spalte, zeile - 1, spalte - 1)) {
					int[] a = {zeile - 1, spalte - 1, 0};
					lst.add(a);
				}
			}
			if ((zeile > 0) && (spalte < 7) && aufstellung[zeile - 1][spalte + 1] != null) {
				if (zugMoeglich(aufstellung, zeile, spalte, zeile - 1, spalte + 1)) {
					int[] a = {zeile - 1, spalte + 1, 0};
					lst.add(a);
				}
			}
			
			//en passant (1)
			if (zeile == 3) {
				int[] last = MainSchach.lastMove();
				if ((last[0] == 1) && (last[2] == 3) && (aufstellung[last[2]][last[3]].bezeichnung == "Bauer")) {
					int[] a = {zeile - 1, last[3], 1};
					lst.add(a);
				}
			}
		}
		if (!istWeiß && (zeile == 1)) {//Schwarz Grundstellung
			if (zugMoeglich(aufstellung, zeile, spalte, zeile + 2, spalte) && (aufstellung[zeile + 1][spalte] == null)) {
				int[] a = {zeile + 2, spalte, 0};
				lst.add(a);
			}
		}
		if (!istWeiß) {//Schwarz
			if (zugMoeglich(aufstellung, zeile, spalte, zeile + 1, spalte) && (aufstellung[zeile + 1][spalte] == null)) {
				int[] a = {zeile + 1, spalte, 0};
				lst.add(a);
			}
			if ((zeile < 7) && (spalte > 0) && aufstellung[zeile + 1][spalte - 1] != null) {
				if (zugMoeglich(aufstellung, zeile, spalte, zeile + 1, spalte - 1)) {
					int[] a = {zeile + 1, spalte - 1, 0};
					lst.add(a);
				}
			}
			if ((zeile < 7) && (spalte < 7) && aufstellung[zeile + 1][spalte + 1] != null) {
				if (zugMoeglich(aufstellung, zeile, spalte, zeile + 1, spalte + 1)) {
					int[] a = {zeile + 1, spalte + 1, 0};
					lst.add(a);
				}
			}
			
			//en passant (1)
			if (zeile == 4) {
				int[] last = MainSchach.lastMove();
				if ((last[0] == 6) && (last[2] == 4) && (aufstellung[last[2]][last[3]].bezeichnung == "Bauer")) {
					int[] a = {zeile + 1, last[3], 1};
					lst.add(a);
				}
			}
		}
		
		int[][] erg = new int[lst.size()][3];
		for (int i = 0; i < erg.length; i++) {
			erg [i][0] = lst.get(i)[0];
			erg [i][1] = lst.get(i)[1];
			erg [i][2] = lst.get(i)[2];
		}
		return erg;
	}
	
	public boolean zugMoeglich(Figur[][] aufstellung, int vonZeile, int vonSpalte, int nachZeile, int nachSpalte) {
		//außerhalb der Grenzen
		if ((nachZeile > 7) || (nachZeile < 0) || (nachSpalte > 7) || (nachSpalte < 0)) return false;
		//eigene Figur im Weg
		if ((aufstellung[nachZeile][nachSpalte] != null) && (aufstellung[nachZeile][nachSpalte].istWeiß == istWeiß)) return false;
		
		if (MainSchach.weißAmZug == istWeiß) {
			return MainSchach.moveValid(vonZeile, vonSpalte, nachZeile, nachSpalte);
		}
		else return true;
	}
}
