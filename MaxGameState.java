/**
 * Gamestate.java
 *
 * Beinhaltet eine Enumeration mit Zuständen die das Spiel annehmen kann
 *
 * @author Anton Makarow (191721)
 * @author Timo Appenzeller (191382)
 *
 * @date 24.06.2016
 */

public enum MaxGameState {
	P1,		// Spieler 1 ist am Zug
	P2,		// Spieler 2 ist am Zug
	NEXT,	// Aktueller Spieler hat seinen Zug erfolgreich beendet
	LOOP,	// Aktueller Spieler hat seinen Zug noch nicht beendet
	QUIT	// Beende das Spiel, weil ein Spieler gewonnen hat
}
