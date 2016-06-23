/**
 * Spielfeld.java
 * Die Spielfeld Klasse beinhaltet das eigentliche Spielfeld und einige
 * Hilfsmethoden.
 * 
 * @author Timo Appenzeller (191382), Anton Makarow (191721)
 * @date 19.04.2016
 */

public class MaxSpielfeld {

	private String[][] spielfeld;
	private int groesseX;
	private int groesseY;

	/**
	 * Konstuktor. Füllt das Spielfeld mit Zufallszahlen von 1-9
	 * @param x Die Breite des Spielfeldes
	 * @param y Die Höhe des Spielfeldes
	 */
	public MaxSpielfeld(int x, int y) {
		spielfeld = new String[y][x];
		groesseX = x;
		groesseY = y;
		initialisieren();
	}

	/**
	 * Gibt das gesamte Spielfeld zurück
	 * @return String[][] Das Spielfeld
	 */
	public String[][] getSpielfeld() {
		return spielfeld;
	}

	/**
	 * Gibt den Wert eines bestimmten Feldes zurück
	 * @param x Die X-Koordinate
	 * @param y Die Y-Koordinate
	 * @return Inhalt des Feldes als String
	 */
	public String getField(int x, int y) {
		return spielfeld[y][x];
	}

	/**
	 * Setzt den Wert in ein bestimmtes Feld
	 * @param x Die X-Koordinate
	 * @param y Die Y-Koordinate
	 * @param c Der String der in das Feld geschrieben werden soll
	 */
	public void setField(int x, int y, String c) {
		// !!! spielfeld[zeile][spalte]
		spielfeld[y][x] = c;
	}
	
	/**
	 * Füllt das Spielfeld mit zufälligen Zahlen von 1-9
	 */
	public void initialisieren(){
		for(int i = 0; i < groesseY; i++) {
			for(int j = 0; j < groesseX; j++) {
				spielfeld[i][j] = String.valueOf((int)(Math.random()*100000%9+1));
			}
		}
	}
}
