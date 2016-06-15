/**
 * GoLModelController.java
 *
 * Sammlung von Controllern für BaseFrame.java
 *
 * @author Anton Makarow (191721)
 * @author Timo Appenzeller (191382)
 * @author Laura Lohmann (191529)
 */

import java.awt.event.*;
import java.util.*;	// ArrayList<E>
import javax.swing.Timer;

class GoLModelController {
	private GoLModel currentGame;
	private Timer timer;
	private ActionListener task;

	public GoLModelController(GoLModel game) {
		currentGame = game;

		task = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				System.out.print("Timer Event (" + game.getCycleDelay()
				+ "ms) Spiel " + currentGame.getGameID() + ": ");
				currentGame.cycle();
			}
		};

		// Timer wird mit default Wert von 1000ms initialisiert. Den richtigen
		// Wert zieht er sich sobald er gestartet wird.
		timer = new Timer(1000, task);
		timer.stop();
	}

	public void startTimer() {
		timer.setDelay(currentGame.getCycleDelay());
		timer.start();
	}

	public void changeDelay(int delay) {
		timer.stop();
		timer.setDelay(delay);
		timer.start();
	}

	public void pauseTimer() {
		timer.stop();
	}

	public void resumeTimer() {
		timer.start();
	}

	public void stopTimer() {
		timer.stop();
	}
}
