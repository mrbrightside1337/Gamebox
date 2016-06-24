/**
 * Steuerung.java
 * Enthält die Programmlogik, die den Spielablauf steuert.
 * Dient außerdem als Listener für Key- und ActionEvents die vom GUI kommen.
 * 
 * @author Anton Makarow (191721)
 * @author Timo Appenzeller (191382)
 *
 * @date 24.06.2016
 */

import java.awt.event.*;

public class MaxSteuerung implements ActionListener {
	
	private boolean lock; //Um nur das erste Event nach einem Tastendruck zu verarbeiten
	private MaxSpieler p1,p2;
	private MaxView frame;
	private MaxGameState state; //Der aktuelle Spielstatus
	private MaxGameState result;
	private MaxSpielfeld spielfeld;
	
	public MaxSteuerung(MaxSpieler p1, MaxSpieler p2, MaxView frm, MaxSpielfeld sf){
		
		this.p1 = p1;
		this.p2 = p2;
		this.frame = frm;
		state = MaxGameState.P1;
		spielfeld = sf;
		lock = false;
		
	}
	
	/**
	 * Gibt den aktuellen GameState zurück
	 * @return aktueller GameState
	 */
	public MaxGameState getGameState(){
		return state;
	}
	
	/**
	 * Abwechselnd darf Spieler 1 bzw. Spieler 2 einen Spielzug machen.
	 * Ungültige Spielzüge (Verlassen des Spielfelds / Falsche Taste) gelten nicht als Spielzug.
	 * @param c Tastatureingabe der Bewegungsrichtung
	 */
	public void spielzugAusfuehren(String c){
		switch (state) {
		case P1: //Spieler 1 ist dran
			result = p1.update(c);
			break;
			
		case P2: //Spieler 2 ist dran
			result = p2.update(c);
			break;
			
		default:
			result = state;
			break;
		}
		
		frame.updateFeld();
		
		
		switch (result) {
		case LOOP:
			//Aktueller Spieler bleibt an der Reihe, weil falsche eingabe 
			break;
		
		case NEXT:
			//Spielerwechsel wird ausgeführt
			if(state==MaxGameState.P1){
				state=MaxGameState.P2;
			}
			else if(state==MaxGameState.P2){
				state=MaxGameState.P1;
			}
			break;
			
		case QUIT:
			//Ein Spieler hat gewonnen.
			state=MaxGameState.QUIT;
			break;

		default:
			break;
		}
		
		frame.updateInfo();
		
	}

	/**
	 * Empfängt die ActionEvents vom Menü.
	 */
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Neues Spiel starten")){
			spielfeld.initialisieren();
			p1.spielerPositionSetzen(3, 4);
			p2.spielerPositionSetzen(4, 3);
			p1.setPunkte(0);
			p2.setPunkte(0);
			state=MaxGameState.P1;
			frame.updateInfo();
			frame.updateFeld();
		}
		
		else if(e.getActionCommand().equals("Spiel beenden")){
			frame.dispose();
		}
		
	}

}
