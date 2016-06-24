/**
 * Spieler.java
 *
 * Die Spieler Klasse beinhaltet alle Information zum jeweiligen Spieler. Dazu
 * gehört der aktuelle Punktestand, die Steuerung, die aktuellen Koordinaten, etc
 *
 * @author Anton Makarow (191721)
 * @author Timo Appenzeller (191382)
 *
 * @date 24.06.2016
 */



public class MaxSpieler {

	private String name;
	private int posX, posY; //aktuelle Position
	private int punkte;
	private String up, down, right, left; //Taste für jeweilige Richtung

	private MaxSpielfeld spielfeld;
	private int spX, spY; // Spielfeldgröße

	/**
	 * Konstruktor
	 * @param String n Der Name der Spielers
	 * @param int pX Start X-Koordinate
	 * @param int pY Start Y-Koordinate
	 * @param String up Tastenbelegung für "oben"
	 * @param String down Tastenbelegung für "unten"
	 * @param String left Tastenbelegung für "links"
	 * @param String right Tastenbelegung für "rechts"
	 * @param MaxSpielfeld sp Eine Referenz auf das aktuell gültige Spielfeld
	 */
	public MaxSpieler(String n, int pX, int pY,
								 String up, String down, String left, String right,
								 MaxSpielfeld sp) {
		name = n;
		posX = pX;
		posY = pY;

		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;

		spielfeld = sp;

		String[][] feld = spielfeld.getSpielfeld();
		spX = feld.length;
		spY = feld[0].length;

		spielfeld.setField(posX, posY, " ");
	}
	
	/**
	 * Setzt die Position des Spielers
	 * @param x neue X-Koordinate
	 * @param y neue Y-Koordinate
	 */
	public void spielerPositionSetzen(int x, int y){
		spielfeld.setField(x, y, " ");
		posX = x;
		posY = y;
	}

	/**
	 * Kollisionsabfrage
	 * @return false bei Kollision
	 */
	private boolean checkCollision(int pX, int pY) {
		return (pX < 0 || pX >= spX || pY < 0 || pY >= spY) ? true : false;
	}

	/**
	 * Sammel Punke auf aktuellem Feld
	 */
	private void collectPoints() {
		String p = spielfeld.getField(posX, posY);
		punkte += p.matches("[1-9]") ? Integer.parseInt(p) : 0;
	}

	/**
	 * Prüfe ob Siegbedingung erfüllt ist
	 * @return true Falls Siegbedingung erfüllt
	 * @return false Falls Siegbedingung nicht erfüllt
	 */
	private boolean winCheck() {
		if(punkte >= 105) {
//			System.out.println("Spieler '" + name + "' hat mit " + punkte
//													+ " Punkten GEWONNEN!");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gib die aktuelle X-Koordinate zurück
	 * @return X-Koordinate
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * Gib die aktuelle Y-Koordinate zurück
	 * @return Y-Koordinate
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * Gib den Namen des Spielers zurück
	 * @return Name des Spielers
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gib die Punktzahl des Spieler zurück
	 * @return aktuelle Punktzahl
	 */
	public int getPunkte(){
		return punkte;
	}
	
	/**
	 * Setzt die Punktzahl des Spielers
	 * @param neue Punktzahl
	 */
	public void setPunkte(int punkte){
		this.punkte = punkte;
	}

	/**
	 * update Methode
	 * Ein Spieler ist solange dran bis er einen gültigen Zug ausführt. 
	 * Kollision (mit dem Rand) zählt nicht als gültiger Zug.
	 * @return Der neu Zustand für den weiteren Spielverlauf
	 */
	public MaxGameState update(String c) {
		
		if(c.equals(up)) {
			if(!checkCollision(posX, posY-1)) {
				spielfeld.setField(posX, posY, " ");
				posY--;
				collectPoints();
				return winCheck() ? MaxGameState.QUIT : MaxGameState.NEXT;
			} else {
				return MaxGameState.LOOP;
			}
		}
		else if(c.equals(down)) {
			if(!checkCollision(posX, posY+1)) {
				spielfeld.setField(posX, posY, " ");
				posY++;
				collectPoints();
				return winCheck() ? MaxGameState.QUIT : MaxGameState.NEXT;
			} else {
				return MaxGameState.LOOP;
			}
		}
		else if(c.equals(left)) {
			if(!checkCollision(posX-1, posY)) {
				spielfeld.setField(posX, posY, " ");
				posX--;
				collectPoints();
				return winCheck() ? MaxGameState.QUIT : MaxGameState.NEXT;
			} else {
				return MaxGameState.LOOP;
			}
		} 
		else if(c.equals(right)) {
			if(!checkCollision(posX+1, posY)) {
				spielfeld.setField(posX, posY, " ");
				posX++;
				collectPoints();
				return winCheck() ? MaxGameState.QUIT : MaxGameState.NEXT;
			} else {
				return MaxGameState.LOOP;
			}
		} 
		else {
			return MaxGameState.LOOP;
		}

	}

}
