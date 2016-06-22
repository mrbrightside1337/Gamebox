/**
 * QuadSpielfeld.java
 *
 * @author Timo Appenzeller, 191382
 */

import java.util.Observable;

public class QuodSpielfeld extends Observable {
	
	/**
	 * spielfeld[Y][X]
	 * Wert 0 = leeres Feld
	 * Wert 1 = Spieler 1
	 * Wert 2 = Spieler 2
	 * Wert -1 = neutraler Stein
	 * Wert 7 = Siegstein
	 */
	private int[][] spielfeld;
	
	/**
	 * Konstruktor.
	 * Initialisiert das Spielfeld.
	 */
	public QuodSpielfeld(){
		spielfeld = new int[11][11];
	}
	
	/**
	 * Get-Spielfeld.
	 * @return gibt eine Referenz zum  Spielfeld zurück.
	 */
	public int[][] getSpielfeld(){
		return spielfeld;
	}
	
	/**
	 * Get-Feld.
	 * Gibt Inhalt eines Feldes auf dem Spielfeld zurück.
	 * @param x X-Koordinate
	 * @param y Y-Koordinate
	 * @return Inhalt an der Stelle x,y des Spielfelds.
	 */
	public int getFeld(int x, int y){
		return spielfeld[y][x];
	}
	
	/**
	 * Gibt die Höhe des Spielfelds zurück.
	 * @return Höhe des Spielfelds.
	 */
	public int getHeight(){
		return spielfeld.length;
	}
	
	/**
	 * Gibt die Breite des Spielfelds zurück.
	 * @return Breite des Spielfelds.
	 */
	public int getWidth(){
		return spielfeld[0].length;
	}
	
	/**
	 * Eckpunkte check
	 * @param x X-Koordinate
	 * @param y Y-Koordinate
	 * @return true, wenn (x,y) ein Eckpunkt ist ; false, wenn (x,y) kein Eckpunkt ist
	 */
	public boolean zelleIsForbidden(int x, int y){
		if(((x==0 && y == 0) || (x==getWidth()-1 && y==getHeight()-1) || (x==getWidth()-1 && y==0) || (x==0 && y==getHeight()-1))){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Checkt ob die Zelle (x,y) auf dem Spielfeld existiert.
	 * @param x X-Koordinate
	 * @param y Y-Koordinate
	 * @return true, wenn (x,y) eine gültige Zelle auf dem Spielfeld ist; 
	 * 			false, wenn (x,y) keine gültige Zelle auf dem Spielfeld ist.
	 */
	public boolean zelleIsOnBoard(int x, int y){
		if(zelleIsForbidden(x, y)){
			return false;
		}
		else if(x>=0 && x<getWidth() && y>=0 && y<getHeight()){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Prüft, ob die Zelle (x,y) belegbar ist.
	 * @param x X-Koordinate
	 * @param y Y-Koordinate
	 * @return true, wenn Zelle belegbar ist ; false, wenn Zelle nicht belegbar ist.
	 */
	public boolean zelleIsEmpty(int x, int y){
		if(zelleIsForbidden(x, y)){
			return false;
		}
		else if(getFeld(x, y)!=0){
			return false;
		}
		else{
			return true;
		}
	}
	
	/**
	 * Setzt einen Wert auf eine Zelle (x,y)
	 * @param x X-Koordinate
	 * @param y Y-Koordinate
	 * @param value Wert, der auf Zelle (x,y) gesetzt werden soll
	 */
	public void setFeld(int x, int y, int value){
		//wenn nicht in den Ecken
		if(!((x==0 && y == 0) || (x==getWidth()-1 && y==getHeight()-1) || (x==getWidth()-1 && y==0) || (x==0 && y==getHeight()-1))){
			spielfeld[y][x] = value;
			setChanged();
			notifyObservers();
		}
		else{
			System.err.println("Fehler: Spielfeld ohne Ecken!");
		}
		
	}

	/**
	 * Gibt das Spielfeld auf der Konsole aus.
	 */
	public void printSpielfeld(){
		System.out.print("\nZeichne Spielfeld:\n");
		for (int i = 0; i < spielfeld.length; i++) {
			for (int j = 0; j < spielfeld[0].length; j++) {
				if(!((j==0 && i == 0) || (j==getWidth()-1 && i==getHeight()-1) || (j==getWidth()-1 && i==0) || (j==0 && i==getHeight()-1))){
					System.out.print(spielfeld[i][j]);
				}
				else{
					System.out.print("X");
				}
			}
			System.out.print("\n");
		}
	}
	
	/**
	 * Leert alle Felder des Spielfelds.
	 */
	public void clearSpielfeld(){
		spielfeld = new int[11][11];
		setChanged();
		notifyObservers();
	}
}
