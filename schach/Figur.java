package schach;

public abstract class Figur {
	boolean istWeiß;
	String bezeichnung;
	int bewegungen = 0;
	
	public Figur(boolean istWeiß, String bezeichnung) {
		this.istWeiß = istWeiß;
		this.bezeichnung = bezeichnung;
	}
	
	public abstract String getUnicode();
	
	public abstract int[][] getPossibleMoves(Figur[][] aufstellung, int zeile, int spalte);
	public abstract boolean zugMoeglich(Figur[][] aufstellung, int vonZeile, int vonSpalte, int nachZeile, int nachSpalte);
}
