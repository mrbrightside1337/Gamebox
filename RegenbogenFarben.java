/**
 * RegenbogenFrarben.java
 *
 * Hilfsklasse RegenbogenFarben erzeugt Frames mit RegenbogenFarben.
 *
 * @author Timo Appenzeller, 191382
 * @date 24.03.2016
 *
 */

import java.awt.Color;
import java.awt.Frame;

import javax.swing.JInternalFrame;

public class RegenbogenFarben extends JInternalFrame {
	
	Color[] colors = { 	Color.red,
						Color.orange,
						Color.yellow,
						Color.green,
						new Color(0, 191, 255), //light Skyblue
						new Color(75, 0, 130), //Indigo
						new Color(148,0,211) //Violet 
						}; // Farbpalette
	int col = 0; //aktuelle Farbe
	
	private BaseFrame baseframe;
	
	/**
	 * Konstruktor zur Erzeugung von RegenbogenFarben-Frames.
	 */
	public RegenbogenFarben(BaseFrame baseframe){
		this.baseframe = baseframe;
		setSize(300, 300);
		setTitle("Regenbogen-Farben");
		setVisible(true);
		baseframe.add(this);
		try {
			this.setSelected(true);
		} catch (java.beans.PropertyVetoException e) {}
	}
	
	/**
	 * Die Methode changeBackgroundColor wechselt bei jedem Aufruf zyklisch
	 * durch die in colors festgelegten Hintergrundfarben.
	 */
	public void changeBackgroundColor(){
		setBackground( colors[col] );
		col = (col+1) % colors.length;
		repaint();
	}

}
