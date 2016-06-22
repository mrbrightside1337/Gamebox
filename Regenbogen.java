/**
 * Regenbogen.java
 *
 * Öffne zunächst ein Fenster mit einem Knopf. Durch Drücken des Knopfes wird
 * (jedesmal neu) ein zusätzliches Fenster geöffnet, in dem automatisch im
 * Dreiviertelsekundentakt die Farbe wechselt, wobei reihum alle
 * Regenbogenfarben erscheinen.
 *
 * @author Timo Appenzeller, 191382
 * @date 24.03.2016
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JInternalFrame;

public class Regenbogen extends JInternalFrame implements ActionListener{

	private Button btn = new Button("  Starter  ");
	private BaseFrame baseframe;

	/**
	 * Konstruktor von Regenbogen-Frames.
	 */
	public Regenbogen(BaseFrame parrent){
		super("Regenbogen-Starter", true, true, true, true);
		baseframe = parrent;
		setLayout(new FlowLayout());
		setSize(200, 100);

		btn.setPreferredSize(new Dimension(80, 40));
		btn.addActionListener(this);
		add(btn);

		setVisible(true);

		baseframe.add(this);
		try {
			this.setSelected(true);
		} catch (java.beans.PropertyVetoException e) {}
	}

	/**
	 * Die actionPerformed-Methode erzeugt jedes mal, wenn der Benutzer auf
	 * den Knopf des Frames drückt einen RegenbogenThread.
	 */
	public void actionPerformed(ActionEvent e) {
		new RegenbogenThread(baseframe);
	}

}
