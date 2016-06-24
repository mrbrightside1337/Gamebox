/**
 * GoLView.java
 *
 * Das Kindfenster mit dem View auf das Spiel
 *
 * @author Anton Makarow (191721)
 * @author Timo Appenzeller (191382)
 * @author Laura Lohmann (191529)
 *
 * @date 24.06.2016
 */

import java.awt.Color;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.Point;
import java.util.*;

import javax.swing.*;

class GoLView extends JInternalFrame implements Observer {
	private GoLViewController controller;
	private GoLModel currentGame;
	private BaseFrame parrentFrame;

	private JScrollPane scrollPane;
	private View view;
	private double zoom = 15;
	private Color aliveColor = new Color(0, 0, 0);
	private Color deadColor = new Color(255, 255, 255);
	private Color gridColor = new Color(192, 192, 192);
	private int mode = 0; // 0: Laufen, 1: Setzen, 2: Malen

	/**
	 * @param	game	Das Spiel das in diesem Frame gezeichnet werden soll
	 */
	public GoLView(BaseFrame parrent, GoLModel game) {
		// JInternalFrame(resizable, closable, maximizable, iconifiable)
		super("Spiel " + game.getGameID(), true, true, true, true);
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		setSize(400, 300);

		// Der Offset von 55px bei der Höhe ist wegen dem Menü und der Kopzeile
		setLocation(new Random().nextInt(parrent.getWidth() - getWidth()),
			new Random().nextInt(parrent.getHeight() - getHeight() - 55));

		setLayout(new BorderLayout());

		currentGame = game;
  		parrentFrame = parrent;

		controller = new GoLViewController(this);

		view = new View();

		scrollPane = new JScrollPane(view);
		add(scrollPane, BorderLayout.CENTER);

  		game.addObserver(this);

		addInternalFrameListener(controller.getFrameListener(game));
		view.addMouseListener(controller.getMouseLaufenListener());

		setJMenuBar(createMenu());

  		show();
	}

	public int getCurrentGameID() {
		return currentGame.getGameID();
	}

	public BaseFrame getParrentFrame() {
		return parrentFrame;
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

	public void update(Observable obs, Object arg) {
		if (obs == currentGame) view.repaint();
		//System.out.println("arg: " + arg);
		if (Objects.equals("CLOSE", arg)) {
			dispose();
		}
	}

	public void setMode(int m) {
		mode = m;

		// Entferne zuerst alle bisherigen MouseListener
		MouseListener[] mouseListeners =
			view.getMouseListeners();

		for(MouseListener ml : mouseListeners) {
			view.removeMouseListener(ml);
		}

		MouseMotionListener[] mouseMotionListeners =
			view.getMouseMotionListeners();

		for(MouseMotionListener ml : mouseMotionListeners) {
			view.removeMouseMotionListener(ml);
		}

		// Setze den neuen MouseListener
		switch(mode) {
			case(0):
				view.addMouseListener(controller.getMouseLaufenListener());
				break;
			case(1):
				view.addMouseListener(controller.getMouseSetzenListener());
				break;
			case(2):
				view.addMouseMotionListener(controller.getMouseMalenListener());
				break;
			default:
		}
	}

	/**
	 * Gibt die Zelle als Punkt zurück die den Pixelkoordinaten x und y
	 * entspricht
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

	/**
	 * Der View selbst ist in dieser Unterklasse. Das macht es etwas einfacher
	 * und übersichtlicher die "paintComponent" Methode zu überschreiben.
	 */
	class View extends JPanel {

		public View () {
			setPreferredSize(new Dimension(
				(int)(currentGame.getWidth() * zoom),
				(int)(currentGame.getHeight() * zoom)));
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
	}

	/**
	 * Menüleiste für die Unterfenster
	 */
	private JMenuBar createMenu() {
		JMenuBar	menuBar = new JMenuBar();
		JMenu		menu;
		JMenuItem	menuItem;
		JMenuItem	subMenuItem;

		// **** Fenster ****
		menu = new JMenu("Fenster");
		menuItem = new JMenuItem("Neue Ansicht");
		menuItem.addActionListener(
			parrentFrame.getBaseFrameController().getNewViewListener(currentGame));
		menu.add(menuItem);
		menuItem = new JMenuItem("Kopie erstellen");
		menuItem.addActionListener(
			parrentFrame.getBaseFrameController().getNewGameCopyListener(currentGame));
		menu.add(menuItem);

		menuBar.add(menu);

		// **** Modus ****
		menu = new JMenu("Modus");
		menuItem = new JMenuItem("Laufen");
		menuItem.addActionListener(controller.getModeListener(currentGame,
			"LAUFEN"));
		menu.add(menuItem);
		menuItem = new JMenuItem("Setzen");
		menuItem.addActionListener(controller.getModeListener(currentGame,
			"SETZEN"));
		menu.add(menuItem);
		menuItem = new JMenuItem("Malen");
		menuItem.addActionListener(controller.getModeListener(currentGame,
			"MALEN"));
		menu.add(menuItem);
		menuBar.add(menu);

		// **** Geschwindigkeit ****
		menu = new JMenu("Geschwindigkeit");
		menuItem = new JMenuItem("10s");
		menuItem.addActionListener(controller.getDelayListener(currentGame,
			10000));
		menu.add(menuItem);
		menuItem = new JMenuItem("2s");
		menuItem.addActionListener(controller.getDelayListener(currentGame,
			2000));
		menu.add(menuItem);
		menuItem = new JMenuItem("1s");
		menuItem.addActionListener(controller.getDelayListener(currentGame,
			1000));
		menu.add(menuItem);
		menuItem = new JMenuItem("0,5s");
		menuItem.addActionListener(controller.getDelayListener(currentGame,
			500));
		menu.add(menuItem);
		menuItem = new JMenuItem("0,25s");
		menuItem.addActionListener(controller.getDelayListener(currentGame,
			250));
		menu.add(menuItem);

		menu.addSeparator();

		menuItem = new JMenuItem("Pause");
		menuItem.addActionListener(controller.getDelayListener(currentGame,
			0));
		menu.add(menuItem);

		menuBar.add(menu);

		// **** Farben **** mit Untermenüs "Lebend", "Tot" und "Gitter"
		menu = new JMenu("Farben");

		subMenuItem = new JMenu("Lebend"); // Farben für lebendige Zellen
		menuItem = new JMenuItem("Rot");
		menuItem.addActionListener(controller.getAliveColorListener(
			new Color(255, 0 ,0)));
		subMenuItem.add(menuItem);
		menuItem = new JMenuItem("Grün");
		menuItem.addActionListener(controller.getAliveColorListener(
			new Color(0, 255 ,0)));
		subMenuItem.add(menuItem);
		menuItem = new JMenuItem("Blau");
		menuItem.addActionListener(controller.getAliveColorListener(
			new Color(0, 0 ,255)));
		subMenuItem.add(menuItem);
		menuItem = new JMenuItem("Schwarz");
		menuItem.addActionListener(controller.getAliveColorListener(
			new Color(0, 0 ,0)));
		subMenuItem.add(menuItem);
		menuItem = new JMenuItem("Weiß");
		menuItem.addActionListener(controller.getAliveColorListener(
			new Color(255, 255 ,255)));
		subMenuItem.add(menuItem);

		menuItem = new JMenuItem("Farbe wählen...");
		menuItem.addActionListener(controller.getColorChooserListener("alive"));
		subMenuItem.add(menuItem);
		menu.add(subMenuItem);

		subMenuItem = new JMenu("Tot"); // Farben für tote Zellen
		menuItem = new JMenuItem("Rot");
		menuItem.addActionListener(controller.getDeadColorListener(
			new Color(255, 0 ,0)));
		subMenuItem.add(menuItem);
		menuItem = new JMenuItem("Grün");
		menuItem.addActionListener(controller.getDeadColorListener(
			new Color(0, 255 ,0)));
		subMenuItem.add(menuItem);
		menuItem = new JMenuItem("Blau");
		menuItem.addActionListener(controller.getDeadColorListener(
			new Color(0, 0 ,255)));
		subMenuItem.add(menuItem);
		menuItem = new JMenuItem("Schwarz");
		menuItem.addActionListener(controller.getDeadColorListener(
			new Color(0, 0 ,0)));
		subMenuItem.add(menuItem);
		menuItem = new JMenuItem("Weiß");
		menuItem.addActionListener(controller.getDeadColorListener(
			new Color(255, 255 ,255)));
		subMenuItem.add(menuItem);

		menuItem = new JMenuItem("Farbe wählen...");
		menuItem.addActionListener(controller.getColorChooserListener("dead"));
		subMenuItem.add(menuItem);
		menu.add(subMenuItem);

		subMenuItem = new JMenu("Gitter"); // Farben für den Rahmen
		menuItem = new JMenuItem("Schwarz");
		menuItem.addActionListener(controller.getGridColorListener(
			new Color(0, 0 ,0)));
		subMenuItem.add(menuItem);
		menuItem = new JMenuItem("Weiß");
		menuItem.addActionListener(controller.getGridColorListener(
			new Color(255, 255 ,255)));
		subMenuItem.add(menuItem);
		menuItem = new JMenuItem("Grau");
		menuItem.addActionListener(controller.getGridColorListener(
			new Color(192, 192 ,192)));
		subMenuItem.add(menuItem);
		menu.add(subMenuItem);

		menuItem = new JMenuItem("Farbe wählen...");
		menuItem.addActionListener(controller.getColorChooserListener("grid"));
		subMenuItem.add(menuItem);
		menuBar.add(menu);

		// **** Formen ****
		menu = new JMenu("Formen");
		menuItem = new JMenuItem("Gleiter");
		menuItem.addActionListener(controller.getLoadFormListener(currentGame,
			"Gleiter"));
		menu.add(menuItem);
		menuItem = new JMenuItem("Segler1");
		menuItem.addActionListener(controller.getLoadFormListener(currentGame,
			"Segler1"));
		menu.add(menuItem);
		menuItem = new JMenuItem("Zerfall");
		menuItem.addActionListener(controller.getLoadFormListener(currentGame,
			"Zerfall"));
		menu.add(menuItem);
		menuItem = new JMenuItem("r-Pentomino");
		menuItem.addActionListener(controller.getLoadFormListener(currentGame,
			"r-Pentomino"));
		menu.add(menuItem);
		menuBar.add(menu);

		return menuBar;
	}
}
