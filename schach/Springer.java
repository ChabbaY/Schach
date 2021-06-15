package schach;

import java.util.ArrayList;
import java.util.List;

public class Springer extends Figur{
	private List<int[]> lst = new ArrayList<>();
	
	public Springer(boolean istWeiß) {
		super(istWeiß, "Springer");
	}
	
	public String getUnicode() {
		if (istWeiß ) {
			return "\u2658";
		}
		else {
			return "\u265e";
		}
	}
	
	public int[][] getPossibleMoves(Figur[][] aufstellung, int zeile, int spalte) {
		lst.clear();
		if ((MainSchach.weißAmZug == istWeiß) && MainSchach.koenigImSchach()) return new int[0][];
		
		if (zugMoeglich(aufstellung, zeile, spalte, zeile + 2, spalte + 1)) {
			int[] a = {zeile + 2, spalte + 1};
			lst.add(a);
		}
		if (zugMoeglich(aufstellung, zeile, spalte, zeile + 2, spalte - 1)) {
			int[] a = {zeile + 2, spalte - 1};
			lst.add(a);
		}
		if (zugMoeglich(aufstellung, zeile, spalte, zeile - 2, spalte + 1)) {
			int[] a = {zeile - 2, spalte + 1};
			lst.add(a);
		}
		if (zugMoeglich(aufstellung, zeile, spalte, zeile - 2, spalte - 1)) {
			int[] a = {zeile - 2, spalte - 1};
			lst.add(a);
		}
		if (zugMoeglich(aufstellung, zeile, spalte, zeile + 1, spalte + 2)) {
			int[] a = {zeile + 1, spalte + 2};
			lst.add(a);
		}
		if (zugMoeglich(aufstellung, zeile, spalte, zeile + 1, spalte - 2)) {
			int[] a = {zeile + 1, spalte - 2};
			lst.add(a);
		}
		if (zugMoeglich(aufstellung, zeile, spalte, zeile - 1, spalte + 2)) {
			int[] a = {zeile - 1, spalte + 2};
			lst.add(a);
		}
		if (zugMoeglich(aufstellung, zeile, spalte, zeile - 1, spalte - 2)) {
			int[] a = {zeile - 1, spalte - 2};
			lst.add(a);
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
