/**
 * Gamestate.java
 * Beinhaltet eine Enumeration mit Zuständen die das Spiel annehmen kann
 * @author Timo Appenzeller, Anton Makarow
 * @date 15.01.2016
 */

public enum MaxGameState {
	P1,		// Spieler 1 ist am Zug
	P2,		// Spieler 2 ist am Zug
	NEXT,	// Aktueller Spieler hat seinen Zug erfolgreich beendet
	LOOP,	// Aktueller Spieler hat seinen Zug noch nicht beendet
	QUIT	// Beende das Spiel, weil ein Spieler gewonnen hat
}
