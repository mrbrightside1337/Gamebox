/**
 * BaseFrameController.java
 *
 * Controller für BaseFrame.java
 *
 * @author Anton Makarow (191721)
 * @author Timo Appenzeller (191382)
 * @author Laura Lohmann (191529)
 */

import java.awt.event.*;
import java.util.*;	// ArrayList<E>
import javax.swing.*;

class BaseFrameController {
	private BaseFrame baseFrame;

	public BaseFrameController(BaseFrame frame) {
		baseFrame = frame;
	}

	/**
	 * Erstellt einen neuen internen Frame und fügt diesen der DesktopPane des
	 * BaseFrame hinzu
	 */
	private void createNewChildFrame(GoLModel game) {
		GoLView childFrame = new GoLView(baseFrame, game);
		baseFrame.getDesktopPane().add(childFrame);

		try {
			childFrame.setSelected(true);
		} catch (java.beans.PropertyVetoException e) {}
	}

	/**
	 * Erstellt ein neues Spiel
	 */
	public ActionListener getNewGameListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.print("Starte ein neues Spiel: ");

				GoLModel newGame = new GoLModel(50, 40);
				baseFrame.getGamesList().add(newGame);

				createNewChildFrame(newGame);

				baseFrame.updateMenu();
			}
		};
	}

	/**
	 * Erstellt ein neues Spiel als Kopie eines bereits bestehenden Spiels
	 */
	public ActionListener getNewGameCopyListener(GoLModel game) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.print("Starte ein neues Spield: ");

				GoLModel newGame = new GoLModel(game);
				baseFrame.getGamesList().add(newGame);

				createNewChildFrame(newGame);

				baseFrame.updateMenu();
			}
		};
	}

	/**
	 * Öffnet eine neue Ansicht auf ein bereits bestehendes Spiel
	 */
	public ActionListener getNewViewListener(GoLModel game) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Öffne neuen View für Spiel " + game.getGameID());
				createNewChildFrame(game);
			}
		};
	}

	/**
	 * NICHT IMPLEMENTIERT!
	 * Popup mit einer Liste der aktiven Spiele
	 */
	public ActionListener getShowActiveGamesListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Zeige Liste mit aktiven Spielen...");
				JOptionPane.showMessageDialog(baseFrame,
					"Liste mit aktiven Spieln");
			}
		};
	}

	/**
	 * Schliesse ein Spiel mit all seinen Ansichten (InternalFrames)
	 * Aktualisiere das Menü damit der Eintrag entfernt wird.
	 */
	public ActionListener getCloseGameListener(GoLModel game) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Beende Spiel " + game.getGameID());

				// Schliesse alle Observer Fenster
				game.closeObservers();

				// Das kann man bestimmer geschickter machen
				for(int i = 0; i < baseFrame.getGamesList().size(); ++i) {
					if(baseFrame.getGamesList().get(i) == game)
						baseFrame.getGamesList().remove(i);
				}

				baseFrame.updateMenu();
			}
		};
	}

	/**
	 * Beende das Programm
	 */
	public ActionListener getCloseListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Beende Programm");
				baseFrame.dispose();
				System.exit(0);
			}
		};
	}
	
	
	/**
	 * Für Regenbogen-Menü
	 */
	public ActionListener getRegenbogenListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new Regenbogen(baseFrame);

				//baseFrame.updateMenu();
			}
		};
	}
	
	/**
	 * Für Quod-Menü
	 */
	public ActionListener getQuodListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new QuodFrame(baseFrame);

				//baseFrame.updateMenu();
			}
		};
	}
}
