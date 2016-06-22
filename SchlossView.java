/**
 * SchlossView.java
 *
 * Das Kindfenster mit dem View auf das Spiel
 *
 * @author Anton Makarow (191721)
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class SchlossView extends JInternalFrame implements ActionListener {
	//private GoLViewController controller;
	private BaseFrame parrentFrame;

	private String code = "230360";
	private int codePos = 0;
	private String codeInput = new String();

	private String[] buttonTitles = new String[] {"0", "1", "2", "3", "4", "5",
		"6", "7", "8", "9"};

	/**
	 * @param	game	Das Spiel das in diesem Frame gezeichnet werden soll
	 */
	public SchlossView(BaseFrame parrent) {
		// JInternalFrame(resizable, closable, maximizable, iconifiable)
		super("Schloss - 230360", true, true, true, true);
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		setSize(350, 200);

  		parrentFrame = parrent;

		setLayout(new FlowLayout());

		for(String s : buttonTitles) {
			Button b = new Button(s);
			b.setPreferredSize(new Dimension(50, 50));
			b.addActionListener(this);
			b.setActionCommand(s);
			add(b);
		}

		setVisible(true);

		parrentFrame.add(this);
		try {
			this.setSelected(true);
		} catch (java.beans.PropertyVetoException e) {}
	}

	public void actionPerformed(ActionEvent e) {
		//System.out.println("ActionEvent: " + e);
		//System.out.println("e.getActionCommand(): " + e.getActionCommand());

		codeInput += e.getActionCommand();
		codePos++;

		if(code.matches(new String(codeInput + "(.*)")) && codePos == code.length()) {
			System.out.println("codeInput: " + codeInput +" OK");
			System.out.println("Code richtig! Beende Programm.");
			dispose();
		} else if(code.matches(new String(codeInput + "(.*)"))) {
			//System.out.println("codeInput: " + codeInput +" OK");
			setBackground(Color.green);
		} else {
			//System.out.println("codeInput: " + codeInput +" Falsch -> reset");
			setBackground(Color.red);
			codeInput = "";
			codePos = 0;
		}

		repaint();
	}
}
