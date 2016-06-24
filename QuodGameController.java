/**
 * QuadGameController.java
 *
 * @author Anton Makarow (191721)
 * @author Timo Appenzeller (191382)
 *
 * @date 24.06.2016
 */

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;

public class QuodGameController extends Observable{
	
	private QuodViewPanel panel;
	private QuodSpielfeld spielfeld;
	
	private QuodGameState state;
	
	private QuodSpieler spielerEins;
	private QuodSpieler spielerZwei;
	
	private QuodSpieler aktuellerSpieler;

	/**
	 * Konstruktor
	 * @param panel Panel, das das Spielfeld zeigt
	 * @param sf Referenz zum Spielfeld
	 */
	public QuodGameController(QuodViewPanel panel, QuodSpielfeld sf) {
		this.panel = panel;
		spielfeld = sf;
		state = QuodGameState.P1;
		spielerEins = new QuodSpieler(1);
		spielerZwei = new QuodSpieler(2);
		aktuellerSpieler = spielerEins;
	}
	
	/**
	 * mouseClicked() Methode identifiziert geklickte Zellen und startet die Ausführung eines Spielzugs.
	 * @return MouseAdapter zum Verarbeiten von Mouse-Events
	 */
	public MouseAdapter getMouseListener() {
		return new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Point mousePt = e.getPoint();
				
				Point coordinates = getGridCell(mousePt.getX(), mousePt.getY());
				
				int x = (int)coordinates.getX();
				int y = (int)coordinates.getY();
				
				//Wenn das Spiel noch nicht beendet ist (durch Win oder Draw)
				if (state!=QuodGameState.WinP1 && state!=QuodGameState.WinP2 && state!=QuodGameState.Draw) {
					spielzugDurchfuehren(x, y, e.getButton());
				}
			}
		};
	}
	
	/**
	 * Führt einen Spielzug aus.
	 * 1. Stein setzen und falls erfolgreich Spieler wechseln
	 * 2. Prüfen ob Spiel beendet ist (durch sieg oder unentschieden) oder noch weiter geht
	 * @param x X-Koordinate der geklickten Zelle
	 * @param y Y-Kooridinte der geklickten Zelle
	 * @param button Maustaste (1=links, 3=rechts)
	 */
	private void spielzugDurchfuehren(int x, int y, int button){
		if(setzeStein(x, y, button)){
			
			//Wenn Quad gesetzt wurde und dadurch das Spiel gewonnen wurde
			if(button==1 && checkWin(x, y)){
				if (state == QuodGameState.P1) {
					state = QuodGameState.WinP1;
				}
				else if(state == QuodGameState.P2){
					state = QuodGameState.WinP2;
				}
				setChanged();
				notifyObservers();
			}
			
			//Sofern keine Quads mehr vorhanden sind
			else if(button==1 && keineQuadsMehr()){
				if(spielerEins.getAnzahlQuasare()>spielerZwei.getAnzahlQuasare()){
					state = QuodGameState.WinP1;
				}
				else if(spielerZwei.getAnzahlQuasare()>spielerEins.getAnzahlQuasare()){
					state = QuodGameState.WinP2;
				}
				else{
					state = QuodGameState.Draw;
				}
				setChanged();
				notifyObservers();
			}
			
			//Quad wurde gesetzt -> Spielzug ist zuende
			if(button==1){
				nextPlayer();
			}
		}
		else{
			//Stein konnte nicht gesetzt werden
		}
		
	}
	
	/**
	 * Setzt einen Stein auf das Spielfeld.
	 * @param x X-Koordinate auf welche der Stein gesetzt werden soll
	 * @param y Y-Koordinate auf welche der Stein gesetzt werden soll
	 * @param button Mausetaste (1=links oder 3=rechts) entscheidet welcher Stein gesetzt wird (Quad oder Quasar) 
	 * @return
	 */
	private boolean setzeStein(int x, int y, int button){
		
		if(spielfeld.zelleIsEmpty(x, y)){
			if(button==1){//Linksklick
				if(aktuellerSpieler.getAnzahlQuods()>0){
					spielfeld.setFeld(x, y, aktuellerSpieler.getNummer());
					aktuellerSpieler.reduziereQuods();
					return true;
				}
				else{
					//Keine Quads mehr zur Verfügung
					System.err.println("Spieler "+aktuellerSpieler.getNummer()+" hat keine Quads mehr.");
					return false;
				}
			}
			else if(button==3){//Rechtsklick
				if(aktuellerSpieler.getAnzahlQuasare()>0){
					spielfeld.setFeld(x, y, -1);
					aktuellerSpieler.reduziereQuasare();
					return true;
				}
				else{
					//Keine Quasare mehr zur Verfügung
					System.err.println("Spieler "+aktuellerSpieler.getNummer()+" hat keine Quasare mehr.");
					return false;
				}
			}
			else{
				//button ist nicht 1 oder 3
				System.err.println("Maustaste war nicht links (1) oder rechts (3)");
				return false;
			}
		}
		else{
			//Zelle besetzt oder Ecke --> Zug nicht möglich
			System.err.println("Zelle nicht belegbar");
			return false;
		}
	}
	
	/**
	 * Prüft, ob durch das Setzen eines Quods an einer bestimmten Position ein Quadrat gebildet wurde.
	 * @param x X-Koordinate des gesetzten Quods
	 * @param y Y-Koordinate des gesetzten Quods
	 * @return true, wenn Quadrat gebildet wurde ; false, wenn kein Quadrat gebildet wurde
	 */
	private boolean checkWin(int x, int y){
		int spielerNummer = aktuellerSpieler.getNummer();
		int[][] feldArray = spielfeld.getSpielfeld();
		
		int dx, dy;
		
		for (int i = 0; i < feldArray.length; i++) {
			for (int j = 0; j < feldArray.length; j++) {
				if(!(i==y && j==x) && feldArray[i][j]==spielerNummer){
					dx=j-x;
					dy=i-y;
					
					int dxNeu = dy;
					int dyNeu = dx*-1;
					if(winCheckHelper(x, y, j, i, dxNeu, dyNeu, spielerNummer)){
						return true;
					}
					
					dxNeu = dy*-1;
					dyNeu = dx;
					if(winCheckHelper(x, y, j, i, dxNeu, dyNeu, spielerNummer)){
						return true;
					}
				}
				
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param j
	 * @param i
	 * @param dx
	 * @param dy
	 * @param spielerNummer
	 * @return
	 */
	private boolean winCheckHelper(int x, int y, int j, int i, int dx, int dy, int spielerNummer){
		int anzahl=0;
		
		if(spielfeld.zelleIsOnBoard(x+dx, y+dy)){
			if(spielfeld.getFeld(x+dx, y+dy)==spielerNummer){
				anzahl++;
			}
		}
		if(spielfeld.zelleIsOnBoard(j+dx, i+dy)){
			if(spielfeld.getFeld(j+dx, i+dy)==spielerNummer){
				anzahl++;
			}
		}
		if (anzahl==2) {
			spielfeld.setFeld(x, y, 7);
			spielfeld.setFeld(j, i, 7);
			spielfeld.setFeld(x+dx, y+dy, 7);							
			spielfeld.setFeld(j+dx, i+dy, 7);
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Prüft, ob die Spieler noch Quods zum Setzen haben.
	 * @return true, wenn Spieler noch Quods besitzen ; false, wenn alle Quods verbraucht worden sind.
	 */
	private boolean keineQuadsMehr(){
		return (spielerEins.getAnzahlQuods()+spielerZwei.getAnzahlQuods()==0);
	}
	
	/**
	 * Spielerwechsel von Spieler 1 auf Spieler 2 und umgekehrt
	 */
	private void nextPlayer(){
		if(state==QuodGameState.P1){
			state=QuodGameState.P2;
			aktuellerSpieler=spielerZwei;
			setChanged();
			notifyObservers();
		}
		else if(state==QuodGameState.P2){
			state=QuodGameState.P1;
			aktuellerSpieler=spielerEins;
			setChanged();
			notifyObservers();
		}
	}
	
	/**
	 * Berechnet welche in welcher Zelle sich die Maus beim klicken befand.
	 * @param x X-Koordinate der Position des Mauszeigers beim Klick
	 * @param y Y-Koordinate der Position des Mauszeigers beim Klick
	 * @return Punkt mit X- und Y-Koordinate einer Zelle
	 */
	private Point getGridCell(double x, double y) {
		int xCell = (int)(x / panel.getZoom());
		int yCell = (int)(y / panel.getZoom());
		//System.out.println("Zelle: " + xCell + "/" + yCell);
		return new Point(xCell, yCell);
	}
	
	/**
	 * Gibt eine Referenz auf Spieler i zurück
	 * @param i Spielernummer
	 * @return Referenz zu Spieler i ; null, wenn i nicht 1 oder 2
	 */
	public QuodSpieler getSpieler(int i){
		if(i==1){
			return spielerEins;
		}
		else if(i==2){
			return spielerZwei;
		}
		else{
			return null;
		}
	}
	
	/**
	 * Gibt den aktuellen GameState zurück
	 * @return aktueller GameState
	 */
	public QuodGameState getGameState(){
		return state;
	}
	
	/**
	 * Setzt das Spiel zurück.
	 * Spieler 1 am Zug,
	 * Spielfeld leer,
	 * Spielsteine an Spieler verteilt
	 */
	public void restartGame(){
		state = QuodGameState.P1;
		aktuellerSpieler = spielerEins;
		setChanged();
		notifyObservers();
		spielfeld.clearSpielfeld();
		spielerEins.reset();
		spielerZwei.reset();
	}

}
