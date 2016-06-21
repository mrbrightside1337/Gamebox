/**
 * Child.java
 *
 * Das Kindfenster mit dem View auf das Spiel
 *
 * @author Anton Makarow (191721)
 * @author Timo Appenzeller (191382)
 * @author Laura Lohmann (191529)
 */

import java.awt.Color;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.*;

class GoLView extends JInternalFrame {
	private GoLViewController controller = new GoLViewController(this);
	private GoLModel currentGame;
	private BaseFrame parrentFrame;
	private GraphicsView view;

	private JScrollPane scrollPane;

	/**
	 * @param	game	Das Spiel das in diesem Frame gezeichnet werden soll
	 */
	public GoLView(BaseFrame parrent, GoLModel game) {
		// JInternalFrame(resizable, closable, maximizable, iconifiable)
		super("Spiel " + game.getGameID(), true, true, true, true);
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		setSize(400, 300);
		setLocation(0,0);
		setLayout(new GridBagLayout());
		addInternalFrameListener(controller.getFrameListener(game));

  		currentGame = game;
  		parrentFrame = parrent;

		view = new GraphicsView(this, game);
		game.addObserver(view);

		// Die ScrollPane hat ein JPanel in dem der GraphicsView angezeigt wird.
		// So wird der Inhalt automatisch zentriert wenn man die Fenster-
		// größe ändert.
		JPanel panel = new JPanel();
		panel.add(view);
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(panel);

		setContentPane(scrollPane);

		setJMenuBar(createMenu());

  		show();
	}

	public int getCurrentGameID() {
		return currentGame.getGameID();
	}

	public BaseFrame getParrentFrame() {
		return parrentFrame;
	}

	/**
	 * @return	Der GraphicsView dieses Frames
	 */
	public GraphicsView getView() {
		return view;
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
		menuItem.addActionListener(controller.getSetDelayListener(currentGame,
			10000));
		menu.add(menuItem);
		menuItem = new JMenuItem("2s");
		menuItem.addActionListener(controller.getSetDelayListener(currentGame,
			2000));
		menu.add(menuItem);
		menuItem = new JMenuItem("1s");
		menuItem.addActionListener(controller.getSetDelayListener(currentGame,
			1000));
		menu.add(menuItem);
		menuItem = new JMenuItem("0,5s");
		menuItem.addActionListener(controller.getSetDelayListener(currentGame,
			500));
		menu.add(menuItem);
		menuItem = new JMenuItem("0,25s");
		menuItem.addActionListener(controller.getSetDelayListener(currentGame,
			250));
		menu.add(menuItem);

		menu.addSeparator();

		menuItem = new JMenuItem("Pause");
		menuItem.addActionListener(controller.getSetDelayListener(currentGame,
			0));
		menu.add(menuItem);

		menuBar.add(menu);

		// **** Farben **** mit Untermenüs "Lebend", "Tot" und "Gitter"
		menu = new JMenu("Farben");

		subMenuItem = new JMenu("Lebend"); // Farben für lebendige Zellen
		menuItem = new JMenuItem("Rot");
		menuItem.addActionListener(controller.getSetAliveColorListener(view,
			new Color(255, 0 ,0)));
		subMenuItem.add(menuItem);
		menuItem = new JMenuItem("Grün");
		menuItem.addActionListener(controller.getSetAliveColorListener(view,
			new Color(0, 255 ,0)));
		subMenuItem.add(menuItem);
		menuItem = new JMenuItem("Blau");
		menuItem.addActionListener(controller.getSetAliveColorListener(view,
			new Color(0, 0 ,255)));
		subMenuItem.add(menuItem);
		menuItem = new JMenuItem("Schwarz");
		menuItem.addActionListener(controller.getSetAliveColorListener(view,
			new Color(0, 0 ,0)));
		subMenuItem.add(menuItem);
		menuItem = new JMenuItem("Weiß");
		menuItem.addActionListener(controller.getSetAliveColorListener(view,
			new Color(255, 255 ,255)));
		subMenuItem.add(menuItem);

		menuItem = new JMenuItem("Farbe wählen...");
		menuItem.addActionListener(controller.getSetColorChooserListener(view,"alive"));
		subMenuItem.add(menuItem);
		menu.add(subMenuItem);

		subMenuItem = new JMenu("Tot"); // Farben für tote Zellen
		menuItem = new JMenuItem("Rot");
		menuItem.addActionListener(controller.getSetDeadColorListener(view,
			new Color(255, 0 ,0)));
		subMenuItem.add(menuItem);
		menuItem = new JMenuItem("Grün");
		menuItem.addActionListener(controller.getSetDeadColorListener(view,
			new Color(0, 255 ,0)));
		subMenuItem.add(menuItem);
		menuItem = new JMenuItem("Blau");
		menuItem.addActionListener(controller.getSetDeadColorListener(view,
			new Color(0, 0 ,255)));
		subMenuItem.add(menuItem);
		menuItem = new JMenuItem("Schwarz");
		menuItem.addActionListener(controller.getSetDeadColorListener(view,
			new Color(0, 0 ,0)));
		subMenuItem.add(menuItem);
		menuItem = new JMenuItem("Weiß");
		menuItem.addActionListener(controller.getSetDeadColorListener(view,
			new Color(255, 255 ,255)));
		subMenuItem.add(menuItem);

		menuItem = new JMenuItem("Farbe wählen...");
		menuItem.addActionListener(controller.getSetColorChooserListener(view,"dead"));
		subMenuItem.add(menuItem);
		menu.add(subMenuItem);

		subMenuItem = new JMenu("Gitter"); // Farben für den Rahmen
		menuItem = new JMenuItem("Schwarz");
		menuItem.addActionListener(controller.getSetGridColorListener(view,
			new Color(0, 0 ,0)));
		subMenuItem.add(menuItem);
		menuItem = new JMenuItem("Weiß");
		menuItem.addActionListener(controller.getSetGridColorListener(view,
			new Color(255, 255 ,255)));
		subMenuItem.add(menuItem);
		menuItem = new JMenuItem("Grau");
		menuItem.addActionListener(controller.getSetGridColorListener(view,
			new Color(192, 192 ,192)));
		subMenuItem.add(menuItem);
		menu.add(subMenuItem);

		menuItem = new JMenuItem("Farbe wählen...");
		menuItem.addActionListener(controller.getSetColorChooserListener(view,"grid"));
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

		/*
		 * Evtl. später. Im Moment buggy. (View wird nicht richtig größer)
		 *
		menu = new JMenu("Zoom"); // Ändere Zoomlevel
		menuItem = new JMenuItem("x20");
		menuItem.addActionListener(controller.getSetZoomListener(view, 20));
		menu.add(menuItem);
		menuItem = new JMenuItem("x15");
		menuItem.addActionListener(controller.getSetZoomListener(view, 15));
		menu.add(menuItem);
		menuItem = new JMenuItem("x10");
		menuItem.addActionListener(controller.getSetZoomListener(view, 10));
		menu.add(menuItem);
		menuItem = new JMenuItem("x5");
		menuItem.addActionListener(controller.getSetZoomListener(view, 5));
		menu.add(menuItem);
		menuBar.add(menu);
		*/

		return menuBar;
	}
}
