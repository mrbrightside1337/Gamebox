/**
 * GoLModel.java
 *
 * Game of Life. Das Spielfeld als kartesisches Koordinatensystem
 *
 *		+------------------------------------------------->
 *		| 0,0 | 1,0 | 2,0 | 3,0 | 4,0 | 5,0 | ...
 *		| --- + --- + --- + --- + --- + ---
 *		| 0,1 | 1,1 | 2,1 | 3,1 | 4,1 | ...
 *		| --- + --- + --- + --- + ---
 *		| 0,2 | 1,2 | 2,2 | 3,2 | ...
 *		| --- + --- + --- + ---
 *		| 0,3 | 1,3 | 2,3 | ...
 *		| ... | ... | ... | ...
 *		|
 *
 *	X-Koordinate: Breite, Anzahl der Spalten (Columns)
 *	Y-Koordinate: Höhe, Anzahl der Zeilen (Rows)
 *
 * @author Anton Makarow (191721)
 * @author Timo Appenzeller (191382)
 * @author Laura Lohmann (191529)
 *
 * @date 24.06.2016
 */

import java.util.*;

class GoLModel extends Observable {
	private GoLModelController controller = new GoLModelController(this);

	static private int gameCounter = 1;
	private int gameID = 0; // ID dieses Spiels (wird pro Spiel um 1 erhöht)

	private int cycleDelay = 1000; // Schrittgeschwindigkeit in millisekunden (ms)

	private int generation = 0;
	private boolean[][] field, tmpField;

	/**
	 * @param	width		Spalten
	 * @param	height		Zeilen
	 */
	public GoLModel(int width, int height) {
		gameID = gameCounter++;
		System.out.println("Erstelle Spiel " + getGameID());

		/********************************************************************
		 * BEACHTEN: Breite und Höhe entsprechen der tatsächlichen Array Struktur.
		 * Y/X statt X/Y. Ist so festgelegt damit es kein Chaos gibt.
		 * Die transformation erfolgt in getWidth()/getHeight()/isAlive()/setField()
		 * VORSICHT beim direkten Schreiben in field oder tmpField !!!
		 ********************************************************************/
		field = new boolean[height][width];
		tmpField = new boolean[height][width];

		/**
		 * Gleiter zum testen
		 */
		loadForm("Gleiter");

		controller.startTimer();
	}

	/**
	 * Copy Konstruktor
	 */
	public GoLModel(GoLModel game) {
		this(game.getWidth(), game.getHeight());

		copyField(game.getField(), field);
		setCycleDelay(game.getCycleDelay());

	}

	/**
	 * @return	Schrittgeschwindigkeit in Millisekunden
	 */
	public int getCycleDelay() {
		return cycleDelay;
	}

	/**
	 * @return	Spiel-ID
	 */
	public int getGameID() {
		return gameID;
	}

	/**
	 * @return	Das Spielfeld
	 */
	public boolean[][] getField() {
		return field;
	}

	/**
	 * @return	Anzahl der Zeilen (Höhe)
	 */
	public int getHeight() {
		return field.length;
	}

	/**
	 * @return	Anzahl der Spalten (Breite)
	 */
	public int getWidth() {
		return field[0].length;
	}

	/**
	 * Setze die Schrittgeschwindigkeit.
	 *
	 * @param	delay	Schrittgeschwindigkeit in Millisekunden
	 */
	public void setCycleDelay(int delay) {
		cycleDelay = delay;
		controller.changeDelay(delay);
	}

	/**
	 * Setze die Spiel ID.
	 *
	 * @param	id	Die neue Spiel ID
	 */
	public void setGameID(int id) {
		gameID = id;
	}

	/**
	 * Pausiere das Spiel.
	 */
	public void pause() {
		controller.pauseTimer();
	}

	/**
	 * Setze das Spiel fort.
	 */
	public void resume() {
		controller.resumeTimer();
	}

	public void closeObservers() {
		setChanged();
		notifyObservers(new String("CLOSE"));
		controller.stopTimer();
	}

	/**
	 * Setze ein bestimmtes Feld im aktiven Spielfeld.
	 *
	 * @param	x	Die X-Koordinate
	 * @param	y	Die Y-Koordinate
	 * @param	b	Der Wert (true/false)
	 */
	public void setField(int x, int y, boolean b) {
		field[y][x] = b;

		setChanged();
		notifyObservers();
	}

	/**
	 * Prüfe obe eine bestimme Zelle lebendig ist.
	 *
	 * @param	x	Die X-Koordinate
	 * @param	y	Die Y-Koordinate
	 */

	public boolean isAlive(int x, int y) {
		return field[y][x] == true ? true : false;
	}

	/**
	 * Erzeuge eine neue Generation.
	 */
	public void cycle() {
		generation++;
		System.out.println("Generation " + generation + " wird berechnet.");

		copyField(field, tmpField);

		int tmp;
		for(int y = 0; y < this.getHeight(); ++y) { // Zeilen
			for(int x = 0; x < this.getWidth(); ++x) { // Spalten
				tmp = 0;

				// Beachten, dass das Koordinatensysstem auf dem Kopf steht
				// Oben Links
				if (isAlive(checkWidth(x-1), checkHeight(y-1)) == true) ++tmp;
				// Oben
				if (isAlive(checkWidth(x), checkHeight(y-1)) == true) ++tmp;
				// Oben Rechts
				if (isAlive(checkWidth(x+1), checkHeight(y-1)) == true) ++tmp;
				// Links
				if (isAlive(checkWidth(x-1), checkHeight(y)) == true) ++tmp;
				// Rechts
				if (isAlive(checkWidth(x+1), checkHeight(y)) == true) ++tmp;
				// Unten Links
				if (isAlive(checkWidth(x-1), checkHeight(y+1)) == true) ++tmp;
				// Unten
				if (isAlive(checkWidth(x), checkHeight(y+1)) == true) ++tmp;
				// Unten Rechts
				if (isAlive(checkWidth(x+1), checkHeight(y+1)) == true) ++tmp;

				/**
				 * Weniger als 2 oder mehr als 3 Nachbarn => Zelle stribt
				 * 2 Nachbarn => Keine Änderung
				 * 3 Nachbarn => Zeller erwacht zum Leben (falls bereits
				 *				 lebendig, dann keine Änderung)
				 */
				if (isAlive(x, y) && (tmp < 2 || tmp > 3)) {
					tmpField[y][x] = false;
				} else if (tmp == 3) {
					tmpField[y][x] = true;
				}
			}
		}

		copyField(tmpField, field);

		setChanged();
		notifyObservers();
	}

	/**
	 * Gibt bei über- bzw. unterschreiten der Spielfeldbreite 0 oder
	 * "spielfeldbreite" zurück. Torusförmige Welt.
	 */
	private int checkWidth(int x) {
		if (x < 0) return getWidth() - 1;
		else if (x >= getWidth()) return 0;
		else return x;
	}

	/**
	 * Gibt bei über- bzw. unterschreiten der Spielfeldhöhe 0 oder
	 * "spielfeldhöhe" zurück. Torusförmige Welt.
	 */
	private int checkHeight(int y) {
		if (y < 0) return getHeight() - 1;
		else if (y >= getHeight()) return 0;
		else return y;
	}

	private void copyField(boolean[][] src, boolean[][] dest) {
		for(int i = 0; i < src.length; i++) {
			for(int j = 0; j < src[i].length; j++) {
				dest[i][j] = src[i][j];
			}
		}
		/**	Alte Version
		for(int i = 0; i < dest.length; i++) {
			dest[i] = src[i].clone();
		}
		*/
	}

	private void copyForm(boolean[][] src) {
		// Neues und sauberes Spielfeld (um keine Reste zu haben)
		boolean[][] formTMP = new boolean[getHeight()][getWidth()];

		copyField(src, formTMP);	// Lade Form in neues Spielfeld
		copyField(formTMP, field);	// Überschreibe aktuelles Spielfeld
		copyField(formTMP, tmpField);	// Überschreibe TMP Spielfeld
	}

	public void loadForm(String form) {

		pause();
		switch(form) {
			case ("Gleiter"):
				System.out.println("Spiel " + getGameID()
					+ ": Lade -> " + form);
				copyForm(gleiter);
				break;
			case ("Segler1"):
				System.out.println("Spiel " + getGameID()
					+ ": Lade -> " + form);
				copyForm(segler1);
				break;
			case ("Zerfall"):
				System.out.println("Spiel " + getGameID()
					+ ": Lade -> " + form);
				copyForm(zerfall);
				break;
			case ("r-Pentomino"):
				System.out.println("Spiel " + getGameID()
					+ ": Lade -> " + form);
				copyForm(r_Pentomino);
				break;
			default:
				System.out.println("Modus nicht bekannt!");
		}
		resume();

		setChanged();
		notifyObservers();
	}

	private boolean[][] gleiter = new boolean[][] {
		{false},
		{false, false, true, false},
		{false, false, false, true},
		{false, true, true, true},};

	private boolean[][] segler1 = new boolean[][] {
		{false},
		{false},
		{false},
		{false},
		{false},
		{false, false, true, true, true, true},
		{false, true, false, false, false, true},
		{false, false, false, false, false, true},
		{false, true, false, false, true, false},};

	private boolean[][] zerfall = new boolean[][] {
		{false},
		{false},
		{false},
		{false},
		{false},
		{false, false, false, false, false, true, true, true},
		{false, false, false, false, false, true, false, true},
		{false, false, false, false, false, true, false, true},
		{false},
		{false, false, false, false, false, true, false, true},
		{false, false, false, false, false, true, false, true},
		{false, false, false, false, false, true, true, true},};

	private boolean[][] r_Pentomino = new boolean[][] {
		{false},
		{false},
		{false},
		{false},
		{false},
		{false, false, false, false, false, false, true, true},
		{false, false, false, false, false, true, true, false},
		{false, false, false, false, false, false, true, false},};
}
