/**
 * QuadViewPanel.java
 *
 * @author Timo Appenzeller, 191382
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

public class QuodViewPanel extends JPanel implements Observer {


	private double zoom=50;
	
	private QuodSpielfeld spielfeld;

	private QuodGameController controller;
	
	private Color colorPlayerOne = new Color(255, 0, 0);
	private Color colorPlayerTwo = new Color(0, 0, 255);
	private Color colorEmpty = new Color(255, 255, 255);
	private Color colorNeutral = new Color(100, 100, 100);
	private Color gridColor = new Color(192, 192, 192);
	private Color cornerColor = new Color(238, 238, 238);
	private Color winColor = new Color(0, 255, 0);
	
	/**
	 * 
	 */
	public QuodViewPanel(QuodSpielfeld sf) {
		
		spielfeld = sf;
		
		controller = new QuodGameController(this, spielfeld);
		
		addMouseListener(controller.getMouseListener());
		
		setPreferredSize(new Dimension(
				(int)(sf.getWidth() * zoom),
				(int)(sf.getHeight() * zoom)));
		
		sf.addObserver(this);
	}
	
	/**
	 * Zum Zeichnen des Spielfelds im Panel
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Hilfsrand zur Orientierung
		g.setColor(gridColor);
		g.fillRect(0, 0,
			(int)(spielfeld.getWidth() * zoom),
			(int)(spielfeld.getHeight() * zoom));

		/**
		 * Zeichenroutine
		 */
		for(int y = 0; y < spielfeld.getHeight(); ++y) { // Zeilen (Y-Koordinate)
			for(int x = 0; x < spielfeld.getWidth(); ++x) { // Spalten (X-Koordinate)
				if (spielfeld.zelleIsForbidden(x, y)){//Eckpunkte
					g.setColor(cornerColor);
					g.fillRect((int)((x * zoom)) , (int)((y * zoom)),
					(int)zoom - 1, (int)zoom - 1);
				}
				else if (spielfeld.getFeld(x, y) == 1) {//Steine von Spieler 1
					g.setColor(colorPlayerOne);
					g.fillRect((int)((x * zoom)) , (int)((y * zoom)),
					(int)zoom - 1, (int)zoom - 1);
				} 
				else if(spielfeld.getFeld(x, y) == 2) {//Steine von Spieler 2
					g.setColor(colorPlayerTwo);
					g.fillRect((int)((x * zoom)) , (int)((y * zoom)),
					(int)zoom - 1, (int)zoom - 1);
				}
				else if(spielfeld.getFeld(x, y) == -1) {//neutrale Spielsteine (Quasare)
					g.setColor(colorNeutral);
					g.fillRect((int)((x * zoom)) , (int)((y * zoom)),
					(int)zoom - 1, (int)zoom - 1);
				}
				else if(spielfeld.getFeld(x, y) == 0) {//leere Zellen
					g.setColor(colorEmpty);
					g.fillRect((int)((x * zoom)) , (int)((y * zoom)),
					(int)zoom - 1, (int)zoom - 1);
				}
				else if(spielfeld.getFeld(x, y) == 7) {//leere Zellen
					g.setColor(winColor);
					g.fillRect((int)((x * zoom)) , (int)((y * zoom)),
					(int)zoom - 1, (int)zoom - 1);
				}
			}
		}
		
		//Nur für die Optik. ;)
		g.setColor(cornerColor);
		g.drawLine(getWidth()-1, 0, getWidth()-1, getHeight()-1);
		g.drawLine(0, getHeight()-1, getWidth()-1, getHeight()-1);
	}
	
	/**
	 * Gibt den Zoom-Faktor zurück
	 * @return
	 */
	public double getZoom(){
		return zoom;
	}
	
	public QuodGameController getGameController(){
		return controller;
	}

	/**
	 * Bei Änderungen (nach Spielzügen) soll das Spielfeld neu gezeichnet
	 * werden.
	 */
	public void update(Observable arg0, Object arg1) {
		repaint();
	}

}
