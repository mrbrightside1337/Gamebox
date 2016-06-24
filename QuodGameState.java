/**
 * QuadGameState.java
 *
 * Die Zustände die das Spiel "Quod" annehmen kann
 *
 * @author Anton Makarow (191721)
 * @author Timo Appenzeller (191382)
 *
 * @date 24.06.2016
 */

public enum QuodGameState {
	
	P1, // Spieler 1 ist am Zug
	P2, // Spieler 2 ist am Zug
	Draw, // Unentschieden
	WinP1, // ein Spieler hat gewonnen
	WinP2
}
