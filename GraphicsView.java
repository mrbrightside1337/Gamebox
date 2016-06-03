/**
 * GraphicsView.java
 *
 * Controller: GraphicsViewController.java
 *
 * Darstellung des Spielfelds in einem JPanel.
 *
 * @author Anton Makarow (191721)
 * @author Timo Appenzeller (191382)
 * @author Laura Lohmann (191529)
 */

import java.awt.Color;
import java.awt.Dimension;
//import java.awt.event.WindowEvent;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.util.*;

import javax.swing.*;

class GraphicsView extends JPanel implements Observer {
	private GraphicsViewController controller = new GraphicsViewController(this);
	private ChildFrame parrentFrame;
	private GameModel currentGame;

	private double zoom;
	private Color aliveColor = new Color(0, 0, 0);
	private Color deadColor = new Color(255, 255, 255);
	private Color gridColor = new Color(192, 192, 192);
	private int mode = 0; // 0: Laufen, 1: Setzen, 2: Malen


	public GraphicsView(ChildFrame frame, GameModel game) {
		super(new GridBagLayout());

		parrentFrame = frame;
		currentGame = game;

		zoom = 15;

		this.setPreferredSize(new Dimension(
			(int)(currentGame.getWidth() * zoom),
			(int)(currentGame.getHeight() * zoom)));

		// Defalut Modus is Laufen (0)
		this.addMouseListener(controller.getMouseLaufenListener());
	}

	public void update(Observable obs, Object arg) {
		if (obs == currentGame) repaint();
		//System.out.println("arg: " + arg);
		if (Objects.equals("CLOSE", arg)) {
			parrentFrame.dispose();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Hilfsrand zur Orientierung
		g.setColor(gridColor);
		g.fillRect(0, 0,
			(int)(currentGame.getWidth() * zoom),
			(int)(currentGame.getHeight() * zoom));

		/**
		 * Zeichenroutine
		 */
		for(int y = 0; y < currentGame.getHeight(); ++y) { // Zeilen (Y-Koordinate)
			for(int x = 0; x < currentGame.getWidth(); ++x) { // Spalten (X-Koordinate)
				if (currentGame.isAlive(x, y) == true) {
					g.setColor(aliveColor);
					g.fillRect((int)((x * zoom)) , (int)((y * zoom)),
					(int)zoom - 1, (int)zoom - 1);
				} else {
					g.setColor(deadColor);
					g.fillRect((int)((x * zoom)) , (int)((y * zoom)),
					(int)zoom - 1, (int)zoom - 1);
				}
			}
		}
	}

	public void setAliveColor(Color c) {
		aliveColor = c;
		repaint();
	}

	public void setDeadColor(Color c) {
		deadColor = c;
		repaint();
	}

	public void setGridColor(Color c) {
		gridColor = c;
		repaint();
	}

	public void setMode(int m) {
		controller.clearMouseListeners();
		mode = m;

		switch(mode) {
			case(0):
				this.addMouseListener(controller.getMouseLaufenListener());
				break;
			case(1):
				this.addMouseListener(controller.getMouseSetzenListener());
				break;
			case(2):
				this.addMouseMotionListener(controller.getMouseMalenListener());
				break;
			default:
		}
	}

	/**
	 * Gibt die Zelle als Punktzurückden den Pixelkoordinaten x und y
	 * entrspricht
	 *
	 * @param	x	Die X-Kooridanete in px
	 * @param	y	Die Y-Kooridanete in px
	 */
	public Point getGridCell(double x, double y) {
		int xCell = (int)(x / zoom);
		int yCell = (int)(y / zoom);
		//System.out.println("Zelle: " + xCell + "/" + yCell);
		return new Point(xCell, yCell);
	}

	/**
	 * Setzt das Feld x/y im Spiel auf "lebendig"
	 *
	 * @param	x	Die X-Kooridanete
	 * @param	y	Die y-Kooridanete
	 */
	public void setGameField(int x,int y) {
		currentGame.setField(x, y, true);
	}

	/**
	 * Ändert das Feld x/y im Spiel von tot auf lebending bzw. lebendig in tot.
	 *
	 * @param	x	Die X-Kooridanete
	 * @param	y	Die y-Kooridanete
	 */
	public void changeGameField(int x,int y) {
		if(currentGame.isAlive(x, y)) {
			currentGame.setField(x, y, false);
		} else {
			currentGame.setField(x, y, true);
		}
	}

	/*
	 * Evtl. später. Im Moment buggy. (View wird nicht richtig größer)
	 *
	public void setZoom(int z) {
		zoom = z;
		this.setPreferredSize(new Dimension(
			(int)(currentGame.getWidth() * zoom),
			(int)(currentGame.getHeight() * zoom)));

		repaint();
	}
	*/
}
