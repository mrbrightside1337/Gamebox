/**
 * ChildFrameController.java
 *
 * Controllern für ChildFrame.java
 *
 * @author Anton Makarow (191721)
 * @author Timo Appenzeller (191382)
 * @author Laura Lohmann (191529)
 */

import java.awt.Color;
import java.awt.event.*;

import javax.swing.JColorChooser;
import javax.swing.event.*;

class ChildFrameController {
	private ChildFrame childFrame = null;

	public ChildFrameController(ChildFrame frame) {
		childFrame = frame;
	}

	public InternalFrameListener getFrameListener(GameModel game) {
		return new InternalFrameListener() {
			public void internalFrameClosing(InternalFrameEvent e) {
				game.deleteObserver(childFrame.getView());

				System.out.print("Spiel: " + game.getGameID() + " hat noch "
					+ game.countObservers() + " offene Fenster.");

				if(game.countObservers() == 0) {
					System.out.print(" Pausiere Spiel: " + game.getGameID());
					game.pause();
				}

				System.out.println("");
			}

			public void internalFrameClosed(InternalFrameEvent e) {}

			public void internalFrameOpened(InternalFrameEvent e) {
				System.out.println("Öffne neues Fenster (" + game.countObservers()
				+ ") für Spiel: " + game.getGameID());
				// Nicht automatisch fortsetzen
				// game.resume();
			}

			public void internalFrameIconified(InternalFrameEvent e) {}

			public void internalFrameDeiconified(InternalFrameEvent e) {}

			public void internalFrameActivated(InternalFrameEvent e) {}

			public void internalFrameDeactivated(InternalFrameEvent e) {}
		};
	}

	public ActionListener getModeListener(GameModel game, String mode) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch(mode) {
					case ("LAUFEN"):
						//game.setCycleDelay(delay);
						System.out.println("Spiel " + game.getGameID()
							+ ": Modus -> " + mode);
						childFrame.getView().setMode(0);
						//game.resume();
						break;
					case ("SETZEN"):
						System.out.println("Spiel " + game.getGameID()
							+ ": Modus -> " + mode);
						//game.pause();
						childFrame.getView().setMode(1);
						break;
					case ("MALEN"):
						System.out.println("Spiel " + game.getGameID()
							+ ": Modus -> " + mode);
						//game.pause();
						childFrame.getView().setMode(2);
						break;
					default:
						System.out.println("Modus nicht bekannt!");
				}
			}
		};
	}

	public ActionListener getSetDelayListener(GameModel game, int delay) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(delay == 0) {
					game.pause();
				} else {
					game.setCycleDelay(delay);
					game.resume();
				}
			}
		};
	}

	public ActionListener getSetAliveColorListener(GraphicsView view, Color color) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view.setAliveColor(color);
			}
		};
	}

	/**
	 * ZUM TESTEN
	 * @param view
	 * @param color
	 * @return
	 */
	public ActionListener getSetColorChooserListener(GraphicsView view, String status) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JColorChooser c = new JColorChooser();

				switch (status) {
				case "alive":
					view.setAliveColor(c.showDialog(childFrame, "Farbe wählen", Color.white));
					break;
				case "dead":
					view.setDeadColor(c.showDialog(childFrame, "Farbe wählen", Color.white));
					break;
				case "grid":
					view.setGridColor(c.showDialog(childFrame, "Farbe wählen", Color.white));
					break;
				default:
					break;
				}

			}
		};
	}

	public ActionListener getSetDeadColorListener(GraphicsView view, Color color) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view.setDeadColor(color);
			}
		};
	}

	public ActionListener getSetGridColorListener(GraphicsView view, Color color) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view.setGridColor(color);
			}
		};
	}

	public ActionListener getLoadFormListener(GameModel game, String form) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.loadForm(form);
			}
		};
	}

	/*
	 * Evtl. später. Im Moment buggy. (View wird nicht richtig größer)
	 *
	public ActionListener getSetZoomListener(GraphicsView view, int zoom) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view.setZoom(zoom);
			}
		};
	}
	*/

/*
	public MouseAdapter getClickViewListener() {
		return new MouseAdapter() {
			public void MousePressed(MouseEvent e) {
				mousePt = e.getPoint();
				System.out.println("Mausklick: " + mousePt.getX()
					+ "/" + mousePt.getY());
			}
		};
	}

	public MouseMotionAdapter getMoveViewListener() {
		return new MouseMotionAdapter() {
			public void MouseDragged(MouseEvent e) {

			}
		};
	}
*/
}
