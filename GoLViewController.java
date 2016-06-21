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
import java.awt.Point;

import javax.swing.JColorChooser;
import javax.swing.event.*;

class GoLViewController {
	private GoLView view;// = null;
	private Point mousePt = new Point();
	private Point lastCell = new Point();

	public GoLViewController(GoLView v) {
		view = v;
	}

	public InternalFrameListener getFrameListener(GoLModel game) {
		return new InternalFrameListener() {
			public void internalFrameClosing(InternalFrameEvent e) {
				game.deleteObserver(view);

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

	public ActionListener getModeListener(GoLModel game, String mode) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch(mode) {
					case ("LAUFEN"):
						//game.setCycleDelay(delay);
						System.out.println("Spiel " + game.getGameID()
							+ ": Modus -> " + mode);
						view.setMode(0);
						//game.resume();
						break;
					case ("SETZEN"):
						System.out.println("Spiel " + game.getGameID()
							+ ": Modus -> " + mode);
						//game.pause();
						view.setMode(1);
						break;
					case ("MALEN"):
						System.out.println("Spiel " + game.getGameID()
							+ ": Modus -> " + mode);
						//game.pause();
						view.setMode(2);
						break;
					default:
						System.out.println("Modus nicht bekannt!");
				}
			}
		};
	}

	public ActionListener getDelayListener(GoLModel game, int delay) {
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

	public ActionListener getAliveColorListener(Color color) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view.setAliveColor(color);
			}
		};
	}

	public ActionListener getDeadColorListener(Color color) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view.setDeadColor(color);
			}
		};
	}

	public ActionListener getGridColorListener(Color color) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view.setGridColor(color);
			}
		};
	}

	/**
	 * ZUM TESTEN
	 * @param view
	 * @param color
	 * @return
	 */
	public ActionListener getColorChooserListener(String status) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JColorChooser c = new JColorChooser();

				switch (status) {
				case "alive":
					view.setAliveColor(c.showDialog(view,
						"Farbe wählen", Color.white));
					break;
				case "dead":
					view.setDeadColor(c.showDialog(view,
						"Farbe wählen", Color.white));
					break;
				case "grid":
					view.setGridColor(c.showDialog(view,
						"Farbe wählen", Color.white));
					break;
				default:
					break;
				}

			}
		};
	}

	public ActionListener getLoadFormListener(GoLModel game, String form) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.loadForm(form);
			}
		};
	}

	public void clearMouseListeners() {
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
	}

	public MouseAdapter getMouseLaufenListener() {
		return new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				mousePt = e.getPoint();

				System.out.println("Mausklick: " + mousePt.getX()
					+ "/" + mousePt.getY());

				Point pt = view.getGridCell(mousePt.getX(),mousePt.getY());
				view.setGameField((int)(pt.getX()), (int)(pt.getY()));
			}
		};
	}

	public MouseAdapter getMouseSetzenListener() {
		return new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				mousePt = e.getPoint();

				//System.out.println("Mausklick: " + mousePt.getX()
				//	+ "/" + mousePt.getY());

				Point pt = view.getGridCell(mousePt.getX(),mousePt.getY());
				view.changeGameField((int)(pt.getX()), (int)(pt.getY()));
			}
		};
	}

	public MouseMotionAdapter getMouseMalenListener() {
		return new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				mousePt = e.getPoint();

				System.out.println("Mausklick: " + mousePt.getX()
					+ "/" + mousePt.getY());

				Point pt = view.getGridCell(mousePt.getX(),mousePt.getY());
				view.setGameField((int)(pt.getX()), (int)(pt.getY()));
			}
		};
	}
}
