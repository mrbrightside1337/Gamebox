/**
 * BaseFrame.java
 *
 * Controller: BaseFrameController.java
 *
 * Das Basisfenster in dem die internen Fenster (ChildFrame) angezeigt werden.
 *
 * @author Anton Makarow (191721)
 * @author Timo Appenzeller (191382)
 * @author Laura Lohmann (191529)
 */

import java.awt.event.*;
import java.util.*;	// ArrayList<E>
import javax.swing.*;

class BaseFrame extends JFrame {
	private BaseFrameController controller = new BaseFrameController(this);
	private JDesktopPane desktopPane = new JDesktopPane();
	private JMenuBar menuBar = new JMenuBar();

	private ArrayList<GoLModel> gamesList = new ArrayList<GoLModel>();

	/**
	 * Erzeuge das Grundfenster mit angegebener Breite und Höhe
	 *
	 * @param	width	Fensterbreite (in px)
	 * @param	height	Fensterhöhe (in px)
	 */
	public BaseFrame(int width, int height) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("GameBox");
		setSize(width, height);

		desktopPane.setDesktopManager(new DefaultDesktopManager());
		setContentPane(desktopPane);

		setJMenuBar(updateMenu());

		setVisible(true);
	}

	/**
	 * @return	Liste aller aktiven Spiel
	 */
	public ArrayList<GoLModel> getGamesList() {
		return gamesList;
	}

	/**
	 * @return	Aktive DesktopPane dieses Frames (zum hinzufügen von
	 *			JInternameFrame)
	 */
	public JDesktopPane getDesktopPane() {
		return desktopPane;
	}

	public BaseFrameController getBaseFrameController() {
		return controller;
	}

	/**
	 * Erstelle bzw. aktualisere das Menü für das Hauptfenster
	 *
	 * "Spiele"
	 *
	 * + Neues Spiel (+ neuer View(addObserver))
	 * ------------------------------
	 * \-+ Spiel 1
	 *   \-- Neuer View (addObserver)
	 *   \-- Beenden (getObserver)
	 * \-+ Spiel 2
	 *   \-- neuer View
	 *   \-- Beenden
	 * ------------------------------
	 * + Beenden (Programm beenden)
	 *
	 */
	public JMenuBar updateMenu() {

		// Erstelle eine neue saubere MenuBar
		menuBar = new JMenuBar();
		
		//****************************
		// Menüeinträge für GoL
		//****************************

		JMenu menu = new JMenu("GoL");

		// Neues Spiel erstellen
		JMenuItem menuItemNewGame = new JMenuItem("Neues Spiel");
		menuItemNewGame.addActionListener(controller.getNewGameListener());
		menu.add(menuItemNewGame);

		menu.addSeparator();

		// Liste aller aktiven Spiele mit ihren Untermenüs
		JMenu submenuGame;
		for(GoLModel game : gamesList) {
			submenuGame = new JMenu("Spiel " + game.getGameID());

			JMenuItem submenuItemNewView = new JMenuItem("Neue Ansicht");
			submenuItemNewView.addActionListener(controller.getNewViewListener(game));
			submenuGame.add(submenuItemNewView);

			JMenuItem submenuItemClose = new JMenuItem("Beende Spiel");
			submenuItemClose.addActionListener(controller.getCloseGameListener(game));

			submenuGame.add(submenuItemClose);

			menu.add(submenuGame);
		}

		// Kein zweiter Seperator, wenn keine Spiele aktiv sind
		if (gamesList.size() > 0) {
			menu.addSeparator();
		}

		// Programm Beenden
		JMenuItem menuItemClose = new JMenuItem("Beenden");
		menuItemClose.addActionListener(controller.getCloseListener());
		menu.add(menuItemClose);

		menuBar.add(menu);
		
		//****************************
		// Menüeinträge für Regenbogen
		//****************************
		
		menu = new JMenu("Regenbogen");
		JMenuItem regenbogenStarten = new JMenuItem("Starten");
		regenbogenStarten.setActionCommand("regenbogenStarten");
		regenbogenStarten.addActionListener(controller.getRegenbogenListener());
		menu.add(regenbogenStarten);
		menuBar.add(menu);
		
		//****************************
		// Menüeinträge für Quod
		//****************************
		
		menu = new JMenu("Quod");
		JMenuItem quodStarten = new JMenuItem("Starten");
		quodStarten.setActionCommand("quodStarten");
		quodStarten.addActionListener(controller.getQuodListener());
		menu.add(quodStarten);
		menuBar.add(menu);

		//****************************
		// Menüeinträge für Schloss
		//****************************
		
		menu = new JMenu("Schloss");
		JMenuItem schlossStarter = new JMenuItem("Starten");
		schlossStarter.addActionListener(controller.getSchlossListener());
		menu.add(schlossStarter);
		menuBar.add(menu);

		return menuBar;
	}
}
