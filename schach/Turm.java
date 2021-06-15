package schach;

import java.util.ArrayList;
import java.util.List;

public class Turm extends Figur{
	private List<int[]> lst = new ArrayList<>();
	
	public Turm(boolean istWeiß) {
		super(istWeiß, "Turm");
	}
	
	public String getUnicode() {
		if (istWeiß ) {
			return "\u2656";
		}
		else {
			return "\u265c";
		}
	}
	
	public int[][] getPossibleMoves(Figur[][] aufstellung, int zeile, int spalte) {
		lst.clear();
		if ((MainSchach.weißAmZug == istWeiß) && MainSchach.koenigImSchach()) return new int[0][];
		
		for (int i = 1; i < 8; i++) {
			if (zugMoeglich(aufstellung, zeile, spalte, zeile + i, spalte)) {
				int[] a = {zeile + i, spalte};
				lst.add(a);
			}
			else break;
			if (aufstellung[zeile + i][spalte] != null) break;
		}
		for (int i = 1; i < 8; i++) {
			if (zugMoeglich(aufstellung, zeile, spalte, zeile, spalte + i)) {
				int[] a = {zeile, spalte + i};
				lst.add(a);
			}
			else break;
			if (aufstellung[zeile][spalte + i] != null) break;
		}
		for (int i = 1; i < 8; i++) {
			if (zugMoeglich(aufstellung, zeile, spalte, zeile - i, spalte)) {
				int[] a = {zeile - i, spalte};
				lst.add(a);
			}
			else break;
			if (aufstellung[zeile - i][spalte] != null) break;
		}
		for (int i = 1; i < 8; i++) {
			if (zugMoeglich(aufstellung, zeile, spalte, zeile, spalte - i)) {
				int[] a = {zeile, spalte - i};
				lst.add(a);
			}
			else break;
			if (aufstellung[zeile][spalte - i] != null) break;
		}
		
		int[][] erg = new int[lst.size()][2];
		for (int i = 0; i < erg.length; i++) {
			erg [i][0] = lst.get(i)[0];
			erg [i][1] = lst.get(i)[1];
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
