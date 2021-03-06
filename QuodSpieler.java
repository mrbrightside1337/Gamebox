/**
 * QuadSpieler.java
 * 
 * Repräsentiert einen Spieler des Quod Spiels.
 *
 * @author Anton Makarow (191721)
 * @author Timo Appenzeller (191382)
 *
 * @date 24.06.2016
 */

import java.util.Observable;

public class QuodSpieler extends Observable {
	
	private int anzahlQuods, anzahlQuasare, nummer;

	/**
	 * Initialisiert den Spieler mit der Nummer i. 
	 * @param i Spieler Nummer i
	 */
	public QuodSpieler(int i){
		anzahlQuods = 20;
		anzahlQuasare = 6;
		nummer = i;
	}
	
	/**
	 * Liefert die Anzahl Quads, die ein Spieler aktuell besitzt
	 * @return Anzahl Quads
	 */
	public int getAnzahlQuods(){
		return anzahlQuods;
	}
	
	/**
	 * Liefert die Anzahl Quasare, die ein Spieler aktuell besitzt
	 * @return Anzahl Quasare
	 */
	public int getAnzahlQuasare(){
		return anzahlQuasare;
	}
	
	/**
	 * Liefert die Spielernummer.
	 * @return Nummer des Spielers
	 */
	public int getNummer(){
		return nummer;
	}
	
	/**
	 * Verringert die Anzahl Quads, die ein Spieler besitzt, um eins.
	 */
	public void reduziereQuods(){
		if(anzahlQuods>0){
			anzahlQuods--;
			setChanged();
			notifyObservers();
			//System.out.println("Spieler "+nummer+" hat "+anzahlQuads+" Quads");
		}
		else{
			//System.out.println("Spieler "+nummer+" hat keine Quads mehr");
		}
	}
	
	/**
	 * Verringert die Anzahl Quasare, die ein Spieler besitzt, um eins.
	 */
	public void reduziereQuasare(){
		if(anzahlQuods>0){
			anzahlQuasare--;
			setChanged();
			notifyObservers();
			//System.out.println("Spieler "+nummer+" hat "+anzahlQuasare+" Quasare");
		}
		else{
			//System.out.println("Spieler "+nummer+" hat keine Quasare mehr");
		}
	}
	
	/**
	 * Setzt die Spielsteine des Spieler zurück auf die Anfangswerte.
	 * Quods = 20
	 * Quasare = 6
	 */
	public void reset(){
		anzahlQuods = 20;
		anzahlQuasare = 6;
		setChanged();
		notifyObservers();
	}
}
