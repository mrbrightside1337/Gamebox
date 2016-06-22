import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JInternalFrame;

/**
 * Schreiben Sie ein GUI-Programm Regenbogen, welches zun�chst ein Fenster aufmacht 
 * mit einem Knopf.  Durch Dr�cken des Knopfes wird (jedesmal neu) ein zus�tzliches Fenster
 * ge�ffnet, in dem automatisch im Dreiviertelsekundentakt die Farbe wechselt, wobei reihum
 * alle Regenbogenfarben erscheinen.
 * @author Timo Appenzeller, 191382
 * @date 24.03.2016
 */

public class Regenbogen extends JInternalFrame implements ActionListener{
	
	private Button btn = new Button("      ");
	private BaseFrame baseframe;
	
	/**
	 * Konstruktor von Regenbogen-Frames.
	 */
	public Regenbogen(BaseFrame baseframe){
		this.baseframe = baseframe;
		setLayout(new FlowLayout());
		setSize(300, 300);
		setTitle("Regenbogen-Starter");
		btn.addActionListener(this);
		add(btn);
		setVisible(true);
		baseframe.add(this);
		try {
			this.setSelected(true);
		} catch (java.beans.PropertyVetoException e) {}
	}

	/*
	public static void main(String[] args) {
		Regenbogen r = new Regenbogen();
		r.setSize(300, 300);
		r.setVisible(true);

	}
	*/
	
	/**
	 * Die actionPerformed-Methode erzeugt jedes mal, wenn der Benutzer auf den Knopf des Frames dr�ckt einen RegenbogenThread.
	 */
	public void actionPerformed(ActionEvent e) {
		new RegenbogenThread(baseframe);
	}

}
