import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Schreiben Sie ein GUI-Programm Regenbogen, welches zunächst ein Fenster aufmacht 
 * mit einem Knopf.  Durch Drücken des Knopfes wird (jedesmal neu) ein zusätzliches Fenster
 * geöffnet, in dem automatisch im Dreiviertelsekundentakt die Farbe wechselt, wobei reihum
 * alle Regenbogenfarben erscheinen.
 * @author Timo Appenzeller, 191382
 * @date 24.03.2016
 */

public class Regenbogen extends Frame implements ActionListener{
	
	Button btn = new Button("      ");
	
	/**
	 * Konstruktor von Regenbogen-Frames.
	 */
	public Regenbogen(){
		setLayout(new FlowLayout());
		btn.addActionListener(this);
		add(btn);
	}

	public static void main(String[] args) {
		Regenbogen r = new Regenbogen();
		WindowQuitter wquit = new WindowQuitter();
		r.addWindowListener(wquit);
		r.setSize(300, 300);
		r.setVisible(true);

	}

	/**
	 * Die actionPerformed-Methode erzeugt jedes mal, wenn der Benutzer auf den Knopf des Frames drückt einen RegenbogenThread.
	 */
	public void actionPerformed(ActionEvent e) {
		RegenbogenThread rbt = new RegenbogenThread();
	}

}
