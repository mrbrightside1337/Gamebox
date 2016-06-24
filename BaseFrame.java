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

		updateMenu();

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
	public void updateMenu() {

		// Erstelle eine neue saubere MenuBar
		menuBar = new JMenuBar();
		
		//****************************
		// Menüeinträge für GameBox
		//****************************
		
		JMenu menu = new JMenu("GameBox");
		
		// Programm Beenden
		JMenuItem menuItemClose = new JMenuItem("Beenden");
		menuItemClose.addActionListener(controller.getCloseGameBoxListener());
		menu.add(menuItemClose);
		
		menuBar.add(menu);
		
		//****************************
		// Menüeinträge für GoL
		//****************************

		menu = new JMenu("GoL");

		// Neues Spiel erstellen
		JMenuItem menuItemNewGame = new JMenuItem("Neues Spiel");
		menuItemNewGame.addActionListener(controller.getNewGameListener());
		menu.add(menuItemNewGame);

		if (gamesList.size() > 0) {
			menu.addSeparator();
		}

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

		menuBar.add(menu);
		
		//****************************
		// Menüeinträge für Weitere Spiele
		//****************************
		
		menu = new JMenu("Weitere Spiele");
		
		//Quod
		JMenuItem quodStarten = new JMenuItem("Quod");
		quodStarten.setActionCommand("quodStarten");
		quodStarten.addActionListener(controller.getQuodListener());
		menu.add(quodStarten);
		
		//Regenbogen
		JMenuItem regenbogenStarten = new JMenuItem("Regenbogen");
		regenbogenStarten.setActionCommand("regenbogenStarten");
		regenbogenStarten.addActionListener(controller.getRegenbogenListener());
		menu.add(regenbogenStarten);
		
		//Schloss
		JMenuItem schlossStarter = new JMenuItem("Schloss");
		schlossStarter.addActionListener(controller.getSchlossListener());
		menu.add(schlossStarter);
		
		//Drehschloss
		JMenuItem drehSchlossStarter = new JMenuItem("Drehschloss");
		drehSchlossStarter.addActionListener(controller.getSonstigesListener());
		menu.add(drehSchlossStarter);
		
		//Max
		JMenuItem maxStarter = new JMenuItem("Max");
		maxStarter.addActionListener(controller.getSonstigesListener());
		menu.add(maxStarter);
		
		menuBar.add(menu);

		setJMenuBar(menuBar);
	}
}
