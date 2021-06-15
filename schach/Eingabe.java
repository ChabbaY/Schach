package schach;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Eingabe {
	public static String eingabe() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			do {
				System.out.println("Dame (d) / Turm (t) / Läufer (l) / Springer (s)");
				switch(br.readLine()) {
					case "d":
						return "Dame";
					case "t":
						return "Turm";
					case "l":
						return "Läufer";
					case "s":
						return "Springer";
					default:
						System.out.println("Inkorrekte Eingabe, bitte noch einmal versuchen...");
				}
			}
			while (true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
