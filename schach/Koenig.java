package schach;

import java.util.ArrayList;
import java.util.List;

public class Koenig extends Figur{
	private List<int[]> lst = new ArrayList<>();
	
	public Koenig(boolean istWeiß) {
		super(istWeiß, "König");
	}
	
	public String getUnicode() {
		if (istWeiß ) {
			return "\u2654";
		}
		else {
			return "\u265a";
		}
	}
	
	public int[][] getPossibleMoves(Figur[][] aufstellung, int zeile, int spalte) {
		lst.clear();
		
		if (zugMoeglich(aufstellung, zeile, spalte, zeile + 1, spalte)) {
			int[] a = {zeile + 1, spalte, 0};
			lst.add(a);
		}
		if (zugMoeglich(aufstellung, zeile, spalte, zeile + 1, spalte + 1)) {
			int[] a = {zeile + 1, spalte + 1, 0};
			lst.add(a);
		}
		if (zugMoeglich(aufstellung, zeile, spalte, zeile + 1, spalte - 1)) {
			int[] a = {zeile + 1, spalte - 1, 0};
			lst.add(a);
		}
		if (zugMoeglich(aufstellung, zeile, spalte, zeile - 1, spalte)) {
			int[] a = {zeile - 1, spalte, 0};
			lst.add(a);
		}
		if (zugMoeglich(aufstellung, zeile, spalte, zeile - 1, spalte + 1)) {
			int[] a = {zeile - 1, spalte + 1, 0};
			lst.add(a);
		}
		if (zugMoeglich(aufstellung, zeile, spalte, zeile - 1, spalte - 1)) {
			int[] a = {zeile - 1, spalte - 1, 0};
			lst.add(a);
		}
		if (zugMoeglich(aufstellung, zeile, spalte, zeile, spalte + 1)) {
			int[] a = {zeile, spalte + 1, 0};
			lst.add(a);
		}
		if (zugMoeglich(aufstellung, zeile, spalte, zeile, spalte - 1)) {
			int[] a = {zeile, spalte - 1, 0};
			lst.add(a);
		}
		
		//Rochade (2)
		if (bewegungen == 0) {
			if ((aufstellung[zeile][7] != null) && (aufstellung[zeile][7].bewegungen == 0) && (aufstellung[zeile][5] == null) && (aufstellung[zeile][6] == null)) {//kurz (Königsflügel)
				if ((!MainSchach.wirdAngegriffen(zeile, 5)) && (!MainSchach.wirdAngegriffen(zeile, 6))) {
					int[] a = {zeile, 6, 2};
					lst.add(a);
				}
			}
			if ((aufstellung[zeile][0] != null) && (aufstellung[zeile][0].bewegungen == 0) && (aufstellung[zeile][1] == null) && (aufstellung[zeile][2] == null) && (aufstellung[zeile][3] == null)) {//lang (Damenflügel)
				if ((!MainSchach.wirdAngegriffen(zeile, 2)) && (!MainSchach.wirdAngegriffen(zeile, 3))) {
					int[] a = {zeile, 2, 2};
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
