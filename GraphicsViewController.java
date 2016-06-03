/**
 * GraphicsViewController.java
 *
 * Controller for GraphicsView.java
 *
 *
 * @author Anton Makarow (191721)
 * @author Timo Appenzeller (191382)
 * @author Laura Lohmann (191529)
 */

import java.awt.event.*;
import java.awt.Point;

class GraphicsViewController {
	private GraphicsView currentFrame;
	private Point mousePt = new Point();
	private Point lastCell = new Point();

	public GraphicsViewController(GraphicsView frame) {
		currentFrame = frame;
	}

	public void clearMouseListeners() {
		MouseListener[] mouseListeners =
			currentFrame.getMouseListeners();

		for(MouseListener ml : mouseListeners) {
			currentFrame.removeMouseListener(ml);
		}

		MouseMotionListener[] mouseMotionListeners =
			currentFrame.getMouseMotionListeners();

		for(MouseMotionListener ml : mouseMotionListeners) {
			currentFrame.removeMouseMotionListener(ml);
		}
	}

	public MouseAdapter getMouseLaufenListener() {
		return new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				mousePt = e.getPoint();

				//System.out.println("Mausklick: " + mousePt.getX()
				//	+ "/" + mousePt.getY());

				Point pt = currentFrame.getGridCell(mousePt.getX(),mousePt.getY());
				currentFrame.setGameField((int)(pt.getX()), (int)(pt.getY()));
			}
		};
	}

	public MouseAdapter getMouseSetzenListener() {
		return new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				mousePt = e.getPoint();

				//System.out.println("Mausklick: " + mousePt.getX()
				//	+ "/" + mousePt.getY());

				Point pt = currentFrame.getGridCell(mousePt.getX(),mousePt.getY());
				currentFrame.changeGameField((int)(pt.getX()), (int)(pt.getY()));
			}
		};
	}

	public MouseMotionAdapter getMouseMalenListener() {
		return new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				mousePt = e.getPoint();

				System.out.println("Mausklick: " + mousePt.getX()
					+ "/" + mousePt.getY());

				Point pt = currentFrame.getGridCell(mousePt.getX(),mousePt.getY());
				currentFrame.setGameField((int)(pt.getX()), (int)(pt.getY()));
			}
		};
	}
}
